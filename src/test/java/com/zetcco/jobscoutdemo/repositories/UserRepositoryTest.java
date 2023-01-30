package com.zetcco.jobscoutdemo.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutdemo.domain.support.Address;
import com.zetcco.jobscoutdemo.domain.support.User;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUser() {
        User user = User.builder()
                        .email("indrajith@gmail.com")
                        .password("hello")
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
}
