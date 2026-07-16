package org.woojukang.springChatPractice.query.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.woojukang.springChatPractice.domain.chat.entity.ChatRoomMember;
import org.woojukang.springChatPractice.query.chat.repository.ChatRoomMemberQueryRepository;

@Service
@RequiredArgsConstructor
public class ChatRoomMemberQueryService {

    private final ChatRoomMemberQueryRepository chatRoomMemberQueryRepository;

    public ChatRoomMember findByRoomId(Long roomId){

        return chatRoomMemberQueryRepository.findByRoomId(roomId);
    }
}
