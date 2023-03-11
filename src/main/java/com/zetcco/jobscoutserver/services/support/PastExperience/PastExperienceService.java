package com.zetcco.jobscoutserver.services.support.PastExperience;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.support.PastExperience.JobTitle;
import com.zetcco.jobscoutserver.domain.support.PastExperience.PastExperience;
import com.zetcco.jobscoutserver.repositories.support.PastExperience.JobTitleRepository;
import com.zetcco.jobscoutserver.repositories.support.PastExperience.PastExperienceRepository;

@Service
public class PastExperienceService {

    @Autowired
    private PastExperienceRepository pastExperienceRepository;

    @Autowired
    private JobTitleRepository jobTitleRepository;

    public PastExperience savePastExperience(PastExperience pastExperience) {
        return pastExperienceRepository.save(pastExperience);
    }

    public List<PastExperience> savePastExperiences(List<PastExperience> pastExperiences) {
        return pastExperienceRepository.saveAll(pastExperiences);
    }

    public List<JobTitle> getAllJobTitles() {
        return jobTitleRepository.findAll();
    }
    
}
