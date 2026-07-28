package org.woojukang.springChatPractice.query.chat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.woojukang.springChatPractice.domain.chat.entity.ChatRoomMember;
import org.woojukang.springChatPractice.domain.chat.entity.QChatRoomMember;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatRoomMemberQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QChatRoomMember chatRoomMember = QChatRoomMember.chatRoomMember;

    public void deleteAllChatMemberByRoomId(Long roomId){

         jpaQueryFactory
                 .delete(chatRoomMember)
                 .where(chatRoomMember
                         .chatRoom
                         .id
                         .eq(roomId))
                 .execute();
    }

    public List<ChatRoomMember> findByRoomId(Long roomId){

        return jpaQueryFactory
                .selectFrom(chatRoomMember)
                .where(chatRoomMember
                        .chatRoom
                        .id
                        .eq(roomId))
                .fetch();
    }

    public Optional<ChatRoomMember> findByMemberId(Long memberId){

        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(chatRoomMember)
                        .where(chatRoomMember
                                .user
                                .id
                                .eq(memberId))
                        .fetchOne()
        );
    }

    public boolean checkSubscriberWithRoomId(Long roomId,Long userId){

        Integer result = jpaQueryFactory
                .selectOne()
                .from(chatRoomMember)
                .where(
                        chatRoomMember.chatRoom.id.eq(roomId),
                        chatRoomMember.user.id.eq(userId)
                )
                .fetchFirst();

        return result != null;
    }
}
