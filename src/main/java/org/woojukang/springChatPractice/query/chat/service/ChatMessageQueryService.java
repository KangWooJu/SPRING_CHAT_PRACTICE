package org.woojukang.springChatPractice.query.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.woojukang.springChatPractice.query.chat.repository.ChatMessageQueryRepository;

@Service
@RequiredArgsConstructor
public class ChatMessageQueryService {

    private final ChatMessageQueryRepository chatMessageQueryRepository;

    public Long softDeleteAllMessageInChatRoom(Long roomId){

        return chatMessageQueryRepository.softDeleteAllMessageInChatRoom(roomId);
    }
}
