package com.sensei.mapper;

import com.sensei.dto.CryptoPairDto;
import com.sensei.entity.CryptoPrice;
import com.sensei.repository.CryptoPairDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CryptoPairMapper {
    private final CryptoPairDao cryptoPairDao;

    public CryptoPrice mapToCryptoPair(CryptoPairDto cryptoPairDto) {
        CryptoPrice cryptoPrice = new CryptoPrice();
        cryptoPrice.setSymbol(cryptoPairDto.getSymbol());
        cryptoPrice.setBidPrice(new BigDecimal(cryptoPairDto.getBidPrice()));
        cryptoPrice.setAskPrice(new BigDecimal(cryptoPairDto.getAskPrice()));
        cryptoPrice.setVolume(new BigDecimal(cryptoPairDto.getVolume()));
        cryptoPrice.setPriceChangePercent(new BigDecimal(cryptoPairDto.getPriceChangePercent()));
        return cryptoPrice;
    }

    public CryptoPairDto mapToCryptoPairDto(CryptoPrice cryptoPrice) {
        return CryptoPairDto.builder()
                .symbol(cryptoPrice.getSymbol())
                .bidPrice(cryptoPrice.getBidPrice().toString())
                .askPrice(cryptoPrice.getAskPrice().toString())
                .volume(cryptoPrice.getVolume().toString())
                .priceChangePercent(cryptoPrice.getPriceChangePercent().toString())
                .build();
    }
}
