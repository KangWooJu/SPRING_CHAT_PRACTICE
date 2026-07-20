package org.woojukang.springChatPractice.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.woojukang.springChatPractice.domain.chat.dto.request.AddChatUserRequest;
import org.woojukang.springChatPractice.domain.chat.dto.request.CreateChatRoomRequest;
import org.woojukang.springChatPractice.domain.chat.dto.request.DeleteChatUserRequest;
import org.woojukang.springChatPractice.domain.chat.dto.response.AddChatUserResponse;
import org.woojukang.springChatPractice.domain.chat.dto.response.CreateChatRoomResponse;
import org.woojukang.springChatPractice.domain.chat.dto.response.DeleteChatUserResponse;
import org.woojukang.springChatPractice.domain.chat.entity.ChatRoom;
import org.woojukang.springChatPractice.domain.chat.entity.ChatRoomMember;
import org.woojukang.springChatPractice.domain.chat.repository.ChatRoomMemberRepository;
import org.woojukang.springChatPractice.domain.chat.repository.ChatRoomRepository;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

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
                        .getChatRoomName(),
                chatRoom
                        .getCreatedAt(),
                "채팅방이 생성되었습니다.");
    }

    // 채팅방 인원 추가
    public AddChatUserResponse addChatUser
    (AddChatUserRequest request){

        ChatRoomMember chatRoomMember = ChatRoomMember
                .builder()
                .chatRoom(request.chatRoom())
                .user(request.user())
                .build();

        String username = request.user().getUsername();

        return new AddChatUserResponse(
                username,
                request
                        .chatRoom()
                        .getId(),
                username+"님이 채팅방에 입장하셨습니다.");

    }

    // 채팅방 인원 삭제
    public DeleteChatUserResponse deleteChatUser
    (DeleteChatUserRequest request){

        chatRoomMemberRepository
                .delete(request.chatRoomMember());

        return new DeleteChatUserResponse(request.chatRoom().getId(),
                request.user().getUsername(),
                request.user().getUsername() + "님이 퇴장하셨습니다.");
    }

    // 채팅방 유저 이름 중복 확인 ( QueryDSL )

}
