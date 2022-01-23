package com.osakakuma.opms.audit.dao;

import com.osakakuma.opms.audit.entity.AuditLog;
import com.osakakuma.opms.audit.model.AuditLogListRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AuditLogMapper {
    List<AuditLog> searchAuditLogs(AuditLogListRequest request);
}