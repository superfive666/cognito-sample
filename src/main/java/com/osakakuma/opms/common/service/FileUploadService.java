package com.osakakuma.opms.common.service;

import com.osakakuma.opms.common.model.FileUploadRequest;
import com.osakakuma.opms.config.model.CognitoUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.s3.S3AsyncClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final S3AsyncClient s3AsyncClient;

    @Value("#{@awsS3Config.bucket}")
    private String bucket;

    @Transactional(rollbackFor = Exception.class)
    public String uploadFile(CognitoUser user, FileUploadRequest fileUpload, String domain) {

        return null;
    }
}
