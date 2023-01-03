package com.sensei.mapper;

import com.sensei.dto.CryptocurrencyDto;
import com.sensei.entity.Cryptocurrency;
import com.sensei.entity.WalletCrypto;
import com.sensei.repository.CryptocurrencyDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CryptocurrencyMapper {

    private final CryptocurrencyDao cryptocurrencyDao;

    public Cryptocurrency mapToCryptocurrency(CryptocurrencyDto cryptocurrencyDto) {
        Cryptocurrency cryptocurrency = cryptocurrencyDao.findBySymbol(cryptocurrencyDto.getSymbol()).orElse(new Cryptocurrency());
        cryptocurrency.setSymbol(cryptocurrencyDto.getSymbol());
        cryptocurrency.setName(cryptocurrencyDto.getName());
        return cryptocurrency;
    }

    public CryptocurrencyDto mapToCryptocurrencyDto(Cryptocurrency cryptocurrency) {
        return CryptocurrencyDto.builder()
                .symbol(cryptocurrency.getSymbol())
                .name(cryptocurrency.getName())
                .build();
    }

    public List<CryptocurrencyDto> mapToCryptoListDto(List<Cryptocurrency> cryptos) {
        return cryptos.stream()
                .map(this::mapToCryptocurrencyDto)
                .collect(Collectors.toList());
    }
}
