package org.woojukang.springChatPractice.domain.chat.facade;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.woojukang.springChatPractice.domain.chat.dto.MessageType;
import org.woojukang.springChatPractice.domain.chat.dto.request.CreateChatRoomRequest;
import org.woojukang.springChatPractice.domain.chat.dto.request.DeleteChatRoomRequest;
import org.woojukang.springChatPractice.domain.chat.dto.request.SendChatMessageRequest;
import org.woojukang.springChatPractice.domain.chat.dto.response.CreateChatRoomResponse;
import org.woojukang.springChatPractice.domain.chat.dto.response.DeleteChatRoomResponse;
import org.woojukang.springChatPractice.domain.chat.dto.response.SendChatMessageResponse;
import org.woojukang.springChatPractice.domain.chat.entity.ChatMessage;
import org.woojukang.springChatPractice.domain.chat.entity.ChatRoom;
import org.woojukang.springChatPractice.domain.chat.service.ChatMessageService;
import org.woojukang.springChatPractice.domain.chat.service.ChatRoomMemberService;
import org.woojukang.springChatPractice.domain.chat.service.ChatRoomService;
import org.woojukang.springChatPractice.domain.user.entity.User;
import org.woojukang.springChatPractice.query.chat.service.ChatMessageQueryService;
import org.woojukang.springChatPractice.query.chat.service.ChatRoomMemberQueryService;
import org.woojukang.springChatPractice.query.chat.service.ChatRoomQueryService;
import org.woojukang.springChatPractice.query.user.service.UserQueryService;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ChatFacade {

    private final ChatRoomQueryService chatRoomQueryService;
    private final ChatRoomMemberQueryService chatRoomMemberQueryService;
    private final ChatMessageQueryService chatMessageQueryService;

    private final UserQueryService userQueryService;

    private final ChatRoomService chatRoomService;
    private final ChatRoomMemberService chatRoomMemberService;
    private final ChatMessageService chatMessageService;

    // 나의 채팅방 리스트 띄우기


    // 채팅방 생성하기
    @Transactional
    public CreateChatRoomResponse createChatRoom
    (CreateChatRoomRequest request){

        return chatRoomService.createChatRoom(request);
    }



    // 채팅방 삭제하기
    @Transactional
    public DeleteChatRoomResponse deleteChatRoom
    (DeleteChatRoomRequest request){

        // roomId로 채팅방 조회
        ChatRoom chatRoom = chatRoomQueryService
                .findChatRoomByRoomId(request
                        .roomId());

        // chatRoomMember 관계 삭제(hard)
        chatRoomMemberService
                .deleteChatRoomMember(chatRoomMemberQueryService
                        .findByRoomId(request
                                .roomId()));

        // 채팅방 삭제(soft)
        chatRoomService.deleteChatRoom(chatRoom);

        // 채팅방 대화내역 삭제(soft)
        Long deletedCount = chatMessageQueryService
                .softDeleteAllMessageInChatRoom(request
                        .roomId());

        return new DeleteChatRoomResponse(
                request.roomId(),
                chatRoom.getChatRoomName(),
                "삭제 보관된 메시지 갯수: "+deletedCount,
                "채팅방이 삭제되었습니다.");

    }

    // 채팅방 메시지 전달하기
    @Transactional
    public void publishMessage(Long roomId,
                               SendChatMessageRequest request,
                               String username){

        // ChatRoom 조회
        ChatRoom chatRoom =
                chatRoomQueryService.findChatRoomByRoomId(roomId);

        // User 조회
        User sender = userQueryService
                .findByUsername(username);

        // ChatMessage 객체 생성
        ChatMessage chatMessage =
                chatMessageService.createChatMessage(
                        chatRoom,
                        sender,
                        request
                                .message());

        // chatMessage 객체 저장
        chatMessageService.saveMessage(chatMessage);

        // publish 메소드 호출
        chatMessageService
                .publishMessage(
                        roomId,
                        chatMessageService
                                .makeChatResponse(
                                        roomId,
                                        sender,
                                        chatMessage));
    }

    public void publishSystemMessage(Long roomId,
                                     String username,
                                     MessageType messageType){


        // 참여자 객체(user) 가져오기
        User participant = userQueryService.findByUsername(username);

        // messageType에 따른 시스템 메시지 생성
        String systemMessage = chatMessageService
                .createSystemMessage(messageType, participant.getNickname());

        // response객체 생성
        SendChatMessageResponse response = new SendChatMessageResponse(
                messageType,
                roomId,
                participant.getId(),
                participant.getNickname(),
                systemMessage,
                Instant.now()
        );

        // 해당 방에 메시지 전송
        chatMessageService
                .publishMessage(roomId,response);
    }
}
