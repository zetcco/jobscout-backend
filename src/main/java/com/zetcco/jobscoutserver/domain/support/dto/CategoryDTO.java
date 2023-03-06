package com.zetcco.jobscoutserver.domain.support.dto;

import java.util.List;

import com.zetcco.jobscoutserver.services.support.ProfileDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {
    
    private Long id;
    private String name;
    private String description;
    private List<ProfileDTO> participants;

}
