package com.zetcco.jobscoutdemo.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.support.Address;
import com.zetcco.jobscoutserver.domain.support.Role;
import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.repositories.UserRepository;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUser() {
        User user = User.builder()
                        .email("indjajith@gmail.com")
                        .password("hello")
                        .role(Role.ROLE_ORGANIZATION)
                        .address(Address.builder()
                                        .number("21/B")
                                        .street("Baker Street")
                                        .town("Mini London")
                                        .city("London")
                                        .province("East Kindom")
                                        .country("United Kindom")
                                        .build())
                        .displayPicture("https://.../...")
                        .build();
        userRepository.save(user);
    }

    @Test
    public void updatePassword() {
        User user = userRepository.findById(152L).orElseThrow();
        user.setEmail("New email update");
        userRepository.save(user);
    }
}
