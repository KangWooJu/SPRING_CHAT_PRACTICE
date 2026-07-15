package org.woojukang.springdefaultsetting.global.security.dto.response;

public record LoginResponse(String username,
                            String message,
                            String loginAt) {
}
