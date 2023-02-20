package com.zetcco.jobscoutserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.support.User;
public interface JobPostRepository extends JpaRepository<User, Long>{
    
}
