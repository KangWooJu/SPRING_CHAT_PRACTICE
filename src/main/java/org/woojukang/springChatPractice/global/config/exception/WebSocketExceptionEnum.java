package org.woojukang.springChatPractice.global.config.exception;

import lombok.RequiredArgsConstructor;
import org.woojukang.springChatPractice.global.enums.MessageCommInterface;

@RequiredArgsConstructor
public enum WebSocketExceptionEnum implements MessageCommInterface {
    CHATROOM_NOT_FOUND("WEBSOCKET.EXCEPTION.CHATROOM_NOT_FOUND","해당 채팅방을 찾을 수 없습니다."),
    SUBSCRIBER_NOT_MATCHED("WEBSOCKET.EXCEPTION.SUBSCRIBER_NOT_MATCHED","해당 사용자는 구독자가 아닙니다.");


    private final String errorCode;
    private final String message;

    @Override
    public String getCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
