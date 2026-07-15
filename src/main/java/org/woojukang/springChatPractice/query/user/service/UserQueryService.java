package org.woojukang.springChatPractice.query.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.woojukang.springChatPractice.domain.user.entity.User;
import org.woojukang.springChatPractice.global.config.exception.BaseExceptionEnum;
import org.woojukang.springChatPractice.global.config.exception.domain.BaseException;
import org.woojukang.springChatPractice.global.security.dto.UserAuthCache;
import org.woojukang.springChatPractice.global.security.repository.UserAuthCacheRepository;
import org.woojukang.springChatPractice.query.user.repository.UserQueryRepository;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserQueryRepository userQueryRepository;
    private final UserAuthCacheRepository userAuthCacheRepository;

    // RDB에서 조회
    public User findByUsername(String username){

        return userQueryRepository
                .findByUsername(username)
                .orElseThrow(()-> new BaseException(BaseExceptionEnum
                        .USER_NOT_FOUND));

    }

    // Redis에서 조회
    public UserAuthCache findByUsernameInCache(String username){

        return userAuthCacheRepository
                .findByUsername(username)
                .orElseThrow(()-> new BaseException(BaseExceptionEnum
                        .USER_NOT_FOUND));

    }
}
