package com.zetcco.jobscoutserver.repositories;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.Category;
import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.Organization;
import com.zetcco.jobscoutserver.domain.Skill;
import com.zetcco.jobscoutserver.domain.support.Address;
import com.zetcco.jobscoutserver.domain.support.CategorySkillSet;
import com.zetcco.jobscoutserver.domain.support.NameTitle;
import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Degree;
import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Institute;
import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Qualification;
import com.zetcco.jobscoutserver.domain.support.PastExperience.JobTitle;
import com.zetcco.jobscoutserver.domain.support.PastExperience.PastExperience;
import com.zetcco.jobscoutserver.repositories.support.CategorySkillSetRepository;
import com.zetcco.jobscoutserver.repositories.support.PastExperience.JobTitleRepository;
import com.zetcco.jobscoutserver.repositories.support.PastExperience.PastExperienceRepository;
import com.zetcco.jobscoutserver.repositories.support.Qualification.DegreeRepository;
import com.zetcco.jobscoutserver.repositories.support.Qualification.InstituteRepository;
import com.zetcco.jobscoutserver.repositories.support.Qualification.QualificationRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class JobSeekerRepositoryTest {

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private DegreeRepository degreeRepository;

    @Autowired
    private InstituteRepository instituteRepository;

    @Autowired
    private QualificationRepository qualificationRepository;

    @Autowired
    private JobTitleRepository jobTitleRepository;

    @Autowired
    private PastExperienceRepository pastExperienceRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private CategorySkillSetRepository categorySkillSetRepository;

    @Autowired
    private SkillsRepository skillsRepository;

    @Test
    void getJobSeeker() {
        JobSeeker jobSeeker = jobSeekerRepository.findById(89L).orElseThrow();
        System.out.println(jobSeeker);
    }

    @Test
    void setPastExperience() {
        JobSeeker jobSeeker = jobSeekerRepository.findById(117L).orElseThrow();
        JobTitle jobTitle = jobTitleRepository.findById(2L).orElseThrow();
        Organization organization = organizationRepository.findById(93L).orElseThrow();
        PastExperience pastExperience = PastExperience.builder()
                                                        .startYear("2021")
                                                        .endYear("2024")
                                                        .jobTitle(jobTitle)
                                                        .organization(organization)
                                                        .build();
        pastExperience = pastExperienceRepository.save(pastExperience);

        List<PastExperience> pastExperiences = jobSeeker.getPastExperiences();
        pastExperiences.add(pastExperience);
        jobSeeker.setPastExperiences(pastExperiences);
        jobSeekerRepository.save(jobSeeker);
    }

    @Test
    void setQualifications() {
        JobSeeker jobSeeker = jobSeekerRepository.findById(117L).orElseThrow();
        Degree degree = degreeRepository.findById(14L).orElseThrow();
        Institute institute = instituteRepository.findById(13L).orElseThrow();
        Qualification qualification = Qualification.builder()
                                                    .degree(degree)
                                                    .institute(institute)
                                                    .startYear("2023")
                                                    .endYear("2025")
                                                    .build();
        qualification = qualificationRepository.save(qualification);
        List<Qualification> qualifications = jobSeeker.getQualifications();
        qualifications.add(qualification);
        jobSeeker.setQualifications(qualifications);
        jobSeekerRepository.save(jobSeeker);

    }

    @Test
    public void saveJobSeeker() {
        Address address = Address.builder()
                                .number("41")
                                .street("Street")
                                .town("Town")
                                .city("City")
                                .province("Province")
                                .country("Country")
                                .build();
        JobSeeker jobSeeker = new JobSeeker("seek@gmail.com", "seekpasswd", address);
        jobSeekerRepository.save(jobSeeker);
    }

    @Test
    public void saveJobSeekerProfile() {
        JobSeeker jobSeeker = jobSeekerRepository.findById(2L).orElseThrow();
        jobSeeker.setTitle(NameTitle.MR);
        jobSeeker.setFirstName("Indrajith");
        jobSeeker.setLastName("Madhumal");
        jobSeekerRepository.save(jobSeeker);
    }

    @Test
    @Transactional
    public void updateCategorySkillSet() {
        /* 
         * Input: [
         *          {
         *              categoryId: 2,
         *              skillIds: [ 
         *                          { id: 1, name: "whatev" },
         *                          { id: 2, name: "whatev" },
         *                       ]
         *          },
         *          {
         *              categoryId: 4,
         *              skillIds: [ 
         *                          { id: 1, name: "whatev" },
         *                          { id: 2, name: "whatev" },
         *                       ]
         *          },
         *          {
         *              categoryId: 4,
         *              skillIds: [ 
         *                          { id: null, name: "New Skill" },
         *                       ]
         *          },
         *        ]
         */

         List<CategorySkillSet> categorySkillSets = List.of(
            CategorySkillSet.builder().category(
                    Category.builder().id(8L).build()
                ).skills(
                    List.of(
                        Skill.builder().id(6L).build(),
                        Skill.builder().id(7L).build()
                    )
                ).build(),
            CategorySkillSet.builder().category(
                    Category.builder().id(8L).build()
                ).skills(
                    List.of(
                        Skill.builder().id(9L).build(),
                        Skill.builder().id(10L).build(),
                        Skill.builder().name("NGINIX").description("Server load balancing").build()
                    )
                ).build()
        );
        JobSeeker jobSeeker = jobSeekerRepository.findById(115L).orElseThrow();
        for (CategorySkillSet categorySkillSet : categorySkillSets) {
            for (Skill skill : categorySkillSet.getSkills()) {
                if (skill.getId() == null) {
                    skill = skillsRepository.save(skill);
                }
            }
        }
        categorySkillSets = categorySkillSetRepository.saveAll(categorySkillSets);
        jobSeeker.setCategorySkillSets(categorySkillSets);
        jobSeekerRepository.save(jobSeeker);
    }
}
