package com.zetcco.jobscoutserver.services;

import java.time.LocalDate;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.Meeting;
import com.zetcco.jobscoutserver.domain.support.MeetingDTO;
import com.zetcco.jobscoutserver.domain.support.RTCSignal;
import com.zetcco.jobscoutserver.repositories.MeetingRepository;
import com.zetcco.jobscoutserver.services.mappers.MeetingMapper;
import com.zetcco.jobscoutserver.services.support.exceptions.BadRequestException;
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
    private RTCService rtcService;

    @PreAuthorize("hasRole('JOB_CREATOR')")
    public MeetingDTO hostMeeting(MeetingDTO meetingDTO) throws BadRequestException {
        if (meetingDTO.getTimestamp() == null || meetingDTO.getTimestamp().isBefore(LocalDate.now())) throw new BadRequestException("Invalid date");
        String link = RandomStringUtils.randomAlphanumeric(3) + "-" + RandomStringUtils.randomAlphanumeric(3) + "-" + RandomStringUtils.randomAlphanumeric(3);
        Meeting newMeeting = Meeting.builder()
                                    .timestamp(meetingDTO.getTimestamp())
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

    public MeetingDTO getMeetingByLink(String link) throws NotFoundException {
        Meeting meeting = meetingRepository.findByLink(link).orElseThrow(() -> new NotFoundException("Meeting not found"));
        if (meeting.getTimestamp().isBefore(LocalDate.now())) {
            endMeetingByLink(link);
            throw new NotFoundException("Meeting expired");
        }
        return mapper.mapMeeting(meeting);
    }

    MeetingDTO mapToDTO(Meeting meeting) {
        MeetingDTO meetingDTO = MeetingDTO.builder()
                                            .hoster(userService.getUser(meeting.getHoster()))
                                            .id(meeting.getId())
                                            .build();
        return meetingDTO;
    }

    public void sendToMeeting(String meetingId, RTCSignal rtcSignal) {
        rtcService.sendToDestination("/meeting/" + meetingId, rtcSignal);
    }

    public void sendToMeeting(String meetingId, Long userId, RTCSignal rtcSignal) {
        rtcService.sendToDestination("/meeting/" + meetingId + "/" + userId.toString(), rtcSignal);
    }

}