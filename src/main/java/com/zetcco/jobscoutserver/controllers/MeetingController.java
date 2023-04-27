package com.zetcco.jobscoutserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.domain.support.MeetingDTO;
import com.zetcco.jobscoutserver.domain.support.RTCSignal;
import com.zetcco.jobscoutserver.services.MeetingService;
import com.zetcco.jobscoutserver.services.support.exceptions.NotFoundException;

@Controller
@RequestMapping("meeting")
public class MeetingController {
    
    @Autowired
    private MeetingService meetingService;

    @PostMapping("/host")
    public ResponseEntity<MeetingDTO> hostMeeting(@RequestBody MeetingDTO meetingDTO) {
        try {
            return new ResponseEntity<MeetingDTO>(meetingService.hostMeeting(meetingDTO), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{link}")
    public ResponseEntity<MeetingDTO> getMeeting(@PathVariable String link) {
        try {
            return new ResponseEntity<MeetingDTO>(meetingService.getMeetingByLink(link), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{link}")
    public ResponseEntity<Void> endMeeting(@PathVariable String link) {
        try {
            meetingService.endMeetingByLink(link);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @MessageMapping("/meeting/{meetingId}")
    public void sendSignal(@DestinationVariable String meetingId, Message<RTCSignal> signal) {
        meetingService.sendToMeeting(meetingId, signal.getPayload());
    }

    @MessageMapping("/meeting/{meetingId}/{recieverId}")
    public void sendSignal(@DestinationVariable String meetingId, @DestinationVariable Long recieverId, Message<RTCSignal> signal) {
        meetingService.sendToMeeting(meetingId, recieverId, signal.getPayload());
    }
}
