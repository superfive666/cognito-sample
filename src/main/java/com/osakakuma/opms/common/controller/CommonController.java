package com.osakakuma.opms.common.controller;

import com.osakakuma.opms.common.model.BaseResponse;
import com.osakakuma.opms.config.model.CognitoUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jpi/admin/common")
public class CommonController {
    @GetMapping("/userInfo")
    public ResponseEntity<BaseResponse<CognitoUser>> userInfo(CognitoUser user) {
        return BaseResponse.success(user);
    }
}
