package org.woojukang.springChatPractice.domain.chat.facade;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.woojukang.springChatPractice.domain.chat.dto.request.CreateChatRoomRequest;
import org.woojukang.springChatPractice.domain.chat.dto.request.DeleteChatRoomRequest;
import org.woojukang.springChatPractice.domain.chat.dto.response.CreateChatRoomResponse;
import org.woojukang.springChatPractice.domain.chat.dto.response.DeleteChatRoomResponse;
import org.woojukang.springChatPractice.domain.chat.entity.ChatRoom;
import org.woojukang.springChatPractice.domain.chat.service.ChatMessageService;
import org.woojukang.springChatPractice.domain.chat.service.ChatRoomMemberService;
import org.woojukang.springChatPractice.domain.chat.service.ChatRoomService;
import org.woojukang.springChatPractice.query.chat.service.ChatMessageQueryService;
import org.woojukang.springChatPractice.query.chat.service.ChatRoomMemberQueryService;
import org.woojukang.springChatPractice.query.chat.service.ChatRoomQueryService;

@Component
@RequiredArgsConstructor
public class ChatFacade {

    private final ChatRoomQueryService chatRoomQueryService;
    private final ChatRoomMemberQueryService chatRoomMemberQueryService;
    private final ChatMessageQueryService chatMessageQueryService;

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




}
