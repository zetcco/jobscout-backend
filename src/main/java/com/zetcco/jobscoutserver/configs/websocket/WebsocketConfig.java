package com.zetcco.jobscoutserver.configs.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app"); // Every websocket endpoint will use the suffix, /app.

        /* STOMP subscribe endpoints */
        registry.enableSimpleBroker(
            "/user/",       // Subscription Endpoint: /user/<userid>/<service>
            "/all/notify"         // Subscription Endpoint: /all/<channel>/<service>
        );
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /* Websocket will be connected to this endpoint, so, ex: http://localhost:8080/ws. Then it will switch fron HTTP to WS */
        registry.addEndpoint("/ws");
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS(); // For devices which doesn't support websockets.
    }
    
}
