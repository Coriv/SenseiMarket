package com.sensei.entitiy;

import com.sensei.entity.Cryptocurrency;
import com.sensei.entity.User;
import com.sensei.entity.Wallet;
import com.sensei.entity.WalletCrypto;
import com.sensei.repository.CryptocurrencyDao;
import com.sensei.repository.UserDao;
import com.sensei.repository.WalletCryptoDao;
import com.sensei.repository.WalletDao;
import org.junit.jupiter.api.Assertions;
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
    private WalletDao walletDao;
    @Autowired
    private WalletCryptoDao walletCryptoDao;

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
        wallet.setActive(true);
        wallet.setUser(user);

        user.setWallet(wallet);

        Cryptocurrency crypto1 = new Cryptocurrency();
        crypto1.setSymbol("BTC");
        crypto1.setName("Bitcoin");

        WalletCrypto walletCrypto = new WalletCrypto();
        walletCrypto.setWallet(wallet);
        walletCrypto.setCryptocurrency(crypto1);
        walletCrypto.setQuantity(new BigDecimal("1200"));

        crypto1.getWalletCryptoList().add(walletCrypto);
        wallet.getCryptosList().add(walletCrypto);

        cryptocurrencyDao.save(crypto1);
        userDao.save(user);


        BigDecimal quantity = userDao.findById(user.getId()).get().getWallet().getCryptosList().get(0).getQuantity();

        Assertions.assertEquals(new BigDecimal("1200.00"), quantity);
    }
}
