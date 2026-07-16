package org.woojukang.springChatPractice.query.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.woojukang.springChatPractice.domain.chat.entity.ChatRoom;
import org.woojukang.springChatPractice.global.config.exception.BaseExceptionEnum;
import org.woojukang.springChatPractice.global.config.exception.WebSocketExceptionEnum;
import org.woojukang.springChatPractice.global.config.exception.domain.BaseException;
import org.woojukang.springChatPractice.query.chat.repository.ChatRoomQueryRepository;

@Service
@RequiredArgsConstructor
public class ChatRoomQueryService {

    private final ChatRoomQueryRepository chatRoomQueryRepository;

    // roomId 기준으로 채팅방 찾기
    public ChatRoom findChatRoomByRoomId(Long roomId){

        return chatRoomQueryRepository
                .findByRoomId(roomId)
                .orElseThrow(()->
                        new BaseException(WebSocketExceptionEnum
                                .CHATROOM_NOT_FOUND));
    }

}
