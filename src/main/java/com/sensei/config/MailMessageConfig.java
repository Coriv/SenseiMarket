package com.sensei.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MailMessageConfig {

    private final String NEW_CRYPTO_SUBJECT = "New Cryptocurrency!";
    private final String NEW_CRYPTO_MESSAGE = "We are glad to inform you, that on Sensei Market from now \n" +
            "available is a trading with the new Cryptocurrency: ";
    private final String SENSEI_WISHES = "We wish you only good trades!\n\nRegards \nSenseiMarket Team";
}
