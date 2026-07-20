package org.woojukang.springChatPractice.query.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.woojukang.springChatPractice.domain.user.entity.QUser;
import org.woojukang.springChatPractice.domain.user.entity.User;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class UserQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QUser user = QUser.user;

    public Optional<User> findByUsername(String username) {

        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(user)
                        .where(user.username.eq(username))
                        .fetchOne()

        );

    }


    // nickname 중복 체크하기
    public boolean existsByNickname(String nickname){

        Integer result = jpaQueryFactory
                .selectOne()
                .from(user)
                .where(user.nickname.eq(nickname))
                .fetchFirst();

        return result != null;
    }

}
