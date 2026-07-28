package org.woojukang.springChatPractice.query.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woojukang.springChatPractice.domain.chat.entity.ChatRoomMember;
import org.woojukang.springChatPractice.global.config.exception.WebSocketExceptionEnum;
import org.woojukang.springChatPractice.global.config.exception.domain.BaseException;
import org.woojukang.springChatPractice.query.chat.repository.ChatRoomMemberQueryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomMemberQueryService {

    private final ChatRoomMemberQueryRepository chatRoomMemberQueryRepository;

    public void deleteAllChatRoomMemberByRoomId(Long roomId){

        chatRoomMemberQueryRepository.deleteAllChatMemberByRoomId(roomId);
    }

    public List<ChatRoomMember> findByRoomId(Long roomId){

        return chatRoomMemberQueryRepository.findByRoomId(roomId);
    }

    public ChatRoomMember findByMemberId(Long memberId){

        return chatRoomMemberQueryRepository
                .findByMemberId(memberId)
                .orElseThrow(()-> new BaseException(WebSocketExceptionEnum
                        .SUBSCRIBER_NOT_MATCHED));
    }

    // 단일 메소드 (facade사용 금지)
    @Transactional(readOnly = true)
    public boolean checkSubscriberWithRoomId(Long roomId,Long userId){

        return chatRoomMemberQueryRepository.checkSubscriberWithRoomId(roomId,userId);

    }
}
