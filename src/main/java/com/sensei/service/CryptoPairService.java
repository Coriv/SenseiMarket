package com.sensei.service;

import com.sensei.config.AdminConfig;
import com.sensei.entity.CryptoPrice;
import com.sensei.entity.Cryptocurrency;
import com.sensei.exception.EmptyCryptocurrencyDatabaseException;
import com.sensei.repository.CryptoPairDao;
import com.sensei.repository.CryptocurrencyDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CryptoPairService {

    private final CryptoPairDao cryptoPairDao;
    private final CryptocurrencyDao cryptocurrencyDao;
    private final AdminConfig adminConfig;


    public List<CryptoPrice> prepareCryptoPairs() throws EmptyCryptocurrencyDatabaseException {
        Map<Cryptocurrency, CryptoPrice> map = new HashMap<>();
        List<Cryptocurrency> cryptos = cryptocurrencyDao.findAll();
        if(cryptos.size() == 0) {
            throw new EmptyCryptocurrencyDatabaseException();
        }
        for (Cryptocurrency crypto : cryptos) {

        }
        return null;
    }
}
