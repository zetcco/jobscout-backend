package com.zetcco.jobscoutserver.domain;

import com.zetcco.jobscoutserver.domain.support.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recommondation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommondationId;

    private String content;

    @OneToOne
    private User requesterId;

    @OneToOne
    private User requestRespondeerId;
    
}
