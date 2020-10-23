package pl.filmveeb.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final JavaMailSender javaMailSender;

    public MessageService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendResetMail(String token, String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("mbalicki25@gmail.com");
        simpleMailMessage.setSubject("Zresetuj hasło");
        simpleMailMessage.setText("localhost8082:/user/changePasswordForm/" + token);
        javaMailSender.send(simpleMailMessage);
    }
}
