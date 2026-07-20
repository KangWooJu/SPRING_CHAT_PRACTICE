package org.woojukang.springChatPractice.domain.chat.dto.request;

import org.woojukang.springChatPractice.domain.chat.entity.ChatRoom;
import org.woojukang.springChatPractice.domain.user.entity.User;

public record AddChatUserRequest(ChatRoom chatRoom,
                                 User user) {
}
