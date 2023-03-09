package com.zetcco.jobscoutserver.services.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TypingDTO {
    private Long conversationId;
    private String name;
}
