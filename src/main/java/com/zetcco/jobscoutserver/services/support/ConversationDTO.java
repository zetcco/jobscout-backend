package com.zetcco.jobscoutserver.services.support;

import java.util.ArrayList;
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
    private String name;
    private String picture;
    private List<ProfileDTO> participants;
    
    @Builder.Default
    private int page = 0;

    @Builder.Default
    private List<MessageDTO> messages = new ArrayList<MessageDTO>();

    private Boolean read;

}
