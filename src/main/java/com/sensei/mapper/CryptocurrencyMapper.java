package com.sensei.mapper;

import com.sensei.domain.CryptocurrencyDto;
import com.sensei.entity.Cryptocurrency;
import com.sensei.entity.WalletCrypto;
import com.sensei.repository.CryptocurrencyDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CryptocurrencyMapper {

    private final CryptocurrencyDao cryptocurrencyDao;

    public Cryptocurrency mapToCryptocurrency(CryptocurrencyDto cryptocurrencyDto) {
        Cryptocurrency cryptocurrency;
        if (cryptocurrencyDto.getSymbol() != null) {
            cryptocurrency = cryptocurrencyDao.findById(cryptocurrencyDto.getSymbol()).orElse(new Cryptocurrency());
        } else {
            cryptocurrency = new Cryptocurrency();
        }
        cryptocurrency.setSymbol(cryptocurrencyDto.getSymbol());
        cryptocurrency.setName(cryptocurrencyDto.getName());
        return cryptocurrency;
    }

    public CryptocurrencyDto mapToCryptocurrencyDto(Cryptocurrency cryptocurrency) {
        return CryptocurrencyDto.builder()
                .symbol(cryptocurrency.getSymbol())
                .name(cryptocurrency.getName())
                .walletsCryptoList(cryptocurrency.getWalletCryptoList().stream()
                        .map(WalletCrypto::getId)
                        .collect(Collectors.toList()))
                .build();
    }
}
