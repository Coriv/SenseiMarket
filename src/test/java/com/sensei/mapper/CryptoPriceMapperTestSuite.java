package com.sensei.mapper;

import com.sensei.cryptoPrice.CryptoPriceDto;
import com.sensei.cryptoPrice.CryptoPrice;
import com.sensei.cryptoPrice.CryptoPriceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CryptoPriceMapperTestSuite {

    @Autowired
    private CryptoPriceMapper cryptoPriceMapper;

    @Test
    void mapToCryptoPairTest() {
        //Given
        CryptoPriceDto cryptoPriceDto = CryptoPriceDto.builder()
                .symbol("BTCUSDT")
                .askPrice("100")
                .bidPrice("102")
                .priceChangePercent24h("1.23")
                .volume("10000")
                .build();
        //When
        CryptoPrice cryptoPrice = cryptoPriceMapper.mapToCryptoPrice(cryptoPriceDto);
        //Then
        assertEquals(cryptoPrice.getSymbol(), "BTCUSDT");
        assertEquals(cryptoPrice.getAskPrice(), BigDecimal.valueOf(100));
        assertEquals(cryptoPrice.getBidPrice(), BigDecimal.valueOf(102));
        assertEquals(cryptoPrice.getVolume(), BigDecimal.valueOf(10000));
    }

    @Test
    void mapToCryptoPriceDtoTest() {

        //Given
        CryptoPrice cryptoPrice = new CryptoPrice();
                cryptoPrice.setSymbol("BTCUSDT");
                cryptoPrice.setAskPrice(BigDecimal.valueOf(100));
                cryptoPrice.setBidPrice(BigDecimal.valueOf(102));
                cryptoPrice.setPriceChangePercent24h(BigDecimal.valueOf(1.23));
                cryptoPrice.setVolume(BigDecimal.valueOf(10000));
                cryptoPrice.setTime(LocalDateTime.now());
        //When
        CryptoPriceDto cryptoPriceDto = cryptoPriceMapper.mapToCryptoPriceDto(cryptoPrice);
        //Then
        assertEquals(cryptoPriceDto.getId(), cryptoPrice.getId());
        assertEquals(cryptoPriceDto.getSymbol(), "BTCUSDT");
        assertEquals(cryptoPriceDto.getAskPrice(), "100");
        assertEquals(cryptoPriceDto.getBidPrice(), "102");
        assertEquals(cryptoPriceDto.getVolume(), "10000");
        assertEquals(cryptoPriceDto.getTime(), cryptoPrice.getTime());
    }

    @Test
    void mapToCryptoPricesDtoListTest() {
        //Given
        CryptoPrice cryptoPrice = new CryptoPrice();
        cryptoPrice.setSymbol("BTCUSDT");
        cryptoPrice.setAskPrice(BigDecimal.valueOf(100));
        cryptoPrice.setBidPrice(BigDecimal.valueOf(102));
        cryptoPrice.setPriceChangePercent24h(BigDecimal.valueOf(1.23));
        cryptoPrice.setVolume(BigDecimal.valueOf(10000));
        cryptoPrice.setTime(LocalDateTime.now());
        List<CryptoPrice> prices = Arrays.asList(cryptoPrice);
        //when
        List<CryptoPriceDto> pricesDto = cryptoPriceMapper.mapToCryptoPricesDtoList(prices);
        //then
        assertEquals(pricesDto.size(), 1);
        assertEquals(pricesDto.get(0).getAskPrice(), "100");
        assertEquals(pricesDto.get(0).getPriceChangePercent24h(), "1.23");
    }
}