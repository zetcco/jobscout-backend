package com.zetcco.jobscoutserver.domain.support;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RTCSignal {
    
    Long senderId;
    String type;
    String data;
    
}
