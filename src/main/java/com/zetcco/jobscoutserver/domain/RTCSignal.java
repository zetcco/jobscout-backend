package com.zetcco.jobscoutserver.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RTCSignal {
    
    Long senderId;
    Long recieverId;
    String type;
    String data;
    
}
