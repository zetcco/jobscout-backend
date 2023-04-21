package com.zetcco.jobscoutserver.services.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.controllers.auth.support.AuthenticationResponse;
import com.zetcco.jobscoutserver.controllers.auth.support.JobCreatorRegistrationRequest;
import com.zetcco.jobscoutserver.controllers.auth.support.JobSeekerRegistrationRequest;
import com.zetcco.jobscoutserver.controllers.auth.support.LoginRequest;
import com.zetcco.jobscoutserver.controllers.auth.support.OrganizationRegisterRequest;
import com.zetcco.jobscoutserver.domain.Admin;
import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.Organization;
import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.repositories.AdminRepository;
import com.zetcco.jobscoutserver.repositories.JobCreatorRepository;
import com.zetcco.jobscoutserver.repositories.JobSeekerRepository;
import com.zetcco.jobscoutserver.repositories.OrganizationRepository;
import com.zetcco.jobscoutserver.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final JobCreatorRepository jobCreatorRepository;
    private final AdminRepository adminRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse login(LoginRequest request) throws Exception {
        System.out.println("****************************");
        System.out.println(request.getEmail());
        System.out.println(request.getPassword());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        System.out.println("---------------------------");
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().jwtToken(token).build();
    }

    public AuthenticationResponse registerOrganization(OrganizationRegisterRequest request) {
        Organization organization = new Organization(
                    request.getEmail(), 
                    passwordEncoder.encode(request.getPassword()), 
                    request.getAddress(),
                    request.getCompanyName(),
                    request.getBrFileName());
        organizationRepository.save(organization);

        String token = jwtService.generateToken(organization);

        return AuthenticationResponse.builder().jwtToken(token).build();
    }

    public AuthenticationResponse registerJobSeeker(JobSeekerRegistrationRequest request) {
        JobSeeker jobSeeker = new JobSeeker(
                                            request.getEmail(),
                                            passwordEncoder.encode(request.getPassword()),
                                            request.getAddress(),
                                            request.getTitle(),
                                            request.getFirstName(),
                                            request.getLastName(),
                                            request.getContact(),
                                            request.getDob(),
                                            request.getGender());
        jobSeekerRepository.save(jobSeeker);
        String token = jwtService.generateToken(jobSeeker);

        return AuthenticationResponse.builder().jwtToken(token).build();
    }

    public AuthenticationResponse registerJobCreator(JobCreatorRegistrationRequest request) {
        JobCreator jobCreator = new JobCreator(
                                            request.getEmail(),
                                            passwordEncoder.encode(request.getPassword()),
                                            request.getAddress(),
                                            request.getTitle(),
                                            request.getFirstName(),
                                            request.getLastName(),
                                            request.getContact(),
                                            request.getDob(),
                                            request.getGender());
        jobCreatorRepository.save(jobCreator);
        String token = jwtService.generateToken(jobCreator);

        return AuthenticationResponse.builder().jwtToken(token).build();
    }

    public AuthenticationResponse registerAdmin(String email, String password) {
        Admin admin = new Admin(email, passwordEncoder.encode(password));
        admin = adminRepository.save(admin);
        String token = jwtService.generateToken(admin);
        return AuthenticationResponse.builder().jwtToken(token).build();
    }

    public Long getCurrentLoggedInUserId() {
        return ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
