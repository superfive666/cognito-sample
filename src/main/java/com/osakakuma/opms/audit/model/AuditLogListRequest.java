package com.osakakuma.opms.audit.model;

import com.osakakuma.opms.audit.entity.LogAction;
import com.osakakuma.opms.audit.entity.LogModule;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.api.annotations.ParameterObject;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@ParameterObject
public record AuditLogListRequest(
        @Schema(description = "page number starting with 1", required = true)
        @NotNull(message = "page number is missing")
        @Min(value = 1, message = "Invalid page number")
        Integer page,
        @Schema(description = "page size", required = true)
        @NotNull(message = "page size is missing")
        @Min(value = 10, message = "Minimum page size is 10")
        Integer pageSize,
        @Schema(description = "Search audit log by cognito username, exactly search")
        String username,
        @Schema(description = "Search audit log by actions, null -> search every action")
        List<LogAction> actions,
        @Schema(description = "Search audit log by modules, null -> search every module")
        List<LogModule> modules,
        @Schema(description = "The unix millisecond timestamp for begin time", required = true)
        @NotNull(message = "Audit log begin timestamp is required")
        Long begin,
        @Schema(description = "The unix millisecond timestamp for end time", required = true)
        @NotNull(message = "Audit log end timestamp is required")
        Long end
) { }
