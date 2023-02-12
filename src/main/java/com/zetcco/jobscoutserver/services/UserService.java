package com.zetcco.jobscoutserver.services;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.controllers.support.ProfileDTO;
import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    // @TODO : Add exception handling here
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public String setProfilePicture(String file) {
        User user = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        user.setDisplayPicture(file);
        userRepository.save(user);
        return file;
    }

    public ProfileDTO getUser(Long profileId) {
        return modelMapper.map(userRepository.findById(profileId).orElseThrow(null), ProfileDTO.class);
    }
}
