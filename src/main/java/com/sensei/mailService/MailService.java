package com.sensei.mailService;

import com.sensei.config.MailMessageConfig;
import com.sensei.entity.Cryptocurrency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailMessageConfig mailMessageConfig;

    public void send(Mail mail) {
        mailSender.send(prepareMailMessage(mail));
    }

    private SimpleMailMessage prepareMailMessage(Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        return mailMessage;
    }

    public  void prepareNewCryptocurrencyMessage(String email, Cryptocurrency cryptocurrency) {
        Mail mail = Mail.builder()
                .mailTo(email)
                .subject(mailMessageConfig.getNEW_CRYPTO_SUBJECT())
                .message(mailMessageConfig.getNEW_CRYPTO_MESSAGE() + cryptocurrency.getSymbol() + ".\n" +
                mailMessageConfig.getSENSEI_WISHES())
                .build();
        send(mail);
    }
}
