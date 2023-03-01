package com.zetcco.jobscoutserver.services.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zetcco.jobscoutserver.domain.Meeting;
import com.zetcco.jobscoutserver.domain.support.MeetingDTO;

@Component
public class MeetingMapper {

    @Autowired
    private UserMapper userMapper;

    public MeetingDTO mapNotification(Meeting meeting) {
        MeetingDTO meetingDTO = MeetingDTO.builder()
                                            .id(meeting.getId())
                                            .link(meeting.getLink())
                                            .hoster(userMapper.mapToDto(meeting.getHoster()))
                                            .timestamp(meeting.getTimestamp())
                                            .build();
        return meetingDTO;
    }
    
}
