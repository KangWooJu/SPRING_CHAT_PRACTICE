package org.woojukang.springChatPractice.domain.user.dto.response;

import java.time.LocalDateTime;

public record UserDeleteResponse(String username,
                                 LocalDateTime deletedAt) {
}
