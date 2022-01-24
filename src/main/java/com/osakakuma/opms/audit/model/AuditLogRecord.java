package com.osakakuma.opms.audit.model;

import com.osakakuma.opms.audit.entity.LogAction;
import com.osakakuma.opms.audit.entity.LogModule;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record AuditLogRecord(
        @Schema(description = "The cognito username", required = true)
        String username,
        @Schema(description = "The audit log entry timestamp", required = true)
        Instant logTime,
        @Schema(description = "The audit log action performed", required = true)
        String logAction,
        @Schema(description = "The audit log module that belongs to", required = true)
        String logModule,
        @Schema(description = "The translated display audit log title", required = true)
        String logTitle,
        @Schema(description = "The translated display audit log description", required = true)
        String logDescription,
        @Schema(description = "The audit log value before display string")
        String valBefore,
        @Schema(description = "The audit log value after display string")
        String valAfter
) { }
