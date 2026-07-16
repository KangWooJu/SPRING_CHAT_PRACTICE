package org.woojukang.springChatPractice.global.config.exception;

import lombok.RequiredArgsConstructor;
import org.woojukang.springChatPractice.global.enums.MessageCommInterface;

@RequiredArgsConstructor
public enum WebSocketExceptionEnum implements MessageCommInterface {
    CHATROOM_NOT_FOUND("WEBSOCKET.EXCEPTION.CHATROOM_NOT_FOUND","해당 채팅방을 찾을 수 없습니다.");


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
