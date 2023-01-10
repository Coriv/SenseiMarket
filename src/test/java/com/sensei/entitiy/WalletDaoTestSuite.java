package com.sensei.entitiy;

import com.sensei.entity.*;
import com.sensei.exception.WalletNotFoundException;
import com.sensei.repository.CryptocurrencyDao;
import com.sensei.repository.UserDao;
import com.sensei.repository.WalletDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WalletDaoTestSuite {
    @Autowired
    private WalletDao walletDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CryptocurrencyDao cryptocurrencyDao;

    @Test
    public void addingWalletTestSuite() throws WalletNotFoundException {
        //Given
        User user = new User();
        user.setFirstName("Sebastian");
        user.setLastName("Brown");
        user.setDateOfJoin(LocalDateTime.now());
        user.setActive(true);
        user.setUsername("Coriver@Testdf" + new Random().nextInt());
        user.setPassword("Password");
        user.setEmail("sebastian@kodilla.com");
        user.setPESEL("12345678910");
        user.setIdCard("AZC2133");

        Wallet wallet = new Wallet();
        wallet.setActive(true);
        wallet.setUser(user);
        user.setWallet(wallet);

        Cryptocurrency crypto1 = cryptocurrencyDao.findBySymbol("BTC").orElse(new Cryptocurrency("BTC", "Bitcoin"));
        Cryptocurrency crypto2 = cryptocurrencyDao.findBySymbol("ETH").orElse(new Cryptocurrency("ETH", "Ethereum"));

        WalletCrypto walletCrypto = new WalletCrypto();
        walletCrypto.setWallet(wallet);
        walletCrypto.setCryptocurrency(crypto1);
        walletCrypto.setQuantity(new BigDecimal("1200"));
        walletCrypto.setAddress("testAddress");

        WalletCrypto walletCrypto2 = new WalletCrypto();
        walletCrypto2.setWallet(wallet);
        walletCrypto2.setCryptocurrency(crypto2);
        walletCrypto2.setQuantity(new BigDecimal("982"));
        walletCrypto2.setAddress("testAddress");

        CashWallet cashWallet = new CashWallet();
        cashWallet.setQuantity(BigDecimal.valueOf(100));
        cashWallet.setWallet(wallet);
        wallet.setCashWallet(cashWallet);

        crypto1.getWalletCryptoList().add(walletCrypto);
        crypto2.getWalletCryptoList().add(walletCrypto2);
        wallet.getCryptosList().add(walletCrypto);
        wallet.getCryptosList().add(walletCrypto2);
        //When
        userDao.save(user);
        walletDao.save(wallet);
        //Then
        Wallet resultWallet = walletDao.findById(wallet.getId()).orElseThrow(WalletNotFoundException::new);
        assertEquals(resultWallet.getCryptosList().size(), 2);
        assertEquals(resultWallet.getUser().getId(), user.getId());
        assertEquals(resultWallet.getId(), wallet.getId());
        assertEquals(resultWallet.getCashWallet().getQuantity().doubleValue(), 100.00);
        //CleanUp
        walletDao.deleteById(wallet.getId());
        userDao.deleteById(user.getId());
    }
}
