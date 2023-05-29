package com.zetcco.jobscoutserver.services.support;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.domain.support.VerificationKey;
import com.zetcco.jobscoutserver.repositories.support.VerificationKeyRepository;
import com.zetcco.jobscoutserver.services.UserService;

@Service
public class VerificationService {
    
    @Value("${spring.mail.username}") private String SERVER_EMAIL;
    @Value("${server.backend_url}") private String SERVER_URL;

    @Autowired private VerificationKeyRepository verificationKeyRepository;
    @Autowired private JavaMailSender emailSender;
    @Autowired private UserService userService;

    public void sendConfirmationLink(User user) {
        SimpleMailMessage message = new SimpleMailMessage();

        UUID key = getVerificationKey(user);
        String verificationLink = String.format("%s/verify?key=%s", SERVER_URL, key.toString());
        String body = String.format("Please follow the link below to verify your account,\n\n%s\n\nThank you!\nSupport,\nIT-Scout", verificationLink);

        message.setFrom(SERVER_EMAIL);
        message.setTo(user.getEmail());
        message.setSubject("IT-Scout Account Verification");
        message.setText(body);
        emailSender.send(message);
    }

    private UUID getVerificationKey(User user) {
        UUID verificationKey = UUID.randomUUID();
        VerificationKey key = new VerificationKey(null, verificationKey, user);
        key = verificationKeyRepository.save(key);
        return key.getVerificationKey();
    }

    public void verifyAccount(UUID key) {
        VerificationKey verificationKey = verificationKeyRepository.findByVerificationKey(key);
        userService.verifyAccount(verificationKey.getUser());
    }

}
