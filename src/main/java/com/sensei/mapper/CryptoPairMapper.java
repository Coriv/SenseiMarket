package com.sensei.mapper;

import com.sensei.dto.CryptoPairDto;
import com.sensei.entity.CryptoPair;
import com.sensei.repository.CryptoPairDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CryptoPairMapper {
    private final CryptoPairDao cryptoPairDao;

    public CryptoPair mapToCryptoPair(CryptoPairDto cryptoPairDto) {
        CryptoPair cryptoPair = new CryptoPair();
        cryptoPair.setSymbol(cryptoPairDto.getSymbol());
        cryptoPair.setBidPrice(new BigDecimal(cryptoPairDto.getBidPrice()));
        cryptoPair.setAskPrice(new BigDecimal(cryptoPairDto.getAskPrice()));
        cryptoPair.setVolume(new BigDecimal(cryptoPairDto.getVolume()));
        cryptoPair.setPriceChangePercent(new BigDecimal(cryptoPairDto.getPriceChangePercent()));
        return cryptoPair;
    }

    public CryptoPairDto mapToCryptoPairDto(CryptoPair cryptoPair) {
        return CryptoPairDto.builder()
                .symbol(cryptoPair.getSymbol())
                .bidPrice(cryptoPair.getBidPrice().toString())
                .askPrice(cryptoPair.getAskPrice().toString())
                .volume(cryptoPair.getVolume().toString())
                .priceChangePercent(cryptoPair.getPriceChangePercent().toString())
                .build();
    }
}
