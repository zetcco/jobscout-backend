package com.zetcco.jobscoutserver.services;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.Meeting;
import com.zetcco.jobscoutserver.domain.support.MeetingDTO;
import com.zetcco.jobscoutserver.domain.support.RTCSignal;
import com.zetcco.jobscoutserver.repositories.MeetingRepository;
import com.zetcco.jobscoutserver.services.mappers.MeetingMapper;

@Service
public class MeetingService {

    @Autowired
    private MeetingMapper mapper;
    
    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public MeetingDTO hostMeeting(MeetingDTO meetingDTO) {
        String link = RandomStringUtils.randomAlphanumeric(3) + "-" + RandomStringUtils.randomAlphanumeric(3) + "-" + RandomStringUtils.randomAlphanumeric(3);
        Meeting newMeeting = Meeting.builder()
                                    .timestamp(meetingDTO.getTimestamp() != null ? meetingDTO.getTimestamp() : new Date())
                                    .hoster(userService.getAuthUser())
                                    .link(link)
                                    .build();
        Meeting meeting = meetingRepository.save(newMeeting);
        return mapper.mapNotification(meeting);
    }

    MeetingDTO mapToDTO(Meeting meeting) {
        MeetingDTO meetingDTO = MeetingDTO.builder()
                                            .hoster(userService.getUser(meeting.getHoster()))
                                            .id(meeting.getId())
                                            .build();
        return meetingDTO;
    }

    public void sentToRoom(Long roomId, RTCSignal rtcSignal) {
        simpMessagingTemplate.convertAndSend("/room/" + roomId, rtcSignal);
    }

    public void sentToRoomUser(Long roomId, Long userId, RTCSignal rtcSignal) {
        simpMessagingTemplate.convertAndSend("/room/" + roomId + "/" + userId, rtcSignal);
    }

}