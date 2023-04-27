package com.zetcco.jobscoutserver.services;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.Meeting;
import com.zetcco.jobscoutserver.domain.support.MeetingDTO;
import com.zetcco.jobscoutserver.domain.support.RTCSignal;
import com.zetcco.jobscoutserver.repositories.MeetingRepository;
import com.zetcco.jobscoutserver.services.mappers.MeetingMapper;
import com.zetcco.jobscoutserver.services.support.exceptions.NotFoundException;

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

    @PreAuthorize("hasRole('JOB_CREATOR')")
    public MeetingDTO hostMeeting(MeetingDTO meetingDTO) {
        String link = RandomStringUtils.randomAlphanumeric(3) + "-" + RandomStringUtils.randomAlphanumeric(3) + "-" + RandomStringUtils.randomAlphanumeric(3);
        Meeting newMeeting = Meeting.builder()
                                    .timestamp(meetingDTO.getTimestamp() != null ? meetingDTO.getTimestamp() : new Date())
                                    .hoster(userService.getAuthUser())
                                    .link(link)
                                    .build();
        Meeting meeting = meetingRepository.save(newMeeting);
        return mapper.mapMeeting(meeting);
    }

    @PreAuthorize("hasRole('JOB_CREATOR')")
    public void endMeetingByLink(String link) throws AccessDeniedException {
        Long userId = userService.getAuthUser().getId();
        Meeting meeting = meetingRepository.findByLink(link).orElseThrow(() -> new NotFoundException("Meeting not found"));
        if (meeting.getHoster().getId() == userId)
            meetingRepository.delete(meeting);
        else
            throw new AccessDeniedException("You do not have permission to do that");
    }

    public MeetingDTO getMeetingByLink(String link) {
        Meeting meeting = meetingRepository.findByLink(link).orElseThrow(() -> new NotFoundException("Meeting not found"));
        return mapper.mapMeeting(meeting);
    }

    MeetingDTO mapToDTO(Meeting meeting) {
        MeetingDTO meetingDTO = MeetingDTO.builder()
                                            .hoster(userService.getUser(meeting.getHoster()))
                                            .id(meeting.getId())
                                            .build();
        return meetingDTO;
    }

    public void sentToRoom(Long roomId, RTCSignal rtcSignal) {
        System.out.println("----------came here 1--------------");
        simpMessagingTemplate.convertAndSend("/room/" + roomId, rtcSignal);
    }

    public void sentToRoomUser(Long roomId, Long userId, RTCSignal rtcSignal) {
        System.out.println("----------came here 2--------------");
        simpMessagingTemplate.convertAndSend("/room/" + roomId + "/" + userId, rtcSignal);
    }

}