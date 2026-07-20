package org.woojukang.springChatPractice.domain.chat.dto.response;

import java.time.Instant;

public record CreateChatRoomResponse (String chatRoomName,
                                      Instant createdAt,
                                      String message){

}
