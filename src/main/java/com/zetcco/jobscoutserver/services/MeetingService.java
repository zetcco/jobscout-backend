package com.zetcco.jobscoutserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.Meeting;
import com.zetcco.jobscoutserver.domain.support.MeetingDTO;
import com.zetcco.jobscoutserver.domain.support.RTCSignal;
import com.zetcco.jobscoutserver.repositories.MeetingRepository;

@Service
public class MeetingService {
    
    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public MeetingDTO hostMeeting(MeetingDTO meetingDTO) {
        Meeting newMeeting = Meeting.builder()
                                    .hoster(userService.getAuthUser())
                                    .build();
        Meeting meeting = meetingRepository.save(newMeeting);
        return mapToDTO(meeting);
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