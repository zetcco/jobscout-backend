package com.zetcco.jobscoutserver.services.support.JobSeeker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobSeekerAbout {
    String email;
    String phone;
    String intro;
}
