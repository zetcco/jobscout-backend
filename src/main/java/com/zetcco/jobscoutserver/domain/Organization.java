package com.zetcco.jobscoutserver.domain;

import java.util.List;

import com.zetcco.jobscoutserver.domain.support.Address;
import com.zetcco.jobscoutserver.domain.support.Role;
import com.zetcco.jobscoutserver.domain.support.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Organization extends User {
    
    @Column(unique = true)
    private String companyName;
    private String businessRegistration;
    private String logo;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "job_creator_id"))
    private List<JobCreator> jobCreators;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "job_creator_id"))
    private List<JobCreator> jobCreatorRequests;

    @OneToMany(fetch = FetchType.EAGER)
    private List<JobPost> jobPost;

    public Organization(String email, String password, Address address) {
        super(email, password, Role.ROLE_ORGANIZATION, address, null);
    }

    public Organization(String email, String password, Address address, String companyName, String businessRegistration) {
        super(email, password, Role.ROLE_ORGANIZATION, address, companyName);
        this.companyName = companyName;
        this.businessRegistration = businessRegistration;
    }
}
