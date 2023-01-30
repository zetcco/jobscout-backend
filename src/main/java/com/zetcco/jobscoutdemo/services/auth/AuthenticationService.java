package com.zetcco.jobscoutdemo.services.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutdemo.controllers.auth.support.AuthenticationResponse;
import com.zetcco.jobscoutdemo.controllers.auth.support.LoginRequest;
import com.zetcco.jobscoutdemo.controllers.auth.support.OrganizationRegisterRequest;
import com.zetcco.jobscoutdemo.domain.Organization;
import com.zetcco.jobscoutdemo.domain.support.User;
// import com.zetcco.jobscoutdemo.repositories.JobCreatorRepository;
// import com.zetcco.jobscoutdemo.repositories.JobSeekerRepository;
import com.zetcco.jobscoutdemo.repositories.OrganizationRepository;
import com.zetcco.jobscoutdemo.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    // private final JobSeekerRepository jobSeekerRepository;
    // private final JobCreatorRepository jobCreatorRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registerOrganization(OrganizationRegisterRequest request) {
        Organization organization = new Organization(
                    request.getEmail(), 
                    passwordEncoder.encode(request.getPassword()), 
                    request.getAddress(),
                    request.getCompanyName(),
                    request.getBusinessRegistration());
        organizationRepository.save(organization);
        return jwtService.generateToken((User)organization);
    }

    public AuthenticationResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            return jwtService.generateToken(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
