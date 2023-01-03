package com.sensei.service;

import com.sensei.entity.CryptoPrice;
import com.sensei.exception.CryptocurrencyNotFoundException;
import com.sensei.exception.EmptyCryptocurrencyDatabaseException;
import com.sensei.externalService.BinancePairsService;
import com.sensei.mapper.CryptoPriceMapper;
import com.sensei.repository.CryptoPriceDao;
import com.sensei.repository.CryptocurrencyDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CryptoPriceService {

    private final CryptoPriceDao cryptoPriceDao;
    private final BinancePairsService binancePairsService;
    private final CryptoPriceMapper cryptoPriceMapper;
    private final CryptocurrencyDao cryptocurrencyDao;

    public void saveToDatabase() throws EmptyCryptocurrencyDatabaseException {
        binancePairsService.getPairLastPrice().stream()
                .map(cryptoPrice -> cryptoPriceMapper.mapToCryptoPrice(cryptoPrice))
                .forEach(cryptoPrice -> cryptoPriceDao.save(cryptoPrice));
    }

    public List<CryptoPrice> findBySymbol(String symbol, Long daysRange) throws CryptocurrencyNotFoundException {
        cryptocurrencyDao.findAll().stream()
                .filter(crypto -> crypto.getSymbol().equalsIgnoreCase(symbol))
                .findAny().orElseThrow(CryptocurrencyNotFoundException::new);
        List<CryptoPrice> prices = cryptoPriceDao.findBySymbolContainingIgnoreCaseOrderByTimeDesc(symbol);
        if (daysRange != null) {
            prices = prices.stream()
                    .filter(history -> history.getTime().isAfter(LocalDateTime.now().minusDays(daysRange)))
                    .collect(Collectors.toList());
        }
        return prices;
    }
}
