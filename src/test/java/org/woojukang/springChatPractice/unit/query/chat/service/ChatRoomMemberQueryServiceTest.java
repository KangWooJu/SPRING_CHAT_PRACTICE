package org.woojukang.springChatPractice.unit.query.chat.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import org.woojukang.springChatPractice.domain.chat.entity.ChatRoomMember;

import org.woojukang.springChatPractice.query.chat.repository.ChatRoomMemberQueryRepository;
import org.woojukang.springChatPractice.query.chat.service.ChatRoomMemberQueryService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatRoomMemberQueryServiceTest {

    @Mock
    private ChatRoomMemberQueryRepository chatRoomMemberQueryRepository;

    @InjectMocks
    private ChatRoomMemberQueryService chatRoomMemberQueryService;

    @Test
    @DisplayName("채팅방 id를 통한 채팅방 멤버 조회")
    void findByRoomId() {

        // given
        Long roomId = 1L;

        ChatRoomMember firstMember = mock(ChatRoomMember.class);

        ChatRoomMember secondMember = mock(ChatRoomMember.class);

        List<ChatRoomMember> chatRoomMembers = List.of(
                firstMember,
                secondMember
        );

        when(chatRoomMemberQueryRepository
                .findByRoomId(roomId))
                .thenReturn(chatRoomMembers);


        // when
        List<ChatRoomMember> result = chatRoomMemberQueryService
                .findByRoomId(roomId);

        // then
        assertThat(result)
                .isSameAs(chatRoomMembers);

        assertThat(result)
                .hasSize(2)
                .containsExactly(
                        firstMember,
                        secondMember);

        verify(chatRoomMemberQueryRepository)
                .findByRoomId(roomId);
    }

    @Test
    @DisplayName("사용자가 채팅방 구독자인 경우, true 반환")
    void checkSubscriberWithRoomIdSuccess() {

        // given
        Long roomId = 1L;

        Long userId = 10L;

        when(chatRoomMemberQueryRepository
                .checkSubscriberWithRoomId(roomId, userId))
                .thenReturn(true);

        // when
        boolean result = chatRoomMemberQueryService
                .checkSubscriberWithRoomId(roomId, userId);

        // then
        assertThat(result)
                .isTrue();

        verify(chatRoomMemberQueryRepository)
                .checkSubscriberWithRoomId(roomId,
                        userId);
    }

    @Test
    @DisplayName("사용자가 채팅방 구독자가 아닌 경우, false 반환")
    void checkSubscriberWithRoomIdFail() {

        // given
        Long roomId = 1L;

        Long userId = 10L;

        when(chatRoomMemberQueryRepository
                .checkSubscriberWithRoomId(roomId, userId))
                .thenReturn(false);

        // when
        boolean result = chatRoomMemberQueryService
                .checkSubscriberWithRoomId(roomId, userId);

        // then
        assertThat(result)
                .isFalse();

        verify(chatRoomMemberQueryRepository)
                .checkSubscriberWithRoomId(roomId, userId);
    }

    @Test
    @DisplayName("채팅방 id를 통한 채팅방 멤버 전체 삭제")
    void deleteAllChatRoomMemberByRoomId() {

        // given
        Long roomId = 1L;

        // when
        chatRoomMemberQueryService
                .deleteAllChatRoomMemberByRoomId(roomId);

        // then
        verify(chatRoomMemberQueryRepository)
                .deleteAllChatMemberByRoomId(roomId);

    }
}