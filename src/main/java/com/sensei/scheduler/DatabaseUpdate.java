package com.sensei.scheduler;

import com.sensei.exception.EmptyCryptocurrencyDatabaseException;
import com.sensei.service.CryptoPriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatabaseUpdate {

    private final CryptoPriceService cryptoPriceService;

    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedRate = 10)
    public void updateCurrentCryptoPrice() throws EmptyCryptocurrencyDatabaseException {
        log.info("Updating cryptocurrency price database...");
        try {
            cryptoPriceService.saveToDatabase();
            log.info("Entity CryptoPrice updated successfully.");
        } catch (HttpClientErrorException e) {
            log.error("Updating CryptoPrice entity filed. " + e.getMessage());
        }
    }
}
