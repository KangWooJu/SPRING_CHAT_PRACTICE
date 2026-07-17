package org.woojukang.springChatPractice.query.chat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.woojukang.springChatPractice.domain.chat.entity.ChatRoomMember;
import org.woojukang.springChatPractice.domain.chat.entity.QChatRoomMember;

@Repository
@RequiredArgsConstructor
public class ChatRoomMemberQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QChatRoomMember chatRoomMember = QChatRoomMember.chatRoomMember;

    public ChatRoomMember findByRoomId(Long roomId){

        return jpaQueryFactory
                .selectFrom(chatRoomMember)
                .where(chatRoomMember
                        .id
                        .eq(roomId))
                .fetchOne();
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
