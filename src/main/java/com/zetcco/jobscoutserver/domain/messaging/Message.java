package com.zetcco.jobscoutserver.domain.messaging;

import java.util.Date;
import java.util.List;

import com.zetcco.jobscoutserver.domain.support.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Conversation conversation;

    @OneToOne
    private User sender;

    private Date timestamp;

    // TODO: Find a way to limit seenUsers if no of participants increase
    @OneToMany
    private List<User> seenUsers;

    private String content;

}
