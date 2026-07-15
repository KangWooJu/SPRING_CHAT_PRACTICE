package org.woojukang.springChatPractice.global.docs.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.woojukang.springChatPractice.global.config.exception.dto.ApiResult;
import org.woojukang.springChatPractice.global.security.dto.response.LoginResponse;

import java.time.LocalDateTime;

@Tag(name = "Docks", description = "Controller 형태가 아닌 API를 위한 문서")
@RestController
@RequestMapping("/docs/api")
public class ApiDocsController {

    @PostMapping("/login")
    public ResponseEntity<ApiResult<LoginResponse>> login(){

        return ResponseEntity
                .status(HttpStatus
                        .OK)
                .body(ApiResult
                        .success(new LoginResponse("test1",
                                "로그인에 성공하였습니다.",
                                LocalDateTime
                                        .now()
                                        .toString())));

    }
}
