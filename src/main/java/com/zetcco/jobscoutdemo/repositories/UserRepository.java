package com.zetcco.jobscoutdemo.repositories;

import java.util.Optional;

import com.zetcco.jobscoutdemo.domain.support.User;
import com.zetcco.jobscoutdemo.repositories.support.UserBaseRepository;
public interface UserRepository extends UserBaseRepository<User> {
    Optional<User> findByEmail(String email);
}
