package org.woojukang.springChatPractice.domain.chat.dto;

import java.time.Instant;


public record ChatDTO(MessageType type,
                      Long roomId,
                      Long senderId,
                      String senderNickname,
                      String message,
                      Instant sendTime) {


}
