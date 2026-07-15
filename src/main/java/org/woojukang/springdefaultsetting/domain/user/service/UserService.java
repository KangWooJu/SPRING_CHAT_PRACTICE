package org.woojukang.springdefaultsetting.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.woojukang.springdefaultsetting.domain.user.dto.request.UserCreateRequest;
import org.woojukang.springdefaultsetting.domain.user.dto.response.UserCreateResponse;
import org.woojukang.springdefaultsetting.domain.user.entity.User;
import org.woojukang.springdefaultsetting.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User create(UserCreateRequest userCreateRequest){

        return User
                .builder()
                .username(userCreateRequest
                        .username())
                .password(bCryptPasswordEncoder
                        .encode(userCreateRequest
                                .password()))
                .role("USER")
                .deleted(false)
                .build();

    }

    public void save(User user){
        userRepository.save(user);
    }

    public void delete(User user){
        user.delete();
    }

}
