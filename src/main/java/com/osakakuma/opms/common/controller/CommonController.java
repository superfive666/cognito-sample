package com.osakakuma.opms.common.controller;

import com.osakakuma.opms.common.model.BaseResponse;
import com.osakakuma.opms.common.model.FileUploadRequest;
import com.osakakuma.opms.common.service.FileUploadService;
import com.osakakuma.opms.config.model.CognitoUser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/jpi/admin/common")
@RequiredArgsConstructor
public class CommonController {
    private final FileUploadService fileUploadService;

    @Operation(summary = "User Information", description = "Retrieve the current user information, if session exists")
    @GetMapping(value = "/userInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<CognitoUser>> userInfo(CognitoUser user) {
        return BaseResponse.success(user);
    }

    @Operation(summary = "Upload File", description = "Upload a file to the server and obtain the fully qualified URL address for the uploaded file")
    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<String>> uploadFile(CognitoUser user, HttpServletRequest httpServletRequest,
                                                           @ModelAttribute @Valid FileUploadRequest fileUploadRequest) {
        var domain = httpServletRequest.getServerName();

        // return the fully qualified URL for the uploaded file
        return BaseResponse.success(fileUploadService.uploadFile(user, fileUploadRequest, domain));
    }
}
