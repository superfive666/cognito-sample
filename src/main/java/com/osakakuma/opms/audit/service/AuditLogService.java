package com.osakakuma.opms.audit.service;

import com.osakakuma.opms.common.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    private final MessageService messageService;


}
