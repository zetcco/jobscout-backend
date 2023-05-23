package com.zetcco.jobscoutserver.domain.support;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingDTO {
    private Long id;
    private String link;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate timestamp;  

    private ProfileDTO hoster;
}