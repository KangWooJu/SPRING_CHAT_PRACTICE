package org.woojukang.springChatPractice.unit.domain.chat.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.woojukang.springChatPractice.domain.chat.dto.request.CreateChatRoomRequest;
import org.woojukang.springChatPractice.domain.chat.dto.response.CreateChatRoomResponse;
import org.woojukang.springChatPractice.domain.chat.entity.ChatRoom;
import org.woojukang.springChatPractice.domain.chat.repository.ChatRoomRepository;
import org.woojukang.springChatPractice.domain.chat.service.ChatRoomService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChatRoomServiceTest {

    @Mock
    private ChatRoomRepository chatRoomRepository;

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
    @DisplayName("채팅방을 생성하고 생성 응답을 반환")
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
                .save(chatRoomCaptor.capture());

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
}