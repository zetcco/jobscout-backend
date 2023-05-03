package com.zetcco.jobscoutserver.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.domain.support.Role;
import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.domain.support.Socials.SocialProfile;
import com.zetcco.jobscoutserver.repositories.support.specifications.users.User.NameSpecification;
import com.zetcco.jobscoutserver.repositories.support.specifications.users.User.RoleSpecification;
import com.zetcco.jobscoutserver.services.UserService;
import com.zetcco.jobscoutserver.services.mappers.UserMapper;
import com.zetcco.jobscoutserver.services.support.ContactDetails;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;
import com.zetcco.jobscoutserver.services.support.StorageService;
import com.zetcco.jobscoutserver.services.support.exceptions.NotFoundException;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public ResponseEntity<ProfileDTO> getUserProfile() {
        return new ResponseEntity<ProfileDTO>(userService.getUser(), HttpStatus.OK);
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long profileId) {
        // TODO: Change this to use UserMapper
        return new ResponseEntity<ProfileDTO>(userService.getUserProfileDTO(profileId), HttpStatus.OK);
    }

    @GetMapping("/{profileId}/contacts")
    public ResponseEntity<ContactDetails> getContacts(@PathVariable Long profileId) {
        return new ResponseEntity<ContactDetails>(userService.getContacts(profileId), HttpStatus.OK);
    }
    
    // TODO: Set proper HttpStatus codes for exceptions
    @PutMapping("/display-picture")
    public ResponseEntity<ProfileDTO> setProfilePicture(@RequestParam("file") MultipartFile file) {
        try {
            String filename = storageService.store(file);
            return new ResponseEntity<ProfileDTO>(userService.setProfilePicture(filename), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ProfileDTO> getByEmail(@PathVariable String email) {
        try {
            return new ResponseEntity<ProfileDTO>(userMapper.mapToDto(userService.loadUserByEmail(email)), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/socials")
    public ResponseEntity<List<SocialProfile>> setSocials(@RequestBody Map<String, List<String>> body) {
        try {
            List<String> links = body.get("links");
            return new ResponseEntity<List<SocialProfile>>(userService.setSocialLinks(links), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("{userId}/socials")
    public ResponseEntity<List<SocialProfile>> getSocials(@PathVariable Long userId) {
        try {
            return new ResponseEntity<List<SocialProfile>>(userService.getSocialLinks(userId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProfileDTO>> searchUsers(
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "role", required = false) Role role
    ) {
        Specification<User> user_specs = Specification.allOf(
            new NameSpecification(name),
            new RoleSpecification(role)
        );

        return new ResponseEntity<List<ProfileDTO>>(userService.searchUsers(user_specs), HttpStatus.OK);
    }
}
