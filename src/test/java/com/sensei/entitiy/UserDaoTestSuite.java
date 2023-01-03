package com.sensei.entitiy;

import com.sensei.entity.User;
import com.sensei.entity.Cryptocurrency;
import com.sensei.entity.Wallet;
import com.sensei.entity.WalletCrypto;
import com.sensei.exception.CryptocurrencyNotFoundException;
import com.sensei.exception.InvalidUsernameException;
import com.sensei.repository.CryptocurrencyDao;
import com.sensei.repository.UserDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
public class UserDaoTestSuite {

    @Autowired
    private UserDao userDao;
    @Autowired
    private CryptocurrencyDao cryptocurrencyDao;
    @Test
    public void saveUserTest() throws InvalidUsernameException {
        //Given
        User user = new User();
        user.setFirstName("Sebastian");
        user.setLastName("Brown");
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

        Cryptocurrency crypto1 = cryptocurrencyDao.findBySymbol("BTC").orElse(new Cryptocurrency("BTC", "Bitcoin"));
        Cryptocurrency crypto2 = cryptocurrencyDao.findBySymbol("ETH").orElse(new Cryptocurrency("ETH", "Ethereum"));

        WalletCrypto walletCrypto = new WalletCrypto();
        walletCrypto.setWallet(wallet);
        walletCrypto.setCryptocurrency(crypto1);
        walletCrypto.setQuantity(new BigDecimal("1200"));
        walletCrypto.setAddress("testADDRESS");

        WalletCrypto walletCrypto2 = new WalletCrypto();
        walletCrypto2.setWallet(wallet);
        walletCrypto2.setCryptocurrency(crypto2);
        walletCrypto2.setQuantity(new BigDecimal("982"));
        walletCrypto2.setAddress("testAddress");

        crypto1.getWalletCryptoList().add(walletCrypto);
        crypto2.getWalletCryptoList().add(walletCrypto2);
        wallet.getCryptosList().add(walletCrypto);
        wallet.getCryptosList().add(walletCrypto2);
        //When
        userDao.save(user);
        User resultUser = userDao.findById(user.getId()).orElseThrow(InvalidUsernameException::new);
        //Then
        assertEquals(resultUser.getFirstName(), "Sebastian");
        assertEquals(user.getLastName(), "Brown");
        assertTrue(user.isActive());
        assertEquals(user.getEmail(), "sebastian@kodilla.com");
        assertEquals(user.getPESEL(), "12345678910");
        //CleanUp
        userDao.deleteById(user.getId());
    }
}
