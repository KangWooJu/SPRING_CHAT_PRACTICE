package org.woojukang.springChatPractice.unit.domain.chat.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
import org.woojukang.springChatPractice.domain.chat.service.ChatRoomService;
import org.woojukang.springChatPractice.domain.user.entity.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatRoomServiceTest {

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @Mock
    private ChatRoomMemberRepository chatRoomMemberRepository;

    @InjectMocks
    private ChatRoomService chatRoomService;

    @Test
    @DisplayName("채팅방을 삭제 상태로 변경")
    void deleteChatRoom() {

        // given
        ChatRoom chatRoom = mock(ChatRoom.class);

        // when
        chatRoomService.deleteChatRoom(chatRoom);

        // then
        verify(chatRoom).delete();
    }

    @Test
    @DisplayName("채팅방을 생성 후, 생성 응답을 반환")
    void createChatRoom() {

        // given
        CreateChatRoomRequest request =
                new CreateChatRoomRequest(
                        "테스트 채팅방"
                );

        ArgumentCaptor<ChatRoom> chatRoomCaptor =
                ArgumentCaptor.forClass(ChatRoom.class);

        // when
        CreateChatRoomResponse result =
                chatRoomService.createChatRoom(request);

        // then
        verify(chatRoomRepository)
                .save(chatRoomCaptor
                        .capture());

        ChatRoom savedChatRoom =
                chatRoomCaptor.getValue();

        assertThat(savedChatRoom
                .getChatRoomName())
                .isEqualTo("테스트 채팅방");

        assertThat(savedChatRoom
                .isDeleted())
                .isFalse();

        assertThat(result
                .chatRoomName())
                .isEqualTo("테스트 채팅방");

        assertThat(result
                .createdAt())
                .isEqualTo(savedChatRoom.getCreatedAt());

        assertThat(result
                .message())
                .isEqualTo("채팅방이 생성되었습니다.");
    }

    @Test
    @DisplayName("채팅방에 사용자를 추가 후, 입장 응답을 반환")
    void addChatUser() {

        // given
        Long chatRoomId = 1L;
        String username = "woojuice";

        ChatRoom chatRoom = mock(ChatRoom.class);
        User user = mock(User.class);

        when(chatRoom
                .getId())
                .thenReturn(chatRoomId);

        when(user
                .getUsername())
                .thenReturn(username);

        AddChatUserRequest request =
                new AddChatUserRequest(
                        chatRoom,
                        user
                );

        // when
        AddChatUserResponse result =
                chatRoomService.addChatUser(request);

        // then
        assertThat(result
                .username())
                .isEqualTo(username);

        assertThat(result
                .chatRoomId())
                .isEqualTo(chatRoomId);

        assertThat(result
                .message())
                .isEqualTo(
                        "woojuice님이 채팅방에 입장하셨습니다."
                );
    }

    @Test
    @DisplayName("채팅방 사용자를 삭제 후, 퇴장 응답을 반환")
    void deleteChatUser() {

        // given
        Long chatRoomId = 1L;
        String username = "woojuice";

        ChatRoomMember chatRoomMember =
                mock(ChatRoomMember.class);

        ChatRoom chatRoom =
                mock(ChatRoom.class);

        User user =
                mock(User.class);

        when(chatRoom
                .getId())
                .thenReturn(chatRoomId);

        when(user
                .getUsername())
                .thenReturn(username);

        DeleteChatUserRequest request =
                new DeleteChatUserRequest(
                        chatRoomMember,
                        chatRoom,
                        user
                );

        // when
        DeleteChatUserResponse result =
                chatRoomService.deleteChatUser(request);

        // then
        verify(chatRoomMemberRepository)
                .delete(chatRoomMember);

        assertThat(result.chatRoomId())
                .isEqualTo(chatRoomId);

        assertThat(result.username())
                .isEqualTo(username);

        assertThat(result.message())
                .isEqualTo(
                        "woojuice님이 퇴장하셨습니다."
                );
    }
}