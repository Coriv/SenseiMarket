package com.sensei.entitiy;

import com.sensei.entity.*;
import com.sensei.repository.*;
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
    private WalletDao walletDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CryptocurrencyDao cryptocurrencyDao;
    @Autowired
    private CashWalletDao cashWalletDao;

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
        Cryptocurrency bitcoin = new Cryptocurrency();
        bitcoin.setName("Bitcoin");
        bitcoin.setSymbol("BTC");

        wallet.setUser(user);
        user.setWallet(wallet);

        Trade trade = new Trade();
        trade.setTransactionType(TransactionType.BUY);
        trade.setWallet(wallet);
        trade.setCryptocurrency(bitcoin);
        trade.setPrice(BigDecimal.valueOf(3424));
        trade.setQuantity(BigDecimal.valueOf(344));
        trade.setValue(trade.getPrice().multiply(trade.getQuantity()));
        trade.setOpen(false);

        bitcoin.setTrades(Arrays.asList(trade));
        wallet.setTrades(Arrays.asList(trade));

        Cryptocurrency cryptocurrency = cryptocurrencyDao.findBySymbol("BTC").get();
        Cryptocurrency cryptocurrency2 = cryptocurrencyDao.findBySymbol("ETH").get();

        WalletCrypto walletCrypto = new WalletCrypto();
        walletCrypto.setWallet(wallet);
        walletCrypto.setQuantity(BigDecimal.valueOf(34));
        walletCrypto.setCryptocurrency(cryptocurrency);
        WalletCrypto walletCrypto2 = new WalletCrypto();
        walletCrypto2.setWallet(wallet);
        walletCrypto2.setQuantity(BigDecimal.valueOf(34535));
        walletCrypto2.setCryptocurrency(cryptocurrency2);
        wallet.setCryptosList(Arrays.asList(walletCrypto, walletCrypto2));

        CashWallet cashWallet = new CashWallet();
        cashWallet.setQuantity(BigDecimal.valueOf(100));
        cashWallet.setWallet(wallet);
        wallet.setCashWallet(cashWallet);

        //When
        //cashWalletDao.save(cashWallet);
        cryptocurrencyDao.save(bitcoin);
        userDao.save(user);

        walletDao.save(wallet);
        tradeDao.save(trade);
        Optional<Trade> resultTrade = tradeDao.findById(trade.getId());
        //Then
        assertTrue(resultTrade.isPresent());
        assertEquals(resultTrade.get().getCryptocurrency().getSymbol(), "BTC");
        assertEquals(resultTrade.get().getValue().doubleValue(), 1177856.00);
        assertEquals(resultTrade.get().getPrice().doubleValue(), 3424.0);
        assertEquals(resultTrade.get().getQuantity().doubleValue(), 344.0);
        //CleanUp
        //tradeDao.deleteById(trade.getId());
        //walletDao.deleteById(wallet.getId());
        //cryptoPairDao.deleteById(cryptoPrice.getSymbol());
    }
}
