package org.woojukang.springChatPractice.unit.domain.chat.facade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.woojukang.springChatPractice.domain.chat.dto.MessageType;
import org.woojukang.springChatPractice.domain.chat.dto.request.CreateChatRoomRequest;
import org.woojukang.springChatPractice.domain.chat.dto.request.DeleteChatRoomRequest;
import org.woojukang.springChatPractice.domain.chat.dto.request.SendChatMessageRequest;
import org.woojukang.springChatPractice.domain.chat.dto.response.CreateChatRoomResponse;
import org.woojukang.springChatPractice.domain.chat.dto.response.DeleteChatRoomResponse;
import org.woojukang.springChatPractice.domain.chat.dto.response.SendChatMessageResponse;
import org.woojukang.springChatPractice.domain.chat.entity.ChatMessage;
import org.woojukang.springChatPractice.domain.chat.entity.ChatRoom;
import org.woojukang.springChatPractice.domain.chat.facade.ChatFacade;
import org.woojukang.springChatPractice.domain.chat.service.ChatMessageService;
import org.woojukang.springChatPractice.domain.chat.service.ChatRoomMemberService;
import org.woojukang.springChatPractice.domain.chat.service.ChatRoomService;
import org.woojukang.springChatPractice.domain.user.entity.User;
import org.woojukang.springChatPractice.query.chat.service.ChatMessageQueryService;
import org.woojukang.springChatPractice.query.chat.service.ChatRoomMemberQueryService;
import org.woojukang.springChatPractice.query.chat.service.ChatRoomQueryService;
import org.woojukang.springChatPractice.query.user.service.UserQueryService;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatFacadeTest {

    @Mock
    private ChatRoomQueryService chatRoomQueryService;

    @Mock
    private ChatRoomMemberQueryService chatRoomMemberQueryService;

    @Mock
    private ChatMessageQueryService chatMessageQueryService;

    @Mock
    private UserQueryService userQueryService;

    @Mock
    private ChatRoomService chatRoomService;

    @Mock
    private ChatRoomMemberService chatRoomMemberService;

    @Mock
    private ChatMessageService chatMessageService;

    @InjectMocks
    private ChatFacade chatFacade;

    @Test
    @DisplayName("채팅방 생성 요청을 ChatRoomService에 위임")
    void createChatRoomSuccess() {

        // given
        CreateChatRoomRequest request =
                mock(CreateChatRoomRequest.class);

        CreateChatRoomResponse expectedResponse =
                mock(CreateChatRoomResponse.class);

        when(chatRoomService
                .createChatRoom(request))
                .thenReturn(expectedResponse);

        // when
        CreateChatRoomResponse result =
                chatFacade.createChatRoom(request);

        // then
        assertThat(result).isSameAs(expectedResponse);

        verify(chatRoomService)
                .createChatRoom(request);

        verifyNoInteractions(
                chatRoomQueryService,
                chatRoomMemberQueryService,
                chatMessageQueryService,
                userQueryService,
                chatRoomMemberService,
                chatMessageService
        );
    }

    @Test
    @DisplayName("채팅방 삭제 시 멤버 관계, 채팅방, 메시지를 순서대로 삭제")
    void deleteChatRoomSuccess() {

        // given
        Long roomId = 1L;

        String chatRoomName = "테스트 채팅방";

        Long deletedMessageCount = 3L;

        DeleteChatRoomRequest request =
                mock(DeleteChatRoomRequest.class);

        ChatRoom chatRoom =
                mock(ChatRoom.class);

        when(request
                .roomId())
                .thenReturn(roomId);

        when(chatRoomQueryService
                .findChatRoomByRoomId(roomId))
                .thenReturn(chatRoom);

        when(chatRoom
                .getId())
                .thenReturn(roomId);

        when(chatRoom
                .getChatRoomName())
                .thenReturn(chatRoomName);

        when(chatMessageQueryService
                .softDeleteAllMessageInChatRoom(roomId))
                .thenReturn(deletedMessageCount);

        // when
        DeleteChatRoomResponse result = chatFacade.deleteChatRoom(request);

        // then
        assertThat(result).isNotNull();

        InOrder inOrder = inOrder(
                chatRoomQueryService,
                chatRoomMemberQueryService,
                chatRoomService,
                chatMessageQueryService
        );

        inOrder.verify(chatRoomQueryService)
                .findChatRoomByRoomId(roomId);

        inOrder.verify(chatRoomMemberQueryService)
                .deleteAllChatRoomMemberByRoomId(roomId);

        inOrder.verify(chatRoomService)
                .deleteChatRoom(chatRoom);

        inOrder.verify(chatMessageQueryService)
                .softDeleteAllMessageInChatRoom(roomId);

        verifyNoInteractions(
                userQueryService,
                chatRoomMemberService,
                chatMessageService
        );
    }

    @Test
    @DisplayName("일반 채팅 메시지를 생성 , 저장 후, 채팅방에 발행")
    void publishMessageSuccess() {

        // given
        Long roomId = 1L;

        String username = "testUser";

        String messageContent = "안녕하세요";

        SendChatMessageRequest request =
                mock(SendChatMessageRequest.class);

        ChatRoom chatRoom =
                mock(ChatRoom.class);

        User sender =
                mock(User.class);

        ChatMessage chatMessage =
                mock(ChatMessage.class);

        SendChatMessageResponse response =
                mock(SendChatMessageResponse.class);

        when(request
                .message())
                .thenReturn(messageContent);

        when(chatRoomQueryService
                .findChatRoomByRoomId(roomId))
                .thenReturn(chatRoom);

        when(userQueryService
                .findByUsername(username))
                .thenReturn(sender);

        when(chatMessageService.createChatMessage(
                chatRoom,
                sender,
                messageContent
        )).thenReturn(chatMessage);

        when(chatMessageService.makeChatResponse(
                roomId,
                sender,
                chatMessage
        )).thenReturn(response);

        // when
        chatFacade.publishMessage(
                roomId,
                request,
                username
        );

        // then
        InOrder inOrder = inOrder(
                chatRoomQueryService,
                userQueryService,
                chatMessageService
        );

        inOrder.verify(chatRoomQueryService)
                .findChatRoomByRoomId(roomId);

        inOrder.verify(userQueryService)
                .findByUsername(username);

        inOrder.verify(chatMessageService)
                .createChatMessage(
                        chatRoom,
                        sender,
                        messageContent
                );

        inOrder.verify(chatMessageService)
                .saveMessage(chatMessage);

        inOrder.verify(chatMessageService)
                .makeChatResponse(
                        roomId,
                        sender,
                        chatMessage
                );

        inOrder.verify(chatMessageService)
                .publishMessage(
                        roomId,
                        response
                );

        verifyNoInteractions(
                chatRoomMemberQueryService,
                chatMessageQueryService,
                chatRoomService,
                chatRoomMemberService
        );
    }

    @Test
    @DisplayName("입장 시스템 메시지를 생성한 뒤, 채팅방에 발행")
    void publishEnterSystemMessageSuccess() {

        // given
        Long roomId = 1L;

        Long userId = 10L;

        String username = "testUser";

        String nickname = "테스트유저";

        String systemMessage = nickname + "님이 입장했습니다.";

        MessageType messageType = MessageType.ENTER;

        User participant =
                mock(User.class);

        when(userQueryService
                .findByUsername(username))
                .thenReturn(participant);

        when(participant
                .getId())
                .thenReturn(userId);

        when(participant
                .getNickname())
                .thenReturn(nickname);

        when(chatMessageService.createSystemMessage(
                messageType,
                nickname
        )).thenReturn(systemMessage);

        ArgumentCaptor<SendChatMessageResponse> responseCaptor =
                ArgumentCaptor.forClass(
                        SendChatMessageResponse.class
                );

        // when
        chatFacade.publishSystemMessage(
                roomId,
                username,
                messageType
        );

        // then
        verify(userQueryService)
                .findByUsername(username);

        verify(chatMessageService)
                .createSystemMessage(
                        messageType,
                        nickname
                );

        verify(chatMessageService)
                .publishMessage(
                        eq(roomId),
                        responseCaptor.capture()
                );

        SendChatMessageResponse response = responseCaptor.getValue();

        assertThat(response).isNotNull();

        verifyNoInteractions(
                chatRoomQueryService,
                chatRoomMemberQueryService,
                chatMessageQueryService,
                chatRoomService,
                chatRoomMemberService
        );
    }

    @Test
    @DisplayName("퇴장 시스템 메시지를 생성 후, 채팅방에 발행")
    void publishLeaveSystemMessageSuccess() {

        // given
        Long roomId = 1L;

        Long userId = 10L;

        String username = "testUser";

        String nickname = "테스트유저";

        String systemMessage = nickname + "님이 퇴장했습니다.";

        MessageType messageType = MessageType.LEAVE;

        User participant =
                mock(User.class);

        when(userQueryService
                .findByUsername(username))
                .thenReturn(participant);

        when(participant
                .getId())
                .thenReturn(userId);

        when(participant
                .getNickname())
                .thenReturn(nickname);

        when(chatMessageService.createSystemMessage(
                messageType,
                nickname
        )).thenReturn(systemMessage);

        ArgumentCaptor<SendChatMessageResponse> responseCaptor =
                ArgumentCaptor.forClass(
                        SendChatMessageResponse.class
                );

        // when
        chatFacade.publishSystemMessage(
                roomId,
                username,
                messageType
        );

        // then
        verify(userQueryService)
                .findByUsername(username);

        verify(chatMessageService)
                .createSystemMessage(
                        messageType,
                        nickname
                );

        verify(chatMessageService)
                .publishMessage(
                        eq(roomId),
                        responseCaptor.capture()
                );

        assertThat(responseCaptor
                .getValue())
                .isNotNull();

        verifyNoInteractions(
                chatRoomQueryService,
                chatRoomMemberQueryService,
                chatMessageQueryService,
                chatRoomService,
                chatRoomMemberService
        );
    }

    @Test
    @DisplayName("시스템 메시지는 DB에 저장하지 않고 바로 발행")
    void publishSystemMessageDoesNotSaveMessage() {

        // given
        Long roomId = 1L;

        String username = "testUser";

        String nickname = "테스트유저";

        User participant =
                mock(User.class);

        when(userQueryService
                .findByUsername(username))
                .thenReturn(participant);

        when(participant
                .getId())
                .thenReturn(10L);

        when(participant
                .getNickname())
                .thenReturn(nickname);

        when(chatMessageService.createSystemMessage(
                MessageType.ENTER,
                nickname
        )).thenReturn("테스트유저님이 입장했습니다.");

        // when
        chatFacade.publishSystemMessage(
                roomId,
                username,
                MessageType.ENTER
        );

        // then
        verify(chatMessageService, never())
                .saveMessage(any(ChatMessage.class));

        verify(chatMessageService)
                .publishMessage(
                        eq(roomId),
                        any(SendChatMessageResponse.class));
    }
}