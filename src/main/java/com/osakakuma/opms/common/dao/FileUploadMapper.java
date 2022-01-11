package com.osakakuma.opms.common.dao;

import com.osakakuma.opms.common.entity.FileUpload;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileUploadMapper {
    void insertUploadFile(FileUpload fileUpload);

    FileUpload getFileUploadByFileId(String fileId);
}
