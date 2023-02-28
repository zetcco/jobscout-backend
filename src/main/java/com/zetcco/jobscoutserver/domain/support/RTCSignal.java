package com.zetcco.jobscoutserver.domain.support;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RTCSignal {
    
    Long senderId;
    String type;
    String data;
    
}
