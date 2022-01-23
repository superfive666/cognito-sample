package com.osakakuma.opms.common.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class AuditLog {
    private String username;
    private Instant logTime;
    private LogAction logAction;
    private LogModule logModule;
    private String logTitle;
    private String logDescription;
    private String valBefore;
    private String valAfter;
}
