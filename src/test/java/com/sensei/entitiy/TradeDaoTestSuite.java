package com.sensei.entitiy;

import com.sensei.entity.*;
import com.sensei.repository.CryptoPairDao;
import com.sensei.repository.TradeDao;
import com.sensei.repository.UserDao;
import com.sensei.repository.WalletDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TradeDaoTestSuite {
    @Autowired
    private TradeDao tradeDao;
    @Autowired
    private CryptoPairDao cryptoPairDao;
    @Autowired
    private UserDao userDao;

    @Test
    void addingNewTradeTest() {
        //Given
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
        CryptoPair cryptoPair = new CryptoPair();
        cryptoPair.setBidPrice(BigDecimal.valueOf(1000));
        cryptoPair.setAskPrice(BigDecimal.valueOf(103));
        cryptoPair.setSymbol("MATICUSDT");
        cryptoPair.setVolume(BigDecimal.valueOf(12032131));
        cryptoPair.setPriceChangePercent(BigDecimal.valueOf(12));

        wallet.setUser(user);
        user.setWallet(wallet);

        Trade trade = new Trade();
        trade.setTransactionType(TransactionType.BUY);
        trade.setWallet(wallet);
        trade.setCryptoPair(cryptoPair);
        trade.setPrice(BigDecimal.valueOf(3424));
        trade.setQuantity(BigDecimal.valueOf(344));
        trade.setValue(trade.getPrice().multiply(trade.getQuantity()));
        trade.setOpen(false);

        cryptoPair.setTrades(Arrays.asList(trade));
        wallet.setTrades(Arrays.asList(trade));

        //When
        cryptoPairDao.save(cryptoPair);
        userDao.save(user);
        //walletDao.save(wallet);
        tradeDao.save(trade);
        Optional<Trade> resultTrade = tradeDao.findById(trade.getId());
        //Then
        assertTrue(resultTrade.isPresent());
        assertEquals(resultTrade.get().getCryptoPair().getSymbol(), "BTCUSDT");
        assertEquals(resultTrade.get().getValue().doubleValue(), 341.00);
        assertEquals(resultTrade.get().getPrice().doubleValue(), 3424.0);
        assertEquals(resultTrade.get().getQuantity().doubleValue(), 344.0);
        //CleanUp
        //tradeDao.deleteById(trade.getId());
        //walletDao.deleteById(wallet.getId());
        //cryptoPairDao.deleteById(cryptoPair.getSymbol());
    }
}
