package com.sensei.mapper;

import com.sensei.dto.WalletCryptoDto;
import com.sensei.entity.Cryptocurrency;
import com.sensei.entity.Wallet;
import com.sensei.entity.WalletCrypto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WalletCryptoMapperTestSuite {

    @Autowired
    private WalletCryptoMapper walletCryptoMapper;

    @Test
    void mapToWalletCryptoDtoTest() {
        //Given
        Wallet wallet = new Wallet();
        Cryptocurrency btc = new Cryptocurrency("BTC", "Bitcoin");
        WalletCrypto walletCrypto = new WalletCrypto();
        walletCrypto.setWallet(wallet);
        walletCrypto.setCryptocurrency(btc);
        walletCrypto.setQuantity(BigDecimal.valueOf(2000));
        //When
        WalletCryptoDto walletCryptoDto = walletCryptoMapper.mapToWalletCryptoDto(walletCrypto);
        //then
        assertEquals(walletCryptoDto.getWalletId(), wallet.getId());
        assertEquals(walletCryptoDto.getCryptocurrencySymbol(), btc.getSymbol());
        assertEquals(walletCryptoDto.getId(), walletCrypto.getId());
        assertEquals(walletCryptoDto.getQuantity(), BigDecimal.valueOf(2000));
    }

    @Test
    void mapToWalletCryptoListDtoTest() {
        //Given
        Wallet wallet = new Wallet();
        Cryptocurrency btc = new Cryptocurrency("BTC", "Bitcoin");
        WalletCrypto walletCrypto = new WalletCrypto();
        walletCrypto.setWallet(wallet);
        walletCrypto.setCryptocurrency(btc);
        walletCrypto.setQuantity(BigDecimal.valueOf(2000));
        List<WalletCrypto> cryptos = Arrays.asList(walletCrypto);
        //When
        List<WalletCryptoDto> cryptosDto = walletCryptoMapper.mapToWalletCryptoListDto(cryptos);
        //then
        assertEquals(cryptosDto.size(), 1);
        assertEquals(cryptosDto.get(0).getWalletId(), wallet.getId());
        assertEquals(cryptosDto.get(0).getCryptocurrencySymbol(), btc.getSymbol());
        assertEquals(cryptosDto.get(0).getId(), walletCrypto.getId());
        assertEquals(cryptosDto.get(0).getQuantity(), BigDecimal.valueOf(2000));
    }
}