package com.sensei.cryptocurrency;

import com.sensei.exception.CryptoIsObjectOfTradingException;
import com.sensei.exception.CryptocurrencyNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cryptocurrency")
@RequiredArgsConstructor
public class CryptocurrencyController {

    private final CryptocurrencyService cryptoDbService;
    private final CryptocurrencyMapper cryptoMapper;

    @GetMapping
    public ResponseEntity<List<CryptocurrencyDto>> fetchCryptocurrencyList() {
        List<Cryptocurrency> cryptos = cryptoDbService.findAll();
        return ResponseEntity.ok(cryptoMapper.mapToCryptoListDto(cryptos));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addCryptocurrency(@Valid @RequestBody CryptocurrencyDto cryptocurrencydto) {
        cryptoDbService.add(cryptoMapper.mapToCryptocurrency(cryptocurrencydto));
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CryptocurrencyDto> updateCryptocurrencyData(@Valid @RequestBody CryptocurrencyDto cryptocurrencyDto) {
        var savedCrypto = cryptoDbService.add(cryptoMapper.mapToCryptocurrency(cryptocurrencyDto));
        return ResponseEntity.ok(cryptoMapper.mapToCryptocurrencyDto(savedCrypto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCryptocurrency(@RequestParam String symbol) throws CryptoIsObjectOfTradingException, CryptocurrencyNotFoundException {
        cryptoDbService.deleteBySymbol(symbol);
        return ResponseEntity.ok().build();
    }
}
