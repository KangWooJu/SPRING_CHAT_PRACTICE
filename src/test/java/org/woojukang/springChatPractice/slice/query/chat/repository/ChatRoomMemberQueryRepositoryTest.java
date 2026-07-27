package org.woojukang.springChatPractice.slice.query.chat.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import org.woojukang.springChatPractice.domain.chat.entity.ChatRoom;
import org.woojukang.springChatPractice.domain.chat.entity.ChatRoomMember;
import org.woojukang.springChatPractice.domain.user.entity.User;
import org.woojukang.springChatPractice.global.config.persistence.QueryDslConfig;
import org.woojukang.springChatPractice.query.chat.repository.ChatRoomMemberQueryRepository;
import org.woojukang.springChatPractice.slice.query.chat.repository.helper.ChatDomainRepositoryTestHelper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({
        QueryDslConfig.class,
        ChatRoomMemberQueryRepository.class
})
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
@ActiveProfiles("test")
class ChatRoomMemberQueryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ChatRoomMemberQueryRepository chatRoomMemberQueryRepository;

    private ChatDomainRepositoryTestHelper helper;

    @BeforeEach
    void setUp() {
        helper = new ChatDomainRepositoryTestHelper(entityManager);
    }

    @Test
    @DisplayName("채팅방 id를 통해 해당 채팅방의 모든 회원을 삭제")
    void deleteAllChatMemberByRoomId() {

        // given
        User user1 = helper.createUser(
                "user1",
                "password1",
                "ROLE_USER",
                "nickname1",
                false
        );

        User user2 = helper.createUser(
                "user2",
                "password2",
                "ROLE_USER",
                "nickname2",
                false
        );

        User user3 = helper.createUser(
                "user3",
                "password3",
                "ROLE_USER",
                "nickname3",
                false
        );

        ChatRoom room1 = helper.createChatRoom(
                "room1",
                false
        );

        ChatRoom room2 = helper.createChatRoom(
                "room2",
                false
        );

        ChatRoomMember room1Member1 = helper.createChatRoomMember(
                room1,
                user1
        );

        ChatRoomMember room1Member2 = helper.createChatRoomMember(
                room1,
                user2
        );

        ChatRoomMember room2Member = helper.createChatRoomMember(
                room2,
                user3
        );

        Long room1Member1Id = room1Member1.getId();
        Long room1Member2Id = room1Member2.getId();
        Long room2MemberId = room2Member.getId();

        entityManager.flush();
        entityManager.clear();

        // when
        chatRoomMemberQueryRepository
                .deleteAllChatMemberByRoomId(room1.getId());

        entityManager.clear();

        // then
        ChatRoomMember deletedMember1 = entityManager.find(
                ChatRoomMember.class,
                room1Member1Id
        );

        ChatRoomMember deletedMember2 = entityManager.find(
                ChatRoomMember.class,
                room1Member2Id
        );

        ChatRoomMember remainingMember = entityManager.find(
                ChatRoomMember.class,
                room2MemberId
        );

        assertThat(deletedMember1)
                .isNull();

        assertThat(deletedMember2)
                .isNull();

        assertThat(remainingMember)
                .isNotNull();

        assertThat(remainingMember
                .getChatRoom()
                .getId())
                .isEqualTo(room2
                        .getId());
    }

    @Test
    @DisplayName("채팅방 id를 통해 해당 채팅방에 참여한 회원 목록 조회")
    void findByRoomId_success() {

        // given
        User user1 = helper.createUser(
                "user1",
                "password1",
                "ROLE_USER",
                "nickname1",
                false
        );

        User user2 = helper.createUser(
                "user2",
                "password2",
                "ROLE_USER",
                "nickname2",
                false
        );

        User otherRoomUser = helper.createUser(
                "user3",
                "password3",
                "ROLE_USER",
                "nickname3",
                false
        );

        ChatRoom targetRoom = helper.createChatRoom(
                "targetRoom",
                false
        );

        ChatRoom otherRoom = helper.createChatRoom(
                "otherRoom",
                false
        );

        ChatRoomMember targetMember1 = helper.createChatRoomMember(
                targetRoom,
                user1
        );

        ChatRoomMember targetMember2 = helper.createChatRoomMember(
                targetRoom,
                user2
        );

        helper.createChatRoomMember(
                otherRoom,
                otherRoomUser
        );

        entityManager.flush();
        entityManager.clear();

        // when
        List<ChatRoomMember> result = chatRoomMemberQueryRepository
                .findByRoomId(targetRoom
                        .getId());

        // then
        assertThat(result)
                .hasSize(2);

        assertThat(result)
                .extracting(ChatRoomMember::getId)
                .containsExactlyInAnyOrder(
                        targetMember1
                                .getId(),
                        targetMember2
                                .getId()
                );

        assertThat(result)
                .allSatisfy(member ->
                        assertThat(member
                                .getChatRoom()
                                .getId())
                                .isEqualTo(targetRoom
                                        .getId())
                );
    }

    @Test
    @DisplayName("회원이 없는 채팅방 id로 조회하면 빈 목록을 반환")
    void findByRoomId_empty() {

        // given
        ChatRoom emptyRoom = helper.createChatRoom(
                "emptyRoom",
                false
        );

        entityManager.flush();
        entityManager.clear();

        // when
        List<ChatRoomMember> result = chatRoomMemberQueryRepository
                .findByRoomId(emptyRoom
                        .getId());

        // then
        assertThat(result)
                .isEmpty();
    }

    @Test
    @DisplayName("사용자가 해당 채팅방의 참여자일 경우, true를 반환")
    void checkSubscriberWithRoomId_true() {

        // given
        User user = helper.createUser(
                "user1",
                "password1",
                "ROLE_USER",
                "nickname1",
                false
        );

        ChatRoom chatRoom = helper.createChatRoom(
                "chatRoom",
                false
        );

        helper.createChatRoomMember(
                chatRoom,
                user
        );

        entityManager.flush();
        entityManager.clear();

        // when
        boolean result = chatRoomMemberQueryRepository
                .checkSubscriberWithRoomId(
                        chatRoom
                                .getId(),
                        user
                                .getId()
                );

        // then
        assertThat(result)
                .isTrue();
    }

    @Test
    @DisplayName("사용자가 해당 채팅방의 참여자가 아닐경우, false를 반환")
    void checkSubscriberWithRoomId_false() {

        // given
        User member = helper.createUser(
                "member",
                "password1",
                "ROLE_USER",
                "memberNickname",
                false
        );

        User nonMember = helper.createUser(
                "nonMember",
                "password2",
                "ROLE_USER",
                "nonMemberNickname",
                false
        );

        ChatRoom chatRoom = helper.createChatRoom(
                "chatRoom",
                false
        );

        helper.createChatRoomMember(
                chatRoom,
                member
        );

        entityManager.flush();
        entityManager.clear();

        // when
        boolean result = chatRoomMemberQueryRepository
                .checkSubscriberWithRoomId(
                        chatRoom.getId(),
                        nonMember.getId()
                );

        // then
        assertThat(result)
                .isFalse();
    }

    @Test
    @DisplayName("사용자가 다른 채팅방의 참여자인 경우, false를 반환")
    void checkSubscriberWithRoomId_differentRoom() {

        // given
        User user = helper.createUser(
                "user1",
                "password1",
                "ROLE_USER",
                "nickname1",
                false
        );

        ChatRoom joinedRoom = helper.createChatRoom(
                "joinedRoom",
                false
        );

        ChatRoom otherRoom = helper.createChatRoom(
                "otherRoom",
                false
        );

        helper.createChatRoomMember(
                joinedRoom,
                user
        );

        entityManager.flush();
        entityManager.clear();

        // when
        boolean result = chatRoomMemberQueryRepository
                .checkSubscriberWithRoomId(
                        otherRoom
                                .getId(),
                        user
                                .getId()
                );

        // then
        assertThat(result)
                .isFalse();
    }
}