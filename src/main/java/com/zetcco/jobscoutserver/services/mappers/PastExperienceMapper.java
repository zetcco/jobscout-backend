package com.zetcco.jobscoutserver.services.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.domain.support.PastExperience.PastExperience;
import com.zetcco.jobscoutserver.domain.support.dto.PastExperienceDTO;

@Component
public class PastExperienceMapper {

    @Autowired
    private UserMapper userMapper;

    public PastExperienceDTO mapToDto(PastExperience pastExperience) {
        return new PastExperienceDTO(
            pastExperience.getId(),
            pastExperience.getStartYear(),
            pastExperience.getEndYear(),
            pastExperience.getJobTitle(),
            userMapper.mapToDto((User)pastExperience.getOrganization())
        );
    }

    public List<PastExperienceDTO> mapToDtos(List<PastExperience> pastExperiences) {
        return pastExperiences.stream().map( pastExperience -> this.mapToDto(pastExperience)).collect(Collectors.toList());
    }
    
}
