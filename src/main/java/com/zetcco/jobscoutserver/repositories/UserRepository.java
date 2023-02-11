package com.zetcco.jobscoutserver.repositories;

import java.util.Optional;

import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.repositories.support.UserBaseRepository;
public interface UserRepository extends UserBaseRepository<User> {
    Optional<User> findByEmail(String email);
}
