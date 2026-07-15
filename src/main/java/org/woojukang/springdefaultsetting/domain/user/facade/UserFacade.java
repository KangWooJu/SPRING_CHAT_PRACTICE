package org.woojukang.springdefaultsetting.domain.user.facade;


import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.woojukang.springdefaultsetting.domain.user.dto.request.UserCreateRequest;
import org.woojukang.springdefaultsetting.domain.user.dto.response.UserCreateResponse;
import org.woojukang.springdefaultsetting.domain.user.dto.response.UserDeleteResponse;
import org.woojukang.springdefaultsetting.domain.user.entity.User;
import org.woojukang.springdefaultsetting.domain.user.service.UserService;
import org.woojukang.springdefaultsetting.global.security.dto.UserAuthCache;
import org.woojukang.springdefaultsetting.query.user.service.UserQueryService;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final UserQueryService userQueryService;


    @Transactional
    public UserCreateResponse create(UserCreateRequest userCreateRequest){

        // 유저 생성
        User user = userService.create(userCreateRequest);

        // DB에 저장
        userService.save(user);

        return new UserCreateResponse(user.getUsername(),
                LocalDateTime.now(),
                LocalDateTime.now());
    }


    @Transactional
    public UserDeleteResponse delete(String username){

        User user = userQueryService
                .findByUsername(username);

        // DB 정보 삭제
        userService.delete(user);

        // Redis 토큰 삭제 ( 추가 필요 )

        return new UserDeleteResponse(user.getUsername(),
                user.getDeletedAt());
    }

    @Transactional(readOnly = true)
    public UserAuthCache findByUsername(String username){

        return userQueryService
                .findByUsernameInCache(username);
    }



}
