package com.zetcco.jobscoutserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zetcco.jobscoutserver.controllers.support.ProfileDTO;
import com.zetcco.jobscoutserver.services.UserService;
import com.zetcco.jobscoutserver.services.support.StorageService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<ProfileDTO> getUserProfile() {
        return new ResponseEntity<ProfileDTO>(userService.getUser(), HttpStatus.OK);
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long profileId) {
        return new ResponseEntity<ProfileDTO>(userService.getUser(profileId), HttpStatus.OK);
    }
    
    @PutMapping("/display-picture")
    public ResponseEntity<ProfileDTO> setProfilePicture(@RequestParam("file") MultipartFile file) {
        String filename = storageService.store(file);
        return new ResponseEntity<ProfileDTO>(userService.setProfilePicture(filename), HttpStatus.OK);
    }
}
