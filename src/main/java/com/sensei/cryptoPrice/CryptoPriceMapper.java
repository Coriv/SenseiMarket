package com.sensei.cryptoPrice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CryptoPriceMapper {
    private final CryptoPriceDao cryptoPriceDao;

    public CryptoPrice mapToCryptoPrice(CryptoPriceDto cryptoPriceDto) {
        CryptoPrice cryptoPrice = new CryptoPrice();
        cryptoPrice.setSymbol(cryptoPriceDto.getSymbol());
        cryptoPrice.setBidPrice(new BigDecimal(cryptoPriceDto.getBidPrice()));
        cryptoPrice.setAskPrice(new BigDecimal(cryptoPriceDto.getAskPrice()));
        cryptoPrice.setVolume(new BigDecimal(cryptoPriceDto.getVolume()));
        cryptoPrice.setPriceChangePercent24h(new BigDecimal(cryptoPriceDto.getPriceChangePercent24h()));
        cryptoPrice.setTime(LocalDateTime.now());
        return cryptoPrice;
    }

    public CryptoPriceDto mapToCryptoPriceDto(CryptoPrice cryptoPrice) {
        return CryptoPriceDto.builder()
                .id(cryptoPrice.getId())
                .symbol(cryptoPrice.getSymbol())
                .bidPrice(cryptoPrice.getBidPrice().toString())
                .askPrice(cryptoPrice.getAskPrice().toString())
                .volume(cryptoPrice.getVolume().toString())
                .priceChangePercent24h(cryptoPrice.getPriceChangePercent24h().toString())
                .time(cryptoPrice.getTime())
                .build();
    }

    public List<CryptoPriceDto> mapToCryptoPricesDtoList(List<CryptoPrice> cryptoPrices) {
        return cryptoPrices.stream()
                .map(this::mapToCryptoPriceDto)
                .collect(Collectors.toList());
    }
}
