package org.woojukang.springChatPractice.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.woojukang.springChatPractice.domain.chat.entity.ChatRoomMember;
import org.woojukang.springChatPractice.domain.chat.repository.ChatRoomMemberRepository;

@Service
@RequiredArgsConstructor
public class ChatRoomMemberService {

    private final ChatRoomMemberRepository chatRoomMemberRepository;

    public void deleteChatRoomMember(ChatRoomMember chatRoomMember){
        chatRoomMemberRepository.delete(chatRoomMember);
    }
}
