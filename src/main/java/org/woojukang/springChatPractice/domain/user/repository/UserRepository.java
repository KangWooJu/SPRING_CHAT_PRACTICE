package org.woojukang.springChatPractice.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.woojukang.springChatPractice.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
