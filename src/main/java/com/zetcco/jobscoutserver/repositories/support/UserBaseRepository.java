package com.zetcco.jobscoutserver.repositories.support;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.zetcco.jobscoutserver.domain.support.User;

@NoRepositoryBean
public interface UserBaseRepository<T extends User> extends JpaRepository<T, Long> {
}
