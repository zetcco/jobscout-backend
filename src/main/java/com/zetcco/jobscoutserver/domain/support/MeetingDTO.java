package com.zetcco.jobscoutserver.domain.support;

import com.zetcco.jobscoutserver.services.support.ProfileDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeetingDTO {
    private Long id;
    private ProfileDTO hoster;
}