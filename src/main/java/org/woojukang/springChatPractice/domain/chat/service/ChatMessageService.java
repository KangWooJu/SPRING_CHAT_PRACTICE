package org.woojukang.springChatPractice.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.woojukang.springChatPractice.domain.chat.dto.ChatDTO;
import org.woojukang.springChatPractice.domain.chat.repository.ChatMessageRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void publishMessage(Long id, ChatDTO chatDTO){

        messagingTemplate.convertAndSend("/sub/api/v1/chat/"+ id,chatDTO);

    }




}
