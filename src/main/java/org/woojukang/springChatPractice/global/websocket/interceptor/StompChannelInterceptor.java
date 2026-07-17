package org.woojukang.springChatPractice.global.websocket.interceptor;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.woojukang.springChatPractice.global.config.exception.BaseExceptionEnum;
import org.woojukang.springChatPractice.global.config.exception.WebSocketExceptionEnum;
import org.woojukang.springChatPractice.global.config.exception.domain.BaseException;
import org.woojukang.springChatPractice.global.security.auth.AuthPrincipal;
import org.woojukang.springChatPractice.global.security.provider.JwtAuthenticationProvider;
import org.woojukang.springChatPractice.query.chat.service.ChatRoomMemberQueryService;

@Component
@RequiredArgsConstructor
public class StompChannelInterceptor implements ChannelInterceptor {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final ChatRoomMemberQueryService chatRoomMemberQueryService;

    @Override
    public @Nullable Message<?> preSend
            (Message<?> message,
             MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if(accessor.getCommand() == null){
            return message;
        }

        switch (accessor.getCommand()){

            case CONNECT ->
                validateConnection(accessor);
            case SUBSCRIBE,SEND ->
                validateSubscription(accessor);
            default -> {

            }
        }
        return message;
    }

    private void validateConnection(StompHeaderAccessor accessor){

        // StompHeadAccessor를 통해 Header에서 Authorization 정보 추출
        String authorizationHeader = accessor
                .getFirstNativeHeader("Authorization");

        // jwt토큰 파싱 및 검수 후 , Authentication 객체 반환
        Authentication authentication = jwtAuthenticationProvider
                .authenticate(authorizationHeader);

        accessor.setUser(authentication);
    }

    private void validateSubscription(StompHeaderAccessor accessor){

        // 구독 destination 에서 roomId,userId 추출
        Long roomId = extractRoomId(accessor);
        Long userId = extractUserId(accessor);

        // 해당 사용자가 채팅방 멤버인지 검증
        if(!chatRoomMemberQueryService.checkSubscriberWithRoomId(roomId, userId)){
            throw new BaseException(WebSocketExceptionEnum
                    .SUBSCRIBER_NOT_MATCHED);
        }
    }


    // accessor를 통해 roomId 파싱하기
    private Long extractRoomId(StompHeaderAccessor accessor){

        String destination = accessor.getDestination();

        if(destination == null){
            throw new BaseException(WebSocketExceptionEnum.CHATROOM_NOT_FOUND);
        }

        return Long
                .parseLong(destination
                        .substring(destination
                                .lastIndexOf("/")+1));
    }

    private Long extractUserId(StompHeaderAccessor accessor){

        Authentication authentication =
                (Authentication) accessor.getUser();

        if (authentication == null) {
            throw new BaseException(BaseExceptionEnum.USER_UNAUTHORIZED);
        }

        AuthPrincipal principal =
                (AuthPrincipal) authentication.getPrincipal();

        return principal.getUserId();
    }
}
