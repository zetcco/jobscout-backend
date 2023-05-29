package com.zetcco.jobscoutserver.repositories.support;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.support.VerificationKey;

public interface VerificationKeyRepository extends JpaRepository<VerificationKey, Long> {
    
    VerificationKey findByVerificationKey(UUID key);
    
}
