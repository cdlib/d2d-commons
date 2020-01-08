package org.cdlib.mail;

import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailServiceImpl implements MailService {

    private JavaMailSender mailSender;
    private static final Logger LOGGER = Logger.getLogger(MailServiceImpl.class);

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendSimpleMailMessage(MailMessage msg) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(msg.getFrom());
        message.setTo(msg.getTo());
        message.setSubject(msg.getSubject());
        message.setText(msg.getBody());

        try {
            mailSender.send(message);
        } catch (MailException e) {
            LOGGER.error("Failed to send email: " + e.getMessage());
            throw e;
        }
    }
}
