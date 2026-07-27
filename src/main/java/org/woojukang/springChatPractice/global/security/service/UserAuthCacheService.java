package org.woojukang.springChatPractice.global.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.woojukang.springChatPractice.global.security.dto.UserAuthCache;
import org.woojukang.springChatPractice.global.security.repository.UserAuthCacheRepository;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserAuthCacheService {

    private final UserAuthCacheRepository userAuthCacheRepository;

    // Redis에 UserAuthCache 저장
    public void saveUserAuthCache(UserAuthCache userAuthCache){
        userAuthCacheRepository
                .save(userAuthCache.username(), userAuthCache,2_100_000L);
    }
}
