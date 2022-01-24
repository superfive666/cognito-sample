package com.osakakuma.opms.audit.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.osakakuma.opms.audit.dao.AuditLogMapper;
import com.osakakuma.opms.audit.entity.AuditLog;
import com.osakakuma.opms.audit.model.AuditLogListRequest;
import com.osakakuma.opms.audit.model.AuditLogRecord;
import com.osakakuma.opms.common.util.LogBox;
import com.osakakuma.opms.common.util.OpmsAssert;
import com.osakakuma.opms.config.model.CognitoUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    private final MessageSourceAccessor messageSourceAccessor;
    private final AuditLogMapper auditLogMapper;

    public PageInfo<AuditLogRecord> searchAuditLogs(CognitoUser user, AuditLogListRequest request) {
        OpmsAssert.isAdmin(user, "[view audit logs]");

        PageHelper.startPage(request.page(), request.pageSize());
        var page = PageInfo.of(auditLogMapper.searchAuditLogs(request));
        var list = page.getList().stream().map(this::mapRecord).toList();
        var result = PageInfo.of(list);
        BeanUtils.copyProperties(page, result);
        result.setList(list);

        return result;
    }

    private AuditLogRecord mapRecord(AuditLog log) {
        return new AuditLogRecord(
                log.getUsername(),
                log.getLogTime(),
                // direct translation of messages from logged content
                messageSourceAccessor.getMessage("#" + log.getLogAction().name() + LogBox.LOG_ACTION_SUFFIX),
                messageSourceAccessor.getMessage("#" + log.getLogModule().name() + LogBox.LOG_MODULE_SUFFIX),
                messageSourceAccessor.getMessage(log.getLogTitle()),
                messageSourceAccessor.getMessage(log.getLogDescription()),
                Optional.ofNullable(log.getValBefore()).map(messageSourceAccessor::getMessage).orElse(null),
                Optional.ofNullable(log.getValAfter()).map(messageSourceAccessor::getMessage).orElse(null)
        );
    }
}
