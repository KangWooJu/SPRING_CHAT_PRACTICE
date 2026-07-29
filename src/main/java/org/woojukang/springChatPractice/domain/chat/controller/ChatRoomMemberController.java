package org.woojukang.springChatPractice.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.woojukang.springChatPractice.domain.chat.dto.request.AddChatRoomMemberRequest;
import org.woojukang.springChatPractice.domain.chat.dto.request.DeleteChatRoomMemberRequest;
import org.woojukang.springChatPractice.domain.chat.dto.response.AddChatUserResponse;
import org.woojukang.springChatPractice.domain.chat.dto.response.DeleteChatUserResponse;
import org.woojukang.springChatPractice.domain.chat.facade.ChatFacade;
import org.woojukang.springChatPractice.global.config.exception.dto.ApiResult;

@RestController
@RequestMapping("/api/v1/chat/member")
@RequiredArgsConstructor
public class ChatRoomMemberController {

    private final ChatFacade chatFacade;

    @PostMapping("/add")
    public ResponseEntity<ApiResult<AddChatUserResponse>> addMember
            (@AuthenticationPrincipal UserDetails userDetails,
             @RequestBody AddChatRoomMemberRequest addChatUserRequest){

        return ResponseEntity
                .status(HttpStatus
                        .OK)
                .body(ApiResult
                        .success(chatFacade
                                .addChatUser(addChatUserRequest)));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResult<DeleteChatUserResponse>> deleteMember
            (@AuthenticationPrincipal UserDetails userDetails,
             @RequestBody DeleteChatRoomMemberRequest deleteChatRoomMemberRequest){

        return ResponseEntity
                .status(HttpStatus
                        .OK)
                .body(ApiResult
                        .success(chatFacade
                                .deleteChatUser(deleteChatRoomMemberRequest)));
    }
}
