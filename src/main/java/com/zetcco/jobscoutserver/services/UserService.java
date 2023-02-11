package com.zetcco.jobscoutserver.services;

import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // @TODO : Add exception handling here
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }
}
