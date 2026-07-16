package org.woojukang.springChatPractice.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.woojukang.springChatPractice.domain.chat.entity.ChatMessage;

@Repository
public interface ChatMessageRepository
        extends JpaRepository<ChatMessage,Long> {
}
