package com.sensei.entitiy;

import com.sensei.entity.Cryptocurrency;
import com.sensei.entity.User;
import com.sensei.entity.Wallet;
import com.sensei.entity.WalletCrypto;
import com.sensei.repository.CryptocurrencyDao;
import com.sensei.repository.UserDao;
import com.sensei.repository.WalletCryptoDao;
import com.sensei.repository.WalletDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
public class UserDaoTestSuite {

    @Autowired
    private UserDao userDao;
    @Autowired
    private CryptocurrencyDao cryptocurrencyDao;
    @Autowired
    private WalletCryptoDao walletCryptoDao;
    @Autowired
    private WalletDao walletDao;
    @Test
    void addingNewCryptoToUserTest() {
        User user = new User();
        user.setFirstName("Sebastian");
        user.setLastName("Boron");
        user.setDateOfJoin(LocalDateTime.now());
        user.setActive(true);
        user.setUsername("Coriver");
        user.setPassword("Password");
        user.setEmail("sebastian@kodilla.com");
        user.setPESEL("12345678910");
        user.setIdCard("AZC2133");


        Wallet wallet = new Wallet();
        //wallet.setActive(true);
        wallet.setUser(user);

        user.setWallet(wallet);

        Cryptocurrency crypto1 = new Cryptocurrency();
        crypto1.setSymbol("BTC");
        crypto1.setName("Bitcoin");

        Cryptocurrency crypto2 = new Cryptocurrency();
        crypto2.setSymbol("ETH");
        crypto2.setName("Ethereum");

        WalletCrypto walletCrypto = new WalletCrypto();
        walletCrypto.setWallet(wallet);
        walletCrypto.setCryptocurrency(crypto1);
        walletCrypto.setQuantity(new BigDecimal("1200"));

        WalletCrypto walletCrypto2 = new WalletCrypto();
        walletCrypto2.setWallet(wallet);
        walletCrypto2.setCryptocurrency(crypto2);
        walletCrypto2.setQuantity(new BigDecimal("982"));

        crypto1.getWalletCryptoList().add(walletCrypto);
        crypto2.getWalletCryptoList().add(walletCrypto2);
        wallet.getCryptosList().add(walletCrypto);
        wallet.getCryptosList().add(walletCrypto2);



        userDao.save(user);

        userDao.deleteById(user.getId());

        cryptocurrencyDao.deleteById(crypto1.getSymbol());
        cryptocurrencyDao.deleteById(crypto2.getSymbol());

    }
}
