package org.woojukang.springChatPractice.global.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.woojukang.springChatPractice.domain.user.entity.User;
import org.woojukang.springChatPractice.global.security.dto.UserAuthCache;
import org.woojukang.springChatPractice.query.user.service.UserQueryService;


@Service
@RequiredArgsConstructor
public class AuthPrincipalServiceImpl implements UserDetailsService {

    private final UserQueryService userQueryService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userQueryService.findByUsername(username);

        UserAuthCache userAuthCache = new UserAuthCache(
                user
                        .getId(),
                user
                        .getUsername(),
                user
                        .getRole());

        return new AuthPrincipal(userAuthCache);
    }
}
