package com.zetcco.jobscoutserver.services.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Environment environment;

    public ProfileDTO mapToDto(User user) {
        ProfileDTO profile = modelMapper.map(user, ProfileDTO.class);
        profile.setDisplayPicture(getProfilePictureLink(user));
        return profile;
    }

    public List<ProfileDTO> mapToDtos(List<? extends User> users) {
        return users.stream().map( user -> this.mapToDto(user)).collect(Collectors.toList());
    }

    public String getProfilePictureLink(User user) {
        final String PROFILE_RESOURCE_URL = environment.getProperty("server.url") + "/media/file/";
        if (PROFILE_RESOURCE_URL != null && user.getDisplayPicture() != null)
            return PROFILE_RESOURCE_URL.concat(user.getDisplayPicture());
        return null;
    }
    
}
