package com.zetcco.jobscoutserver.services;

import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
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
    private final Environment environment;

    // @TODO : Add exception handling here
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public ProfileDTO setProfilePicture(String file) {
        User user = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        user.setDisplayPicture(file);
        userRepository.save(user);
        return this.getUser();
    }

    // @zetcco @TODO: Find more convinent way to attach the server url to media resources
    public ProfileDTO getUser(Long profileId) {
        User user = userRepository.findById(profileId).orElseThrow();
        return this.getUser(user);
    }

    public ProfileDTO getUser() {
        User user = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return this.getUser(user);
    }

    public ProfileDTO getUser(User user) {
        return this.mapUser(user);
    }

    private ProfileDTO mapUser(User user) {
        ProfileDTO profile = modelMapper.map(user, ProfileDTO.class);
        final String PROFILE_RESOURCE_URL = environment.getProperty("server.url") + "/media/file/";
        if (PROFILE_RESOURCE_URL != null && profile.getDisplayPicture() != null)
            profile.setDisplayPicture(PROFILE_RESOURCE_URL.concat(profile.getDisplayPicture()));
        return profile;
    }
}
