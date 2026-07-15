package org.woojukang.springChatPractice.slice.global.security.repository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.woojukang.springChatPractice.global.security.dto.UserAuthCache;
import org.woojukang.springChatPractice.global.security.repository.UserAuthCacheRepository;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserAuthCacheRepositoryTest {

    @Mock
    private RedisTemplate<String,UserAuthCache> userAuthredisTemplate;

    @Mock
    private ValueOperations<String,UserAuthCache> valueOperations;

    @InjectMocks
    private UserAuthCacheRepository userAuthCacheRepository;
    

}