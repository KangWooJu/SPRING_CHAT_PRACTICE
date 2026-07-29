package org.woojukang.springChatPractice.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.woojukang.springChatPractice.domain.chat.dto.request.CreateChatRoomRequest;
import org.woojukang.springChatPractice.domain.chat.dto.response.CreateChatRoomResponse;
import org.woojukang.springChatPractice.domain.chat.entity.ChatRoom;
import org.woojukang.springChatPractice.domain.chat.repository.ChatRoomRepository;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    // Chat Room 삭제하기 (soft)
    public void deleteChatRoom(ChatRoom chatRoom){
        chatRoom.delete();
    }


    // room name으로 채팅방 만들기
    public CreateChatRoomResponse createChatRoom
    (CreateChatRoomRequest request){

        ChatRoom chatRoom = ChatRoom
                .builder()
                .chatRoomName(request.chatRoomName())
                .deleted(false)
                .build();

        chatRoomRepository.save(chatRoom);

        return new CreateChatRoomResponse(
                chatRoom
                        .getId(),
                chatRoom
                        .getChatRoomName(),
                chatRoom
                        .getCreatedAt(),
                "채팅방이 생성되었습니다.");
    }
    // 채팅방 유저 이름 중복 확인 ( QueryDSL )
}
