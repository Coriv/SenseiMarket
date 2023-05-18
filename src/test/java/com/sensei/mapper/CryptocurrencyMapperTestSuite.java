package com.sensei.mapper;

import com.sensei.cryptocurrency.CryptocurrencyDto;
import com.sensei.cryptocurrency.Cryptocurrency;
import com.sensei.cryptocurrency.CryptocurrencyMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CryptocurrencyMapperTestSuite {

    @Autowired
    private CryptocurrencyMapper cryptocurrencyMapper;

    @Test
    public void mapToCryptocurrencyTest() {
        CryptocurrencyDto bitcoinDto = CryptocurrencyDto.builder()
                .symbol("BTC")
                .name("Bitcoin").build();

        Cryptocurrency bitcoin = cryptocurrencyMapper.mapToCryptocurrency(bitcoinDto);

        assertEquals(bitcoin.getSymbol(), "BTC");
        assertEquals(bitcoin.getName(), "Bitcoin");
    }

    @Test
    public void mapToCryptocurrencyDtoTest() {
        Cryptocurrency ethereum = new Cryptocurrency();
        ethereum.setSymbol("ETH");
        ethereum.setName("Ethereum");

        CryptocurrencyDto ethereumDto = cryptocurrencyMapper.mapToCryptocurrencyDto(ethereum);

        assertEquals(ethereumDto.getSymbol(), "ETH");
        assertEquals(ethereumDto.getName(), "Ethereum");
    }

    @Test
    public void mapToCryptosDtoListTest() {
        Cryptocurrency bitcoin = new Cryptocurrency();
        bitcoin.setSymbol("BTC");
        bitcoin.setName("Bitcoin");
        Cryptocurrency ethereum = new Cryptocurrency();
        ethereum.setSymbol("ETH");
        ethereum.setName("Ethereum");

        List<Cryptocurrency> cryptos = Arrays.asList(bitcoin, ethereum);

        List<CryptocurrencyDto> cryptosDto = cryptocurrencyMapper.mapToCryptoListDto(cryptos);

        assertEquals(cryptosDto.size(), 2);
        assertEquals(cryptosDto.get(0).getSymbol(), "BTC");
        assertEquals(cryptosDto.get(0).getName(), "Bitcoin");
        assertEquals(cryptosDto.get(1).getSymbol(), "ETH");
        assertEquals(cryptosDto.get(1).getName(), "Ethereum");
    }
}