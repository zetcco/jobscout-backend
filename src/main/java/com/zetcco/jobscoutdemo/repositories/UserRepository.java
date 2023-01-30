package com.zetcco.jobscoutdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutdemo.domain.support.User;
public interface UserRepository extends JpaRepository<User, Long> {
    
}
