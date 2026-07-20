package org.woojukang.springChatPractice.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.woojukang.springChatPractice.global.jpa.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    private boolean deleted;

    @Column(nullable = false)
    private String nickname;

    public void delete(){
        this.deleted = true;
        markDeleted();
    }

}
