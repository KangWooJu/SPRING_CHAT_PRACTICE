package org.woojukang.springdefaultsetting.slice.query.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.woojukang.springdefaultsetting.domain.user.entity.User;
import org.woojukang.springdefaultsetting.domain.user.repository.UserRepository;
import org.woojukang.springdefaultsetting.global.config.persistence.QueryDslConfig;
import org.woojukang.springdefaultsetting.query.user.repository.UserQueryRepository;
import org.woojukang.springdefaultsetting.query.user.service.UserQueryService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({QueryDslConfig.class, UserQueryRepository.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UserQueryRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Test
    @DisplayName("username으로 사용자 조회 성공")
    void findByUsername_success(){

        // given
        User user = User.builder()
                .username("testUser")
                .password("1234")
                .deleted(false)
                .role("ROLE_USER")
                .build();

        userRepository.save(user);

        // when
        Optional<User> result = userQueryRepository
                .findByUsername("testUser");

        // then
        assertThat(result
                .isPresent())
                .isTrue();

        assertThat(result
                .get()
                .getUsername())
                .isEqualTo(user
                        .getUsername());

    }

    @Test
    @DisplayName("존재하지 않는 username 조회 시 , Optional.empty 반환")
    void findByUsername_failure(){

        // when
        Optional<User> result = userQueryRepository
                .findByUsername("unknown");

        // then
        assertThat(result)
                .isEmpty();
    }



}