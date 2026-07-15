package org.woojukang.springdefaultsetting.unit.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.woojukang.springdefaultsetting.global.security.dto.request.LoginRequest;
import org.woojukang.springdefaultsetting.global.security.filter.JwtLoginFilter;
import org.woojukang.springdefaultsetting.global.security.service.RefreshService;
import org.woojukang.springdefaultsetting.global.security.util.JwtUtil;
import org.woojukang.springdefaultsetting.global.utils.web.CookieUtil;


import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtLoginFilterTest {

    @Spy
    private final ObjectMapper objectMapper =
            new ObjectMapper();

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RefreshService refreshService;

    @Mock
    private CookieUtil cookieUtil;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private TestJwtLoginFilter testJwtLoginFilter;

    @Test
    @DisplayName("로그인 요청 JSON을 파싱하고 AuthenticationManager에게 인증 위임")
    void attemptAuthentication_success()
            throws Exception{

        // given

        String username = "testUser";
        String password = "1234";

        LoginRequest loginRequest =
                new LoginRequest(username,password);

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        request
                .setContentType("application/json");

        request
                .setContent(
                objectMapper.writeValueAsString(loginRequest)
                        .getBytes(StandardCharsets.UTF_8));

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        Authentication authentication =
                mock(Authentication.class);

        when(authenticationManager
                .authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // when
        Authentication result = testJwtLoginFilter
                .attemptAuthentication(request,response);

        // then
        assertThat(result)
                .isSameAs(authentication);

        verify(refreshService, times(1))
                .validateAlreadyLogin(username);

        ArgumentCaptor<UsernamePasswordAuthenticationToken> captor =
                ArgumentCaptor.forClass(
                        UsernamePasswordAuthenticationToken.class
                );

        verify(authenticationManager,times(1))
                .authenticate(captor.capture());

        UsernamePasswordAuthenticationToken token =
                captor.getValue();

        assertThat(token.getPrincipal())
                .isEqualTo(username);

        assertThat(token.getCredentials())
                .isEqualTo(password);

    }

    @Test
    @DisplayName("로그인 성공 시 , access 토큰의 헤더와 refresh 토큰 쿠키를 추가")
    void successfulAuthentication_success()
            throws Exception{

        // given
        String username = "testUser";
        String role = "ROLE_USER";

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        Authentication authentication =
                mock(Authentication.class);

        List<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority(role));

        doReturn(authorities)
                .when(authentication)
                .getAuthorities();

        when(authentication
                .getName())
                .thenReturn(username);

        when(jwtUtil
                .createJwt(
                        eq("access"),
                        eq(username),
                        eq(role),
                        any(Long.class)))
                .thenReturn("accessToken");

        when(jwtUtil
                .createJwt(
                        eq("refresh"),
                        eq(username),
                        eq(role),
                        any(Long.class)))
                .thenReturn("refreshToken");

        Cookie refreshCookie =
                new Cookie("refreshToken",
                        "refreshToken");

        when(cookieUtil
                .createCookie(
                        "refreshToken",
                        "refreshToken"))
                .thenReturn(refreshCookie);

        // when
        testJwtLoginFilter
                .callSuccessfulAuthentication(
                        request,
                        response,
                        filterChain,
                        authentication);

        // then
        Cookie responseRefreshCookie = response
                .getCookie("refreshToken");


        assertThat(response
                .getHeader("Authorization"))
                .isEqualTo("Bearer accessToken");

        assertThat(responseRefreshCookie)
                .isNotNull();

        assertThat(responseRefreshCookie
                .getValue())
                .isEqualTo("refreshToken");

        assertThat(response.getStatus())
                .isEqualTo(200);

        assertThat(response.getContentAsString())
                .contains("로그인에 성공하였습니다.");

        verify(refreshService,times(1))
                .addRefresh(username, "refreshToken");

        verify(jwtUtil,times(1))
                .createJwt(
                        eq("access"),
                        eq(username),
                        eq(role),
                        any(Long.class)
                );

        verify(jwtUtil, times(1))
                .createJwt(
                        eq("refresh"),
                        eq(username),
                        eq(role),
                        any(Long.class)
                );

    }

    @Test
    @DisplayName("로그인 실패 시 , 401 응답 반환")
    void unsuccessfulAuthentication_success()
            throws Exception {

        // given
        MockHttpServletRequest request =
                new MockHttpServletRequest();

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        AuthenticationException exception =
                mock(AuthenticationException.class);

        // when
        testJwtLoginFilter
                .callUnsuccessfulAuthentication(
                        request,
                        response,
                        exception
                );

        // then
        assertThat(response.getStatus())
                .isEqualTo(401);

        assertThat(response.getContentAsString())
                .contains("AUTH_FAILED")
                .contains("아이디 또는 비밀번호가 일치하지 않습니다.");
    }


    // 테스트 접근 클래스
    private static class TestJwtLoginFilter
            extends JwtLoginFilter {
        public TestJwtLoginFilter
                (ObjectMapper objectMapper,
                 AuthenticationManager authenticationManager,
                 JwtUtil jwtUtil,
                 RefreshService refreshService,
                 CookieUtil cookieUtil) {

            super(objectMapper,
                    authenticationManager,
                    jwtUtil,
                    refreshService,
                    cookieUtil);
        }


        // successfulAuthentication 메소드 호출기
        public void callSuccessfulAuthentication(
                MockHttpServletRequest request,
                MockHttpServletResponse response,
                FilterChain chain,
                Authentication authResult
        ) throws Exception {

            successfulAuthentication(
                    request,
                    response,
                    chain,
                    authResult
            );
        }

        // unsuccessfulAuthentication 메소드 호출기
        public void callUnsuccessfulAuthentication(
                MockHttpServletRequest request,
                MockHttpServletResponse response,
                AuthenticationException failed
        ) throws Exception {

            unsuccessfulAuthentication(
                    request,
                    response,
                    failed
            );
        }
    }

}

