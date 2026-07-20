package org.woojukang.springChatPractice.domain.chat.dto.request;

import org.woojukang.springChatPractice.domain.chat.entity.ChatRoom;
import org.woojukang.springChatPractice.domain.chat.entity.ChatRoomMember;
import org.woojukang.springChatPractice.domain.user.entity.User;

public record DeleteChatUserRequest (ChatRoomMember chatRoomMember,
                                    ChatRoom chatRoom,
                                    User user){
}
