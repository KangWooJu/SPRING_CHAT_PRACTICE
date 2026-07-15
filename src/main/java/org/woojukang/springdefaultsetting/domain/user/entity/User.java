package org.woojukang.springdefaultsetting.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.woojukang.springdefaultsetting.global.jpa.BaseEntity;

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

    private String username;

    private String password;

    private String role;

    private boolean deleted;

    public void delete(){
        this.deleted = true;
        markDeleted();
    }

}
