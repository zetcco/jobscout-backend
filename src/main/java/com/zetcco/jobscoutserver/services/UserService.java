package com.zetcco.jobscoutserver.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.support.Role;
import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.domain.support.Socials.SocialPlatform;
import com.zetcco.jobscoutserver.domain.support.Socials.SocialProfile;
import com.zetcco.jobscoutserver.repositories.UserRepository;
import com.zetcco.jobscoutserver.services.support.ContactDetails;
import com.zetcco.jobscoutserver.services.support.NotFoundException;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Environment environment;

    // @TODO : Add exception handling here
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User with specified email was not found"));
    }

    public ProfileDTO setProfilePicture(String file) {
        User user = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        user.setDisplayPicture(file);
        userRepository.save(user);
        return this.getUser();
    }

    // @zetcco @TODO: Find more convinent way to attach the server url to media resources
    public ProfileDTO getUserProfileDTO(Long profileId) {
        User user = userRepository.findById(profileId).orElseThrow(() -> new NotFoundException("User not found"));
        return this.getUser(user);
    }

    public ContactDetails getContacts(Long profileId) {
        User user = userRepository.findById(profileId).orElseThrow(() -> new NotFoundException("User not found"));
        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setRole(user.getRole());
        contactDetails.setEmail(user.getEmail());
        if (user.getRole() == Role.ROLE_ORGANIZATION)
            contactDetails.setAddress(user.getAddress().toString());
        if (user.getRole() == Role.ROLE_JOB_SEEKER)
            contactDetails.setPhone(((JobSeeker)user).getContact());
        return contactDetails;
    }

    public ProfileDTO getUser() {
        User user = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return this.getUser(user);
    }

    public ProfileDTO getUser(User user) {
        return this.mapUser(user);
    }

    protected User getAuthUser() {
        return ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public List<SocialProfile> getSocialLinks(Long userId) {
        return this.getSocialLinks(this.getUser(userId));
    }

    public List<SocialProfile> getSocialLinks(User user) {

        List<String> socials = user.getSocialLinks();
        List<SocialProfile> socialProfiles = new ArrayList<>();

        for (String link : socials) {
            String host = link.replaceAll("http(s)?://|www\\.|/.*", "");
            String domainName = host.startsWith("www.") ? host.substring(4) : host;
            SocialPlatform platform;
            switch ( domainName ) {
                case "github.com":
                    platform = SocialPlatform.SOCIAL_GITHUB;
                    break;
                case "facebook.com":
                    platform = SocialPlatform.SOCIAL_FACEBOOK;
                    break;
                case "linkedin.com":
                    platform = SocialPlatform.SOCIAL_LINKEDIN;
                    break;
                default:
                    platform = SocialPlatform.SOCIAL_OTHER;
            }
            socialProfiles.add(new SocialProfile(platform, link));
        }

        return socialProfiles;
    }

    public List<SocialProfile> setSocialLinks(List<String> socials) {
        User user = getAuthUser();
        user.setSocialLinks(socials);
        userRepository.save(user);
        return this.getSocialLinks(user.getId());
    }

    protected User getUser(Long userId) throws NotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    private ProfileDTO mapUser(User user) {
        ProfileDTO profile = modelMapper.map(user, ProfileDTO.class);
        final String PROFILE_RESOURCE_URL = environment.getProperty("server.url") + "/media/file/";
        if (PROFILE_RESOURCE_URL != null && profile.getDisplayPicture() != null)
            profile.setDisplayPicture(PROFILE_RESOURCE_URL.concat(profile.getDisplayPicture()));
        return profile;
    }
}
