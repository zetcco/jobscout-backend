package com.zetcco.jobscoutdemo.domain.support;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Inheritance(strategy = InheritanceType.JOINED)
@Data
@SuperBuilder
@Entity
@Table(name = "_user")
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "number", column = @Column(name = "addr_number")),
        @AttributeOverride(name = "street", column = @Column(name = "addr_street")),
        @AttributeOverride(name = "town", column = @Column(name = "addr_town")),
        @AttributeOverride(name = "city", column = @Column(name = "addr_city")),
        @AttributeOverride(name = "province", column = @Column(name = "addr_province")),
        @AttributeOverride(name = "country", column = @Column(name = "addr_country"))
    })
    private Address address;

    private String displayPicture;

    protected User(String email, String password, Role role, Address address) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.address = address;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }
    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

}
