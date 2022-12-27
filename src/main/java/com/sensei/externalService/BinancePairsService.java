package com.sensei.externalService;

import com.sensei.config.AdminConfig;
import com.sensei.dto.CryptoPairDto;
import com.sensei.service.CryptocurrencyDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BinancePairsService {

    private final RestTemplate restTemplate;
    private final CryptocurrencyDbService cryptoDbService;
    private final AdminConfig adminConfig;
    public List<CryptoPairDto> getPairLastPrice() {
        CryptoPairDto[] pairs = restTemplate.getForObject(buildUrl(), CryptoPairDto[].class);
        return Arrays.asList(pairs);
    }

    private URI buildUrl() {
        return UriComponentsBuilder.fromHttpUrl(adminConfig.getBinanceApi())
                .queryParam("symbols", buildSymbolsParam())
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
    }
    private String buildSymbolsParam() {
        String symbols = cryptoDbService.findAll().stream()
                .map(crypto -> crypto.getSymbol())
                .map(symbol -> "\"" + symbol + "USDT" + "\",")
        .reduce("[", (partString, element) -> partString += element);
        String result = symbols.substring(0, symbols.length()-1);
        return result + "]";
    }
}
