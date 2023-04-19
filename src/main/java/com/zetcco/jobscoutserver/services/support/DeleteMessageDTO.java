package com.zetcco.jobscoutserver.services.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteMessageDTO {
    private Long conversationId;
    private Long messageId;
}
