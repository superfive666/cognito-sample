package com.osakakuma.opms.audit.controller;

import com.osakakuma.opms.audit.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jpi/admin/audit")
public class AuditLogController {
    private final AuditLogService auditLogService;


}
