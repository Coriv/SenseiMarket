package com.sensei.service;

import com.sensei.entity.CryptoPrice;
import com.sensei.entity.Cryptocurrency;
import com.sensei.exception.CryptocurrencyNotFoundException;
import com.sensei.repository.CryptoPriceDao;
import com.sensei.repository.CryptocurrencyDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CryptoPriceServiceTestSuite {
    @InjectMocks
    private CryptoPriceService service;
    @Mock
    private CryptoPriceDao cryptoPriceDao;
    @Mock
    private CryptocurrencyDao cryptocurrencyDao;

    @Test
    void findBySymbolTest() throws CryptocurrencyNotFoundException {
        Cryptocurrency btc = new Cryptocurrency("BTC", "Bitcoin");
        Cryptocurrency eth = new Cryptocurrency("ETH", "Ethereum");
        var cryptos = Arrays.asList(btc, eth);
        CryptoPrice priceBTC = new CryptoPrice();
        priceBTC.setSymbol("BTC");
        priceBTC.setTime(LocalDateTime.now());
        priceBTC.setPriceChangePercent24h(BigDecimal.valueOf(10));
        priceBTC.setAskPrice(BigDecimal.valueOf(100));
        priceBTC.setBidPrice(BigDecimal.valueOf(101));
        priceBTC.setVolume(BigDecimal.valueOf(10000));
        when(cryptocurrencyDao.findAll()).thenReturn(cryptos);
        when(cryptoPriceDao.findBySymbolContainingIgnoreCaseOrderByTimeDesc("BTC"))
                .thenReturn(Arrays.asList(priceBTC));
        //when
        var pricesBySymbol = service.findBySymbol("BTC", 10L);
        //then
        assertEquals(pricesBySymbol.size(), 1);
        assertEquals(pricesBySymbol.get(0).getSymbol(), "BTC");
        assertEquals(pricesBySymbol.get(0).getBidPrice(), BigDecimal.valueOf(101));
    }

    @Test
    void findTheNewestPricesTest() {
        CryptoPrice priceBTC = new CryptoPrice();
        priceBTC.setSymbol("BTC");
        priceBTC.setTime(LocalDateTime.now());
        priceBTC.setPriceChangePercent24h(BigDecimal.valueOf(10));
        priceBTC.setAskPrice(BigDecimal.valueOf(100));
        priceBTC.setBidPrice(BigDecimal.valueOf(101));
        priceBTC.setVolume(BigDecimal.valueOf(10000));
        when(cryptoPriceDao.findAllByTimeIsAfter(any()))
                .thenReturn(Arrays.asList(priceBTC));
        //when
        var prices = service.findTheNewestPrices();
        //then
        assertEquals(prices.size(), 1);
        assertEquals(prices.get(0).getSymbol(), "BTC");
        assertEquals(prices.get(0).getBidPrice(), BigDecimal.valueOf(101));
    }
}