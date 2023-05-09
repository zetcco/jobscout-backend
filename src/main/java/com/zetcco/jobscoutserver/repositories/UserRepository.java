package com.zetcco.jobscoutserver.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.repositories.support.UserBaseRepository;
public interface UserRepository extends UserBaseRepository<User>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);
}
