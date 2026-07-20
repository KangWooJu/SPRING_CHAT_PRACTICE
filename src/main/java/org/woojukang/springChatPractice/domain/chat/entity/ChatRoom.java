package org.woojukang.springChatPractice.domain.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import org.woojukang.springChatPractice.global.jpa.BaseEntity;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ChatRoom extends BaseEntity {

    @Id
    @Column(name = "chat_room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String chatRoomName;

    @Column
    private boolean deleted;

    public void delete(){
        this.deleted = true;
        markDeleted();
    }

}
