package com.sensei.entitiy;

import com.sensei.cryptocurrency.Cryptocurrency;
import com.sensei.cryptocurrency.CryptocurrencyDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CryptocurrencyDaoTestSuite {

    @Autowired
    private CryptocurrencyDao cryptocurrencyDao;

    @Test
    public void addingCryptocurrencyTest() {
        //Given
        Cryptocurrency crypto1 = new Cryptocurrency();
        crypto1.setSymbol("BTC@Test");
        crypto1.setName("Bitcoin@Test");

        Cryptocurrency crypto2 = new Cryptocurrency();
        crypto2.setSymbol("ETH@Test");
        crypto2.setName("Ethereum@Test");
        //when
        cryptocurrencyDao.save(crypto1);
        cryptocurrencyDao.save(crypto2);
        Optional<Cryptocurrency> bitcoin = cryptocurrencyDao.findBySymbol(crypto1.getSymbol());
        Optional<Cryptocurrency> ethereum = cryptocurrencyDao.findBySymbol(crypto2.getSymbol());
        //Then
        assertTrue(bitcoin.isPresent());
        assertTrue(ethereum.isPresent());
        assertEquals(bitcoin.get().getName(), "Bitcoin@Test");
        assertEquals(ethereum.get().getName(), "Ethereum@Test");
        //CleanUp
        cryptocurrencyDao.deleteById(crypto1.getSymbol());
        cryptocurrencyDao.deleteById(crypto2.getSymbol());
    }
}
