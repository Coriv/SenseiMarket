package com.sensei.service;

import com.sensei.entity.Cryptocurrency;
import com.sensei.exception.CryptoIsObjectOfTradingException;
import com.sensei.exception.CryptocurrencyNotFoundException;
import com.sensei.repository.CryptocurrencyDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptocurrencyDbService {

    private final CryptocurrencyDao cryptocurrencyDao;

    public Cryptocurrency findCryptocurrencyBySymbol(String symbol) throws CryptocurrencyNotFoundException {
        return cryptocurrencyDao.findBySymbol(symbol).orElseThrow(CryptocurrencyNotFoundException::new);
    }

    public List<Cryptocurrency> findAll() {
        return cryptocurrencyDao.findAll();
    }

    public Cryptocurrency add(Cryptocurrency cryptocurrency) {
        return cryptocurrencyDao.save(cryptocurrency);
    }

    public void deleteBySymbol(String symbol) throws CryptocurrencyNotFoundException, CryptoIsObjectOfTradingException {
        Cryptocurrency crypto = cryptocurrencyDao.findBySymbol(symbol).orElseThrow(CryptocurrencyNotFoundException::new);
        if (!crypto.getWalletCryptoList().isEmpty()) {
            throw new CryptoIsObjectOfTradingException();
        }
        cryptocurrencyDao.deleteBySymbol(symbol);
    }
}
