package com.sensei.service;

import com.sensei.dto.DealDto;
import com.sensei.entity.*;
import com.sensei.exception.*;
import com.sensei.observer.Observer;
import com.sensei.repository.TradeDao;
import com.sensei.repository.TradeHistoryDao;
import com.sensei.repository.UserDao;
import com.sensei.repository.WalletDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TradeServiceTestSuite {

    @Autowired
    private TradeService tradeService;
    @MockBean
    private TradeDao tradeDao;
    @MockBean
    private UserDao userDao;
    @MockBean
    private WalletDao walletDao;
    @Autowired
    private TradeHistoryService tradeHistoryService;
    @MockBean
    private TradeHistoryDao tradeHistoryDao;
    private User user;
    private Wallet wallet;
    private Cryptocurrency btc;
    private Cryptocurrency eth;
    private Trade tradeBtcSell;
    private Trade tradeBtcBuy;
    private Trade tradeEthBuy;
    private Trade tradeEthSellClosed;
    private CashWallet cashWallet;
    private WalletCrypto walletCryptoBtc;
    private WalletCrypto walletCryptoEth;
    private List<Trade> trades;
    private User user2;
    private Wallet wallet2;
    private CashWallet cashWallet2;
    private WalletCrypto walletCrypto2Btc;

    @BeforeEach
    void prepareData() {
        user = new User();
        wallet = new Wallet();
        user.setWallet(wallet);
        wallet.setUser(user);
        cashWallet = new CashWallet();
        cashWallet.setWallet(wallet);
        cashWallet.setQuantity(BigDecimal.valueOf(1000));
        wallet.setCashWallet(cashWallet);
        btc = new Cryptocurrency("BTC", "Bitcoin");
        eth = new Cryptocurrency("ETH", "Ethereum");
        walletCryptoBtc = new WalletCrypto();
        walletCryptoEth = new WalletCrypto();
        walletCryptoBtc.setWallet(wallet);
        walletCryptoEth.setWallet(wallet);
        walletCryptoBtc.setCryptocurrency(btc);
        walletCryptoEth.setCryptocurrency(eth);
        walletCryptoBtc.setQuantity(BigDecimal.valueOf(1000));
        walletCryptoEth.setQuantity(BigDecimal.valueOf(750));
        tradeBtcSell = new Trade();
        tradeBtcSell.setTransactionType(TransactionType.SELL);
        tradeBtcSell.setCryptocurrency(btc);
        tradeBtcSell.setOpen(true);
        tradeBtcSell.setQuantity(BigDecimal.valueOf(100));
        tradeBtcSell.setPrice(BigDecimal.valueOf(5));
        tradeBtcSell.setValue(tradeBtcSell.getQuantity().multiply(tradeBtcSell.getPrice()));
        tradeBtcSell.setWallet(wallet);
        tradeBtcBuy = new Trade();
        tradeBtcBuy.setTransactionType(TransactionType.BUY);
        tradeBtcBuy.setCryptocurrency(btc);
        tradeBtcBuy.setOpen(true);
        tradeBtcBuy.setQuantity(BigDecimal.valueOf(300));
        tradeBtcBuy.setPrice(BigDecimal.valueOf(2));
        tradeBtcBuy.setValue(tradeBtcSell.getQuantity().multiply(tradeBtcSell.getPrice()));
        tradeBtcBuy.setWallet(wallet);
        tradeEthBuy = new Trade();
        tradeEthBuy.setTransactionType(TransactionType.BUY);
        tradeEthBuy.setCryptocurrency(eth);
        tradeEthBuy.setOpen(true);
        tradeEthBuy.setQuantity(BigDecimal.valueOf(500));
        tradeEthBuy.setPrice(BigDecimal.valueOf(7));
        tradeEthBuy.setValue(tradeBtcSell.getQuantity().multiply(tradeBtcSell.getPrice()));
        tradeEthBuy.setWallet(wallet);
        tradeEthSellClosed = new Trade();
        tradeEthSellClosed.setTransactionType(TransactionType.BUY);
        tradeEthSellClosed.setCryptocurrency(eth);
        tradeEthSellClosed.setOpen(false);
        tradeEthSellClosed.setQuantity(BigDecimal.valueOf(500));
        tradeEthSellClosed.setPrice(BigDecimal.valueOf(7));
        tradeEthSellClosed.setValue(tradeBtcSell.getQuantity().multiply(tradeBtcSell.getPrice()));
        tradeEthSellClosed.setWallet(wallet);
        trades = Arrays.asList(tradeBtcSell, tradeBtcBuy, tradeEthBuy, tradeEthSellClosed);
        user2 = new User();
        wallet2 = new Wallet();
        cashWallet2 = new CashWallet();
        walletCrypto2Btc = new WalletCrypto();
        user2.setWallet(wallet2);
        wallet2.setUser(user2);
        wallet2.setCashWallet(cashWallet2);
        wallet2.setCryptosList(Arrays.asList(walletCrypto2Btc));
        walletCrypto2Btc.setCryptocurrency(btc);
        walletCrypto2Btc.setQuantity(BigDecimal.valueOf(100));
        cashWallet2.setQuantity(BigDecimal.valueOf(100));
        wallet.setTrades(Arrays.asList(tradeBtcBuy, tradeEthBuy, tradeBtcSell, tradeEthSellClosed));
        wallet2.setTrades(Collections.emptyList());
        wallet.setId(1L);
        wallet2.setId(2L);
        tradeBtcBuy.setId(33L);
    }

    @Test
    void findOpenTradesWithParamTest() {
        when(tradeDao.findAllByOpenIsTrue()).thenReturn(trades);
        //When
        var tradesBtcBuy = tradeService.findOpenTrades("BTC", TransactionType.BUY);
        var tradesBTC = tradeService.findOpenTrades("BTC", null);
        var allOpenTrades = tradeService.findOpenTrades(null, null);
        //Then
        assertEquals(1, tradesBtcBuy.size());
        assertEquals(2, tradesBTC.size());
        assertEquals(4, allOpenTrades.size());
    }

    @Test
    void findAllUserTradesTest() throws InvalidUserIdException {
        when(userDao.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        //when
        var allUserTrades = tradeService.findAllUserTrades(user.getId(), true);
        var openUserTrades = tradeService.findAllUserTrades(user.getId(), false);
        //Then
        assertEquals(4, allUserTrades.size());
        assertEquals(3, openUserTrades.size());
    }

    @Test
    void createNewTradeBuyTest() throws NotEnoughFoundsException {
        when(tradeDao.save(any(Trade.class))).thenReturn(tradeBtcBuy);
        //when
        var newTradeBuy = tradeService.createNewTradeBuy(tradeBtcBuy);
        //then
        assertEquals(TransactionType.BUY, newTradeBuy.getTransactionType());
        assertEquals(BigDecimal.valueOf(500), cashWallet.getQuantity());
        cashWallet.setQuantity(BigDecimal.valueOf(300));
        assertThrows(NotEnoughFoundsException.class, () -> tradeService.createNewTradeBuy(tradeBtcBuy));
    }

    @Test
    void createNewTradeSellTest() throws WalletCryptoNotFoundException, NotEnoughFoundsException {
        when(tradeDao.save(any(Trade.class))).thenReturn(tradeBtcSell);
        //when
        var newTradeSell = tradeService.createNewTradeSell(tradeBtcSell);
        //then
        assertEquals(TransactionType.SELL, newTradeSell.getTransactionType());
        assertEquals(walletCryptoBtc.getQuantity(), BigDecimal.valueOf(900));
        walletCryptoBtc.setQuantity(BigDecimal.valueOf(50));
        assertThrows(NotEnoughFoundsException.class, () -> tradeService.createNewTradeSell(tradeBtcSell));
    }

    @Test
    void deleteTradeBuyTest() throws WalletCryptoNotFoundException, TradeNotFoundException {
        when(tradeDao.findById(tradeBtcBuy.getId())).thenReturn(Optional.of(tradeBtcBuy));
        //when
        tradeService.deleteTrade(tradeBtcBuy.getId());
        //then
        assertEquals(cashWallet.getQuantity(), BigDecimal.valueOf(1600));
    }

    @Test
    void deleteTradeSellTest() throws WalletCryptoNotFoundException, TradeNotFoundException {
        when(tradeDao.findById(tradeBtcSell.getId())).thenReturn(Optional.of(tradeBtcSell));
        //when
        tradeService.deleteTrade(tradeBtcSell.getId());
        //then
        assertEquals(walletCryptoBtc.getQuantity(), BigDecimal.valueOf(1100));

    }

    @Test
    void makeDealBuyTest() throws OfferIsCloseException, InvalidUserIdException, WalletCryptoNotFoundException, TradeNotFoundException, TradeWithYourselfException, NotEnoughFoundsException, CloneNotSupportedException {
        when(userDao.findById(user2.getId())).thenReturn(Optional.of(user2));
        when(tradeDao.findById(tradeBtcBuy.getId())).thenReturn(Optional.of(tradeBtcBuy));
        when(tradeHistoryDao.save(any())).thenReturn(null);
        //when
        tradeService.makeDeal(user2.getId(), tradeBtcBuy.getId(), BigDecimal.TEN);
        //then
        assertEquals(walletCryptoBtc.getQuantity(), BigDecimal.valueOf(1010));
        assertEquals(walletCrypto2Btc.getQuantity(), BigDecimal.valueOf(90));
        assertEquals(cashWallet.getQuantity(), BigDecimal.valueOf(1000));
        assertEquals(cashWallet2.getQuantity(), BigDecimal.valueOf(120));
        assertThrows(NotEnoughFoundsException.class, () -> tradeService.makeDeal(user2.getId(), tradeBtcBuy.getId(), BigDecimal.valueOf(10000)));
        tradeBtcBuy.setOpen(false);
        assertThrows(OfferIsCloseException.class, () -> tradeService.makeDeal(user2.getId(), tradeBtcBuy.getId(), BigDecimal.TEN));
        tradeBtcBuy.setOpen(true);
        when(userDao.findById(any())).thenReturn(Optional.of(user));
        assertThrows(TradeWithYourselfException.class, () -> tradeService.makeDeal(user.getId(), tradeBtcBuy.getId(), BigDecimal.TEN));
    }

    @Test
    void makeDealSellTest() throws OfferIsCloseException, InvalidUserIdException, WalletCryptoNotFoundException, TradeNotFoundException, TradeWithYourselfException, NotEnoughFoundsException, CloneNotSupportedException {
        when(userDao.findById(user2.getId())).thenReturn(Optional.of(user2));
        when(tradeDao.findById(tradeBtcSell.getId())).thenReturn(Optional.of(tradeBtcSell));
        when(tradeHistoryDao.save(any())).thenReturn(null);
        //when
        tradeService.makeDeal(user2.getId(), tradeBtcSell.getId(), BigDecimal.TEN);
        //then
        assertEquals(walletCryptoBtc.getQuantity(), BigDecimal.valueOf(1000));
        assertEquals(walletCrypto2Btc.getQuantity(), BigDecimal.valueOf(110));
        assertEquals(cashWallet.getQuantity(), BigDecimal.valueOf(1050));
        assertEquals(cashWallet2.getQuantity(), BigDecimal.valueOf(50));
        assertThrows(NotEnoughFoundsException.class, () -> tradeService.makeDeal(user2.getId(), tradeBtcSell.getId(), BigDecimal.valueOf(10000)));
        tradeBtcSell.setOpen(false);
        assertThrows(OfferIsCloseException.class, () -> tradeService.makeDeal(user2.getId(), tradeBtcSell.getId(), BigDecimal.TEN));
        tradeBtcSell.setOpen(true);
        when(userDao.findById(any())).thenReturn(Optional.of(user));
        assertThrows(TradeWithYourselfException.class, () -> tradeService.makeDeal(user.getId(), tradeBtcSell.getId(), BigDecimal.TEN));
    }
}