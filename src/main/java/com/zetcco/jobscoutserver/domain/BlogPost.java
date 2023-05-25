package com.zetcco.jobscoutserver.domain;

import java.util.Date;
import java.util.List;

import com.zetcco.jobscoutserver.domain.support.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private Date timeStamp;
    
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToMany
    private List<User> upvotedUsers;

}
