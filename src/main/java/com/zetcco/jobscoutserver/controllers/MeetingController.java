package com.zetcco.jobscoutserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.domain.support.MeetingDTO;
import com.zetcco.jobscoutserver.domain.support.RTCSignal;
import com.zetcco.jobscoutserver.services.MeetingService;

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
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @MessageMapping("/room/{roomId}")
    public void sendToRoom(@DestinationVariable Long roomId, @Payload RTCSignal rtcSignal) {
        meetingService.sentToRoom(roomId, rtcSignal);
    }

    @MessageMapping("/room/{roomId}/{userId}")
    public void sendToRoomUser(@DestinationVariable Long roomId, @DestinationVariable Long userId, @Payload RTCSignal rtcSignal) {
        meetingService.sentToRoomUser(roomId, userId, rtcSignal);
    }
}
