package org.woojukang.springChatPractice.domain.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import org.woojukang.springChatPractice.domain.user.entity.User;
import org.woojukang.springChatPractice.global.jpa.BaseEntity;

import java.time.Instant;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id",nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id",nullable = false)
    private User sender;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column
    private Instant sendAt;

    private boolean deleted;

    public void deleteOneMessage(){
        this.deleted = true;
        markDeleted();
    }

}
