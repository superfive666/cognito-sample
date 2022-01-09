package com.osakakuma.opms.common.entity;

import com.osakakuma.opms.common.model.FileModule;
import com.osakakuma.opms.common.model.FileStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class FileUpload {
    private String id;
    private FileModule module;
    private String fileName;
    private String fileExt;
    private String author;
    private Instant created;
    private Instant updated;
    private FileStatus status;
}
