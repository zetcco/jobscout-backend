package com.zetcco.jobscoutserver.services.support;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDTO {
    
    private Long id;
    private ProfileDTO sender;
    private Date timestamp;
    private String content;

}
