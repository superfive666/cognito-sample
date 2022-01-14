package com.osakakuma.opms.audit.controller;

import com.github.pagehelper.PageInfo;
import com.osakakuma.opms.audit.entity.AuditLog;
import com.osakakuma.opms.audit.model.AuditLogListRequest;
import com.osakakuma.opms.audit.model.AuditLogRecord;
import com.osakakuma.opms.audit.service.AuditLogService;
import com.osakakuma.opms.common.model.BaseResponse;
import com.osakakuma.opms.config.model.CognitoUser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jpi/admin/audit")
public class AuditLogController {
    private final AuditLogService auditLogService;

    @Operation(summary = "List audit logs", description = "Search audit logs of the system by parameters")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<PageInfo<AuditLogRecord>>> searchAuditLog(CognitoUser user, @Valid AuditLogListRequest request) {
        return BaseResponse.success(auditLogService.searchAuditLogs(user, request));
    }
}
