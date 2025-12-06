package application.service;

import application.model.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender sender;

    public void send(EmailMessage emailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailMessage.recipient());
        message.setSubject(emailMessage.subject());
        message.setText(emailMessage.body());
    }
}
