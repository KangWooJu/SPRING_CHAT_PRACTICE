package org.woojukang.springChatPractice.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.woojukang.springChatPractice.domain.chat.dto.ChatDTO;

@Controller
@RequiredArgsConstructor
@MessageMapping("/api/v1/chat")
public class ChatController {

    @MessageMapping("/{roomId}/send")
    public void sendMessage(@DestinationVariable Long roomId,
                            @Payload ChatDTO chatDTO){

    }

    @MessageMapping("/{roomId}/enter")
    public void enter(){

    }

    @MessageMapping("/{roomId}/leave")
    public void leave(){

    }
}
