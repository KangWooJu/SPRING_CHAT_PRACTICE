package org.woojukang.springChatPractice.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.woojukang.springChatPractice.domain.chat.dto.request.SendChatMessageRequest;
import org.woojukang.springChatPractice.domain.chat.facade.ChatFacade;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@MessageMapping("/api/v1/chat")
public class ChatController {

    private final ChatFacade chatFacade;

    @MessageMapping("/{roomId}/send")
    public void sendMessage(@DestinationVariable Long roomId,
                            @Payload SendChatMessageRequest request,
                            Principal principal){

        chatFacade
                .publishMessage(
                        roomId,
                        request,
                        principal
                                .getName());

    }

    @MessageMapping("/{roomId}/enter")
    public void enter(){

    }

    @MessageMapping("/{roomId}/leave")
    public void leave(){

    }
}
