package org.woojukang.springChatPractice.domain.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name="chatRoomMember",description = "채팅방 멤버 API")
@RestController
@RequestMapping("/api/v1/chat/member")
@RequiredArgsConstructor
public class ChatRoomMemberController {

    private final ChatFacade chatFacade;

    @Operation(summary = "채팅방 맴버 추가",
            description = "채팅방에 새로운 User맴버를 추가합니다.")
    @PostMapping("/add")
    @SecurityRequirement(name = "JWT")
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

    @Operation(summary = "채팅방 맴버 삭제",
            description = "채팅방에서 기존의 User맴버를 삭제합니다.")
    @DeleteMapping("/delete")
    @SecurityRequirement(name = "JWT")
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
