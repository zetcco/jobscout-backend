package com.zetcco.jobscoutserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.Admin;
import com.zetcco.jobscoutserver.repositories.AdminRepository;
import com.zetcco.jobscoutserver.services.mappers.UserMapper;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserMapper userMapper;

    public ProfileDTO save(String email, String password) {
        Admin admin = new Admin(email, password);
        return userMapper.mapToDto(adminRepository.save(admin));
    }

}
