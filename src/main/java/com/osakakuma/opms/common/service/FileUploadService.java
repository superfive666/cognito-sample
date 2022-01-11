package com.osakakuma.opms.common.service;

import com.osakakuma.opms.common.dao.FileUploadMapper;
import com.osakakuma.opms.common.entity.FileUpload;
import com.osakakuma.opms.common.model.FileModule;
import com.osakakuma.opms.common.model.FileStatus;
import com.osakakuma.opms.common.model.FileUploadRequest;
import com.osakakuma.opms.common.model.FileUploadResponse;
import com.osakakuma.opms.common.util.OpmsAssert;
import com.osakakuma.opms.config.model.CognitoUser;
import com.osakakuma.opms.error.OpmsException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {
    private static final int MAX_FILE_SIZE_MB = 5;
    private static final List<String> ALLOWED_MIMES = Arrays.asList("jpeg", "jpg", "png", "gif");
    private static final Map<String, String> MIME_MAP = Map.of(
            "jpeg", "image/jpeg",
            "jpg", "image/jpeg",
            "png", "image/png",
            "gif", "image/gif"
    );

    private final S3AsyncClient s3AsyncClient;
    private final FileUploadMapper fileUploadMapper;

    @Value("#{@awsS3Config.bucket}")
    private String bucket;

    @Transactional(rollbackFor = Exception.class)
    public FileUploadResponse uploadFile(CognitoUser user, FileUploadRequest fileUpload, String domain) {
        return uploadToS3(user, fileUpload, domain);
    }

    private String insertFileDb(CognitoUser user, FileUploadRequest request) {
        var id = UUID.randomUUID().toString();

        fileUploadMapper.insertUploadFile(FileUpload.builder()
                        .id(id)
                        .module(FileModule.PRODUCT)
                        .fileName(request.fileName())
                        .fileExt(request.fileExtension())
                        .author(user.username())
                        .status(FileStatus.OK)
                .build());

        return id;
    }

    @SneakyThrows(IOException.class)
    private FileUploadResponse uploadToS3(CognitoUser user, FileUploadRequest fileUpload, String domain) {
        var mime = validateFileUpload(fileUpload);
        var path = getFilePath(user, fileUpload);
        var id = insertFileDb(user, fileUpload);

        try (var is = fileUpload.file().getInputStream()) {
           upload(is, path, mime);
        } catch (Exception ex) {
           log.error("Failed to upload to s3", ex);
           throw new OpmsException("Failed to upload to s3");
        }

        return new FileUploadResponse(id, MessageFormat.format("https://{0}/{1}", domain, path));
    }

    @SneakyThrows(IOException.class)
    private String validateFileUpload(FileUploadRequest request) {
        OpmsAssert.isTrue(ALLOWED_MIMES.stream()
                        .anyMatch(request.fileExtension()::equalsIgnoreCase),
                () -> "Only allow image upload for the moment");

        try (var is = request.file().getInputStream()) {
            var tika = new Tika();
            var mime = tika.detect(is);
            var expected = MIME_MAP.get(mime);
            OpmsAssert.equalsIgnoreCase(expected, request.fileExtension(),
                    () -> "File extension does not match the file actual content");

            return mime;
        }
    }

    @SneakyThrows(IOException.class)
    private void upload(InputStream is, String path, String mediaType) {
        var bytes = is.readAllBytes();
        OpmsAssert.isTrue(bytes.length/1_000_000 <= MAX_FILE_SIZE_MB,
                () -> "Maximum file size allowed is " + MAX_FILE_SIZE_MB + "MB");

        s3AsyncClient.putObject(PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(path)
                        .contentType(mediaType)
                        .build(),
                AsyncRequestBody.fromBytes(bytes))
                .whenComplete((response, error) -> {
                    if (Objects.nonNull(error)) {
                        log.error("Failed to upload to s3 with error {}", error.toString());
                    } else {
                        log.info("Upload to s3 completed successfully. {}", response.toString());
                    }
                }).join();
    }

    private String getFilePath(CognitoUser user, FileUploadRequest request) {
        // base path is prefixed with "s3"
        return "s3" + "/" +
                // module name to separate the files
                request.module().name() + "/" +
                // username sub-path
                user.username() + "/" +
                // date string to prevent files with same name
                getDateString() + "/" +
                // filename.file_ext
                request.fileName() + "." + request.fileExtension();
    }

    private String getDateString() {
        var format = new SimpleDateFormat("yyyyMMdd");
        return format.format(Instant.now());
    }
}
