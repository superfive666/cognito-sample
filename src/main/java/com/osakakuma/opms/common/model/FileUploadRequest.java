package com.osakakuma.opms.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record FileUploadRequest(
        @Schema(description = "The name of the file to be uploaded without extension", required = true)
        @NotEmpty(message = "File name should not be empty")
        @Size(max = 50, message = "Maximum file name length is 50")
        String fileName,
        @Schema(description = "The extension of the file to be uploaded", required = true)
        @NotEmpty(message = "File extension is missing")
        @Size(max = 4, message = "File extension exceeded maximum length of 4")
        String fileExtension,
        @Schema(description = "The module that the file is uploaded for", required = true)
        @NotNull(message = "Please specify the module (purpose) of the file uploaded")
        FileModule module,
        @Schema(description = "The actual file to be uploaded")
        @NotNull(message = "Missing uploaded file")
        MultipartFile file
) { }
