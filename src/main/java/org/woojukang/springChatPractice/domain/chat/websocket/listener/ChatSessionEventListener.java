package org.woojukang.springChatPractice.domain.chat.websocket.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;
import org.woojukang.springChatPractice.domain.chat.dto.MessageType;
import org.woojukang.springChatPractice.domain.chat.facade.ChatFacade;
import org.woojukang.springChatPractice.domain.chat.websocket.ResolvedSubscription;
import org.woojukang.springChatPractice.domain.chat.websocket.registry.ChatSubscriptionRegistry;
import org.woojukang.springChatPractice.domain.chat.websocket.resolver.ChatDestinationResolver;

import java.security.Principal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChatSessionEventListener {

    private final ChatSubscriptionRegistry subscriptionRegistry;
    private final ChatDestinationResolver destinationResolver;
    private final ChatFacade chatFacade;

    @EventListener
    public void handleSubscribe(SessionSubscribeEvent event) {

        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(event.getMessage());

        resolveSubscription(accessor)
                .filter(subscription ->
                        subscriptionRegistry.subscribe(
                                subscription.sessionId(),
                                subscription.subscriptionId(),
                                subscription.roomId(),
                                subscription.username()
                        )
                )
                .ifPresent(subscription ->
                        publishPresenceMessage(
                                subscription.roomId(),
                                subscription.username(),
                                MessageType.ENTER
                        )
                );
    }

    @EventListener
    public void handleUnsubscribe(SessionUnsubscribeEvent event) {

        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = accessor.getSessionId();
        String subscriptionId = accessor.getSubscriptionId();

        if (sessionId == null || subscriptionId == null) {
            return;
        }

        Optional.ofNullable(
                        subscriptionRegistry.unsubscribe(
                                sessionId,
                                subscriptionId
                        )
                )
                .ifPresent(subscription ->
                        publishPresenceMessage(
                                subscription.roomId(),
                                subscription.username(),
                                MessageType.LEAVE
                        )
                );
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {

        subscriptionRegistry.disconnect(event.getSessionId())
                .forEach(subscription ->
                        publishPresenceMessage(
                                subscription.roomId(),
                                subscription.username(),
                                MessageType.LEAVE
                        )
                );
    }

    // STOMP 헤더로부터 구독 정보추출
    private Optional<ResolvedSubscription> resolveSubscription(
            StompHeaderAccessor accessor
    ) {

        String sessionId = accessor.getSessionId();
        String subscriptionId = accessor.getSubscriptionId();
        Principal principal = accessor.getUser();

        Long roomId = destinationResolver.extractRoomId(
                accessor.getDestination()
        );

        if (sessionId == null
                || subscriptionId == null
                || principal == null
                || roomId == null) {

            return Optional.empty();
        }

        return Optional.of(
                new ResolvedSubscription(
                        sessionId,
                        subscriptionId,
                        roomId,
                        principal.getName()
                )
        );
    }

    // 메시지를 생성하는 메소드
    private void publishPresenceMessage(
            Long roomId,
            String username,
            MessageType messageType
    ) {

        chatFacade.publishSystemMessage(
                roomId,
                username,
                messageType
        );
    }
}