package org.woojukang.springdefaultsetting.domain.user.dto.response;

import java.time.LocalDateTime;

public record UserCreateResponse(String username,
                                 LocalDateTime createdAt,
                                 LocalDateTime updatedAt) {
}
