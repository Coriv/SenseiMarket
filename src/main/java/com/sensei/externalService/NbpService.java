package com.sensei.externalService;

import com.sensei.config.AdminConfig;
import com.sensei.dto.ExchangeRateDto;
import com.sensei.dto.RatesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class NbpService {

    private final AdminConfig adminConfig;
    private final RestTemplate restTemplate;

    private RatesDto prepareExchangeRates() {
        var exchangeRates = restTemplate.getForObject(adminConfig.getUSD_RATE(), ExchangeRateDto.class);
        var rates = Arrays.asList(exchangeRates.getRates());
        return rates.get(0);
    }

    public BigDecimal exchangePlnToUsd(BigDecimal value) {
        return value.divide(prepareExchangeRates().getAsk(), RoundingMode.HALF_UP);
    }

    public BigDecimal exchangeUsdToPln(BigDecimal value) {
        return value.multiply(prepareExchangeRates().getBid());
    }
}
