package org.woojukang.springChatPractice.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.woojukang.springChatPractice.domain.chat.dto.request.CreateChatRoomRequest;
import org.woojukang.springChatPractice.domain.chat.dto.request.DeleteChatRoomRequest;
import org.woojukang.springChatPractice.domain.chat.dto.response.CreateChatRoomResponse;
import org.woojukang.springChatPractice.domain.chat.dto.response.DeleteChatRoomResponse;
import org.woojukang.springChatPractice.domain.chat.facade.ChatFacade;
import org.woojukang.springChatPractice.global.config.exception.dto.ApiResult;

@RestController
@RequestMapping("/api/v1/chat/room")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatFacade chatFacade;

    // 채팅방 생성하기
    @PostMapping("/create")
    public ResponseEntity<ApiResult<CreateChatRoomResponse>> createChatRoom
    (@AuthenticationPrincipal UserDetails userDetails,
     @RequestBody CreateChatRoomRequest request){

        return ResponseEntity
                .status(HttpStatus
                        .CREATED)
                .body(ApiResult
                        .success(chatFacade
                                .createChatRoom(request)));
    }



    // 채팅방 삭제하기
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResult<DeleteChatRoomResponse>> deleteChatRoom
    (@AuthenticationPrincipal UserDetails userDetails,
     @RequestBody DeleteChatRoomRequest request){

        return ResponseEntity
                .status(HttpStatus
                        .OK)
                .body(ApiResult
                        .success(chatFacade
                                .deleteChatRoom(request)));
    }


}
