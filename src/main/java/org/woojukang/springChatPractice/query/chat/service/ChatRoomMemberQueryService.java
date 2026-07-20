package org.woojukang.springChatPractice.query.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woojukang.springChatPractice.domain.chat.entity.ChatRoomMember;
import org.woojukang.springChatPractice.query.chat.repository.ChatRoomMemberQueryRepository;

@Service
@RequiredArgsConstructor
public class ChatRoomMemberQueryService {

    private final ChatRoomMemberQueryRepository chatRoomMemberQueryRepository;

    public ChatRoomMember findByRoomId(Long roomId){

        return chatRoomMemberQueryRepository.findByRoomId(roomId);
    }

    // 단일 메소드 (facade사용 금지)
    @Transactional(readOnly = true)
    public boolean checkSubscriberWithRoomId(Long roomId,Long userId){

        return chatRoomMemberQueryRepository.checkSubscriberWithRoomId(roomId,userId);

    }
}
