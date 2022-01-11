package com.osakakuma.opms.common.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record FileUploadResponse(
    @Schema(description = "The globally unique ID for the file uploaded to the system", required = true)
    String fileId,
    @Schema(description = "The fully qualified URL for retrieving the file uploaded", required = true)
    String url
) { }
