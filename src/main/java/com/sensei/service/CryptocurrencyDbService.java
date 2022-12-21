package com.sensei.service;

import com.sensei.entity.Cryptocurrency;
import com.sensei.exception.CryptocurrencyNotFoundException;
import com.sensei.repository.CryptocurrencyDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CryptocurrencyDbService {

    private final CryptocurrencyDao cryptocurrencyDao;

    public Cryptocurrency findCryptocurrencyBySymbol(String symbol) throws CryptocurrencyNotFoundException{
        return cryptocurrencyDao.findById(symbol).orElseThrow(CryptocurrencyNotFoundException::new);
    }
}
