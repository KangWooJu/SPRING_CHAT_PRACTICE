package org.woojukang.springChatPractice.domain.user.dto.response;

import java.time.Instant;
import java.time.LocalDateTime;

public record UserCreateResponse(String username,
                                 Instant createdAt,
                                 Instant updatedAt) {
}
