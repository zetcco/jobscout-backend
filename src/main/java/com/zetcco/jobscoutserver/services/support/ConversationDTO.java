package com.zetcco.jobscoutserver.services.support;

import java.util.List;

import com.zetcco.jobscoutserver.domain.messaging.Message;

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
    private List<ProfileDTO> participants;
    private List<Message> messages;

}
