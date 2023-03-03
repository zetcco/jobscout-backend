package com.zetcco.jobscoutserver.services.support;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationDTO {
    
    private Long id;
    private List<ProfileDTO> participants;

}
