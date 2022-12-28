package com.sensei.externalService;

import com.sensei.config.AdminConfig;
import com.sensei.dto.CryptoPairDto;
import com.sensei.entity.Cryptocurrency;
import com.sensei.exception.EmptyCryptocurrencyDatabaseException;
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
    public List<CryptoPairDto> getPairLastPrice() throws EmptyCryptocurrencyDatabaseException {
        CryptoPairDto[] pairs = restTemplate.getForObject(buildUrl(), CryptoPairDto[].class);
        return Arrays.asList(pairs);
    }

    private URI buildUrl() throws EmptyCryptocurrencyDatabaseException {
        return UriComponentsBuilder.fromHttpUrl(adminConfig.getBinanceApi())
                .queryParam("symbols", buildSymbolsParam())
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
    }
    private String buildSymbolsParam() throws EmptyCryptocurrencyDatabaseException {
        List<Cryptocurrency> cryptos = cryptoDbService.findAll();
        if(cryptos.size() == 0) {
            throw new EmptyCryptocurrencyDatabaseException();
        }
        String symbols = cryptos.stream()
                .map(crypto -> crypto.getSymbol())
                .map(symbol -> "\"" + symbol + adminConfig.getStablecoin() + "\",")
        .reduce("[", (partString, element) -> partString += element);
        String result = symbols.substring(0, symbols.length()-1);
        return result + "]";
    }
}
