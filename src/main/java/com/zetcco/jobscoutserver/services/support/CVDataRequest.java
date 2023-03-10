package com.zetcco.jobscoutserver.services.support;

import java.io.Serializable;
import java.util.List;

import com.zetcco.jobscoutserver.domain.Skill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CVDataRequest implements Serializable {
    
    Long id;
    Long templateId;
    String firstName;
    String lastName;
    String photo;
    String address;
    String contact;
    String email;
    String intro;

    // TODO: Make Educational Qualifications here
    String educationalQualifications;

    // TODO: Make Educational Qualifications here
    String pastExperience;

    List<Skill> skills;

}
