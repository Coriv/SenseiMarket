package com.sensei.service;

import com.sensei.entity.Cryptocurrency;
import com.sensei.entity.User;
import com.sensei.entity.Wallet;
import com.sensei.entity.WalletCrypto;
import com.sensei.exception.InvalidUserIdException;
import com.sensei.exception.WalletNotFoundException;
import com.sensei.repository.UserDao;
import com.sensei.repository.WalletDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WalletDbServiceTestSuite {
    @InjectMocks
    private WalletDbService dbService;
    @Mock
    private UserDao userDao;
    @Mock
    private WalletDao walletDao;
    @Test
    void createWalletTest() throws InvalidUserIdException {
        User user = new User();
        user.setFirstName("Sebastian");
        user.setLastName("Boron");
        Wallet wallet = new Wallet();
        wallet.setActive(true);
        wallet.setUser(user);
        user.setWallet(wallet);

        when(userDao.findById(any())).thenReturn(Optional.of(user));
        when(walletDao.save(any(Wallet.class))).thenReturn(wallet);
        //When
        Wallet resultWallet = dbService.createWallet(1L);
        //Then
        assertTrue(resultWallet.isActive());
    }

    @Test
    void getListOfCryptoTest() throws WalletNotFoundException {
        //Given
        Wallet wallet = new Wallet();
        wallet.setActive(true);
        Cryptocurrency crypto1 = new Cryptocurrency();
        crypto1.setSymbol("BTC");
        WalletCrypto walletCrypto = new WalletCrypto();
        walletCrypto.setCryptocurrency(crypto1);
        walletCrypto.setQuantity(new BigDecimal("1200"));
        walletCrypto.setWallet(wallet);
        wallet.getCryptosList().add(walletCrypto);

        when(walletDao.findById(any())).thenReturn(Optional.of(wallet));
        //When
        List<WalletCrypto> cryptos = dbService.getListOfCrypto(wallet.getId());
        //Then
        assertEquals(cryptos.size(), 1);
        assertEquals(cryptos.get(0).getQuantity(), new BigDecimal("1200"));
        assertEquals(cryptos.get(0).getCryptocurrency().getSymbol(), "BTC");

    }

    @Test
    void getCryptosBySymbolTest() throws WalletNotFoundException {

        String[] symbols = {"BTC", "ETH"};
        Wallet wallet = new Wallet();
        wallet.setId(32L);
        wallet.setActive(true);
        Cryptocurrency bitcoin = new Cryptocurrency();
        bitcoin.setSymbol("BTC");
        Cryptocurrency ethereum = new Cryptocurrency();
        ethereum.setSymbol("ETH");
        Cryptocurrency polygon = new Cryptocurrency();
        polygon.setSymbol("MATIC");

        WalletCrypto walletCryptoBTC = new WalletCrypto();
        walletCryptoBTC.setCryptocurrency(bitcoin);
        walletCryptoBTC.setQuantity(new BigDecimal("1200"));
        walletCryptoBTC.setWallet(wallet);

        WalletCrypto walletCryptoETH = new WalletCrypto();
        walletCryptoETH.setCryptocurrency(ethereum);
        walletCryptoETH.setQuantity(new BigDecimal("1230"));
        walletCryptoETH.setWallet(wallet);


        WalletCrypto walletCryptoMATIC = new WalletCrypto();
        walletCryptoMATIC.setCryptocurrency(polygon);
        walletCryptoMATIC.setQuantity(new BigDecimal("200"));
        walletCryptoMATIC.setWallet(wallet);

        wallet.setCryptosList(Arrays.asList(walletCryptoBTC, walletCryptoETH, walletCryptoMATIC));

        when(walletDao.findById(any())).thenReturn(Optional.of(wallet));
        //When
        List<WalletCrypto> cryptosBySymbol = dbService.getCryptosBySymbol(wallet.getId(), symbols);
        //Then
        assertEquals(cryptosBySymbol.size(), 2);
        assertEquals(cryptosBySymbol.get(0).getCryptocurrency().getSymbol(), "BTC");
        assertEquals(cryptosBySymbol.get(1).getCryptocurrency().getSymbol(), "ETH");

    }
}