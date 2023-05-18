package com.sensei.cryptoPrice;

import com.sensei.exception.CryptocurrencyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/price")
public class CryptoPriceController {

    private final CryptoPriceService cryptoPriceService;
    private final CryptoPriceMapper cryptoPriceMapper;
    @GetMapping("/{symbol}")
    public ResponseEntity<List<CryptoPriceDto>> fetchHistoricalPriceOptionalFromLastParamDays(
            @PathVariable String symbol,
            @RequestParam(value = "daysRange", required = false) Long daysRange) throws CryptocurrencyNotFoundException {
        List<CryptoPrice> prices = cryptoPriceService.findBySymbol(symbol, daysRange);
        return ResponseEntity.ok(cryptoPriceMapper.mapToCryptoPricesDtoList(prices));
    }

    @GetMapping
    public ResponseEntity<List<CryptoPriceDto>> fetchNewestCryptoPrice() {
        List<CryptoPrice> price = cryptoPriceService.findTheNewestPrices();
        var cryptoPriceDtos = cryptoPriceMapper.mapToCryptoPricesDtoList(price);
        return ResponseEntity.ok(cryptoPriceDtos);
    }
}
