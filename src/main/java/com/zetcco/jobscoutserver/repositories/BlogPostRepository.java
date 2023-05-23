package com.zetcco.jobscoutserver.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.zetcco.jobscoutserver.domain.BlogPost;
import com.zetcco.jobscoutserver.domain.support.User;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    Optional<User> findByUser(String user);
}
