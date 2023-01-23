package com.group.contestback.services;

import com.group.contestback.models.Mails;
import com.group.contestback.repositories.MailsRepo;
import com.group.contestback.repositories.NotificationTypesRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Properties;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EmailServiceCS{

    private final MailsRepo mailsRepo;
    private final NotificationTypesRepo notificationTypesRepo;


    @Value("${spring.mail.password}")
    private String userPassword;

    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.mail.ru");
        mailSender.setPort(587);

        mailSender.setUsername("contest-server@mail.ru");
        mailSender.setPassword(userPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
        public void sendSimpleMessage(Mails mails) {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("contest-server@mail.ru");
            message.setTo(mails.getToUserEmail());
            message.setSubject("Информация");
            message.setText(mails.getText());
            log.info("before email sent");
            mailsRepo.save(mails);



           // getJavaMailSender().send(message);
        }
}
