package com.zetcco.jobscoutdemo.services.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutdemo.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class _UserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.loadUserByEmail(username);
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        return userService.loadUserByEmail(email);
    }
    
}
