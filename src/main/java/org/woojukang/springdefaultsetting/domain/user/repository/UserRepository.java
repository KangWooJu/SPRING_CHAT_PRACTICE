package org.woojukang.springdefaultsetting.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.woojukang.springdefaultsetting.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
