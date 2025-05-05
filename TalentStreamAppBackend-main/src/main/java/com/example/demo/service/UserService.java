package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.modal.User;
import com.example.demo.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    @Autowired
    public UserService(UserRepository userRepository, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
    }

    public User registerUser(User user) {
        sendEmail(user.getEmail(), "Account Register",
                "<h3>ThankYou for Choosing us</h3>"
                        + "<b><i>Your registration is Completed</i></b><br/><br/>UserName is : <b>"
                        + user.getEmail() + "</b><br/>Password : <b>" + user.getPassword() + "</b>");
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean adminLogin(User user) {
        // Your admin login logic here
        return true;
    }

    private void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            // Handle the exception or log the error
            e.printStackTrace();
        }
    }
}


