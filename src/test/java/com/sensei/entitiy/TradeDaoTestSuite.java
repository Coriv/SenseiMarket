package com.sensei.entitiy;

import com.sensei.cryptocurrency.Cryptocurrency;
import com.sensei.exception.TradeNotFoundException;
import com.sensei.cryptocurrency.CryptocurrencyDao;
import com.sensei.trade.TradeDao;
import com.sensei.trade.Trade;
import com.sensei.trade.TransactionType;
import com.sensei.user.UserDao;
import com.sensei.wallet.WalletDao;
import com.sensei.user.User;
import com.sensei.wallet.Wallet;
import com.sensei.walletCash.CashWallet;
import com.sensei.walletCrypto.WalletCrypto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TradeDaoTestSuite {
    @Autowired
    private TradeDao tradeDao;
    @Autowired
    private WalletDao walletDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CryptocurrencyDao cryptocurrencyDao;

    @Test
    void addingNewTradeTest() throws TradeNotFoundException {
        //Given
        User user = new User();
        user.setFirstName("Marek");
        user.setLastName("Pawel");
        user.setDateOfJoin(LocalDateTime.now());
        user.setActive(true);
        user.setUsername("Drugi");
        user.setPassword("haslo");
        user.setEmail("test@test.com");
        user.setPESEL("12345423454");
        user.setIdCard("KKK342234");

        Wallet wallet = new Wallet();

        Cryptocurrency bitcoin = cryptocurrencyDao.findBySymbol("BTC").orElse(new Cryptocurrency("BTC", "Bitcoin"));
        Cryptocurrency ethereum = cryptocurrencyDao.findBySymbol("ETH").orElse(new Cryptocurrency("ETH", "Ethereum"));

        wallet.setUser(user);
        user.setWallet(wallet);

        Trade trade = new Trade();
        trade.setTransactionType(TransactionType.BUY);
        trade.setWallet(wallet);
        trade.setCryptocurrency(bitcoin);
        trade.setPrice(BigDecimal.valueOf(1));
        trade.setQuantity(BigDecimal.valueOf(100));
        trade.setValue(trade.getPrice().multiply(trade.getQuantity()));
        trade.setOpen(true);

        bitcoin.setTrades(Arrays.asList(trade));
        wallet.setTrades(Arrays.asList(trade));


        WalletCrypto walletCrypto = new WalletCrypto();
        walletCrypto.setWallet(wallet);
        walletCrypto.setQuantity(BigDecimal.valueOf(100));
        walletCrypto.setCryptocurrency(bitcoin);
        walletCrypto.setAddress("adresssssssssss");
        WalletCrypto walletCrypto2 = new WalletCrypto();
        walletCrypto2.setWallet(wallet);
        walletCrypto2.setQuantity(BigDecimal.valueOf(100));
        walletCrypto2.setCryptocurrency(ethereum);
        walletCrypto2.setAddress("dsassdsafdsva");
        wallet.setCryptosList(Arrays.asList(walletCrypto, walletCrypto2));

        CashWallet cashWallet = new CashWallet();
        cashWallet.setQuantity(BigDecimal.valueOf(100));
        cashWallet.setWallet(wallet);
        wallet.setCashWallet(cashWallet);

        //When
        userDao.save(user);
        tradeDao.save(trade);
        Trade resultTrade = tradeDao.findById(trade.getId()).orElseThrow(TradeNotFoundException::new);
        //Then
        assertEquals(resultTrade.getCryptocurrency().getSymbol(), "BTC");
        assertEquals(resultTrade.getValue().doubleValue(), 100.00);
        assertEquals(resultTrade.getPrice().doubleValue(), 1.00);
        assertEquals(resultTrade.getQuantity().doubleValue(), 100.00);
        //CleanUp
        tradeDao.deleteById(trade.getId());
        walletDao.deleteById(wallet.getId());
        userDao.deleteById(user.getId());
    }
}
