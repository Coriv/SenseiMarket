package com.sensei.service;

import com.sensei.config.AdminConfig;
import com.sensei.cryptocurrency.Cryptocurrency;
import com.sensei.exception.InvalidUserIdException;
import com.sensei.exception.UserNotVerifyException;
import com.sensei.exception.WalletAlreadyExistException;
import com.sensei.exception.WalletNotFoundException;
import com.sensei.cryptocurrency.CryptocurrencyDao;
import com.sensei.user.UserDao;
import com.sensei.wallet.WalletDao;
import com.sensei.user.User;
import com.sensei.wallet.Wallet;
import com.sensei.wallet.WalletService;
import com.sensei.walletCash.CashWallet;
import com.sensei.walletCrypto.WalletCrypto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WalletServiceTestSuite {
    @InjectMocks
    private WalletService dbService;
    @Mock
    private UserDao userDao;
    @Mock
    private WalletDao walletDao;
    @Mock
    private CryptocurrencyDao cryptocurrencyDao;
    @Mock
    private AdminConfig adminConfig;

    @Test
    void createWalletTest() throws InvalidUserIdException, WalletAlreadyExistException, UserNotVerifyException {
        User user = new User();
        user.setFirstName("Sebastian");
        user.setLastName("Brown");
        user.setPESEL("213331323");
        user.setIdCard("Add21");
        Wallet wallet = new Wallet();
        wallet.setActive(true);
        Cryptocurrency btc = new Cryptocurrency("BTC", "Bitcoin");

        when(userDao.findById(any())).thenReturn(Optional.of(user));
        when(walletDao.save(any(Wallet.class))).thenReturn(wallet);
        when(cryptocurrencyDao.findAll()).thenReturn(Arrays.asList(btc));
        when(adminConfig.getAddress()).thenReturn("address");
        // When
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
        String[] symbols = {"ALL"};

        when(walletDao.findById(any())).thenReturn(Optional.of(wallet));
        //When
        List<WalletCrypto> cryptos = dbService.getCryptosBySymbol(wallet.getId(), symbols);
        //Then
        assertEquals(cryptos.size(), 2);
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

    @Test
    void getWalletContentTest() throws WalletNotFoundException {
        Wallet wallet = new Wallet();
        Cryptocurrency btc = new Cryptocurrency("BTC", "Bitcoin");
        CashWallet cashWallet = new CashWallet();
        WalletCrypto walletCrypto = new WalletCrypto();
        walletCrypto.setCryptocurrency(btc);
        User user = new User();
        user.setId(44L);
        wallet.setUser(user);
        wallet.setCashWallet(cashWallet);
        wallet.setCryptosList(Arrays.asList(walletCrypto));
        wallet.setActive(true);
        when(walletDao.findById(any())).thenReturn(Optional.of(wallet));
        //when
        var resultWallet = dbService.getWalletContent(wallet.getId());
        //then
        assertTrue(resultWallet.isActive());
        assertEquals(44L, resultWallet.getUser().getId());
    }
}