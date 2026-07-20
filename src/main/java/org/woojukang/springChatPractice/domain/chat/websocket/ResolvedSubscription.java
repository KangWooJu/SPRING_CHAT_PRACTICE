package org.woojukang.springChatPractice.domain.chat.websocket;

public record ResolvedSubscription(String sessionId,
                                   String subscriptionId,
                                   Long roomId,
                                   String username) {
}
