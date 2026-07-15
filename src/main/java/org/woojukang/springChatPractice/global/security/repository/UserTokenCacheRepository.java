package org.woojukang.springChatPractice.global.security.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.woojukang.springChatPractice.global.config.exception.BaseExceptionEnum;
import org.woojukang.springChatPractice.global.config.exception.domain.BaseException;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class UserTokenCacheRepository {

    private final RedisTemplate<String,String> StringRedisTemplate;

    // 저장
    public void save(String key,
                     String value,
                     Long expiredMs){

        StringRedisTemplate
                .opsForValue()
                .set(key,
                        value,
                        expiredMs,
                        TimeUnit.MILLISECONDS);

    }

    public Object findByKey(String key){

        Object value = StringRedisTemplate.opsForValue().get(key);

        if(value == null){
            throw new BaseException(BaseExceptionEnum.REFRESH_TOKEN_NOT_FOUND);
        }

        return value;
    }

    public void delete(String key){
        StringRedisTemplate.delete(key);
    }

    public boolean exists(String key){
        return StringRedisTemplate
                .hasKey(key);
    }

}
