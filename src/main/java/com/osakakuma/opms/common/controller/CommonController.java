package com.osakakuma.opms.common.controller;

import com.osakakuma.opms.common.model.BaseResponse;
import com.osakakuma.opms.config.model.CognitoUser;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jpi/admin/common")
public class CommonController {
    @Operation(summary = "User Information", description = "Retrieve the current user information, if session exists")
    @GetMapping("/userInfo")
    public ResponseEntity<BaseResponse<CognitoUser>> userInfo(CognitoUser user) {
        return BaseResponse.success(user);
    }
}
