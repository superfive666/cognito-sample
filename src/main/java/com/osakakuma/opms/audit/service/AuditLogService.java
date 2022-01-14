package com.osakakuma.opms.audit.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.osakakuma.opms.audit.entity.AuditLog;
import com.osakakuma.opms.audit.model.AuditLogListRequest;
import com.osakakuma.opms.audit.model.AuditLogRecord;
import com.osakakuma.opms.common.service.MessageService;
import com.osakakuma.opms.common.util.OpmsAssert;
import com.osakakuma.opms.config.model.CognitoUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    private final MessageService messageService;

    public PageInfo<AuditLogRecord> searchAuditLogs(CognitoUser user, AuditLogListRequest request) {
        OpmsAssert.isAdmin(user, "[view audit logs]");

        PageHelper.startPage(request.page(), request.pageSize());


        return PageInfo.of(new ArrayList<>());
    }

    private AuditLogRecord mapRecord(AuditLog log) {

        return null;
    }
}
