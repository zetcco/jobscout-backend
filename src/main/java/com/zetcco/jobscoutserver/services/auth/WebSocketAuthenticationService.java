package com.zetcco.jobscoutserver.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.services.UserService;

@Service
public class WebSocketAuthenticationService implements ChannelInterceptor {
    
    private static final String TOKEN_HEADER = "token";

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    // TODO: Find a better way to authorize the websocket
    @Override
    @Nullable
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        try {
            final StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            if ( headerAccessor != null && headerAccessor.getCommand() == StompCommand.CONNECT) {
                final String token = headerAccessor.getFirstNativeHeader(TOKEN_HEADER);
                final User user = userService.loadUserByEmail(jwtService.getUserEmail(token));
                if ( token != null && jwtService.isTokenValid(token, user)) 
                    return message;
                else 
                    throw new AccessDeniedException("Bad Credentials");
            } else 
                return message;
        } catch (Exception e) {
            throw new AccessDeniedException(e.getMessage());
        } 
    }



}
