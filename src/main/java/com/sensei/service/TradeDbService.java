package com.sensei.service;

import com.sensei.dto.DealDto;
import com.sensei.entity.*;
import com.sensei.exception.*;
import com.sensei.observer.Observable;
import com.sensei.observer.Observer;
import com.sensei.repository.TradeDao;
import com.sensei.repository.UserDao;
import com.sensei.repository.WalletDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeDbService {

    private final TradeDao tradeDao;
    private final UserDao userDao;
    private final WalletDao walletDao;
    private final TransactionHistoryDbService historyService;
    private String cryptoSymbol;
    private List<Observer> observers = new ArrayList<>();

    public List<Trade> findOpenTrades(String symbol, TransactionType type) {
        List<Trade> trades = tradeDao.findAllByOpenIsTrue();
        if (symbol != null) {
            trades = tradeDao.findAllByOpenIsTrue().stream()
                    .filter(trade -> trade.getCryptocurrency().getSymbol().equals(symbol))
                    .collect(Collectors.toList());
        }
        if (type != null) {
            trades = tradeDao.findAllByOpenIsTrue().stream()
                    .filter(trade -> trade.getTransactionType().equals(type))
                    .collect(Collectors.toList());
        }
        return trades;
    }

    public List<Trade> findAllUserTrades(Long userId, boolean showClosed) throws InvalidUserIdException {
        var user = userDao.findById(userId).orElseThrow(InvalidUserIdException::new);
        if (showClosed == true) {
            return user.getWallet().getTrades();
        }
        return user.getWallet().getTrades().stream()
                .filter(trade -> trade.isOpen() == true)
                .collect(Collectors.toList());
    }

    public Trade createNewTradeBuy(Trade trade) throws NotEnoughFoundsException {
        Wallet wallet = trade.getWallet();
        var cashQuantity = wallet.getCashWallet().getQuantity();
        if (cashQuantity.compareTo(trade.getValue()) < 0) {
            throw new NotEnoughFoundsException();
        } else {
            wallet.getCashWallet().setQuantity(cashQuantity.subtract(trade.getValue()));
        }
        trade.setOpenDate(LocalDateTime.now());
        return tradeDao.save(trade);
    }

    public Trade createNewTradeSell(Trade trade) throws NotEnoughFoundsException, WalletCryptoNotFoundException {
        cryptoSymbol = trade.getCryptocurrency().getSymbol();
        var wallet = trade.getWallet();
        var walletCrypto = fetchWalletCrypto(wallet, cryptoSymbol);
        var quantity = walletCrypto.getQuantity();
        if (quantity.compareTo(trade.getQuantity()) < 0) {
            throw new NotEnoughFoundsException();
        } else {
            walletCrypto.setQuantity(quantity.subtract(trade.getQuantity()));
        }
        trade.setOpenDate(LocalDateTime.now());
        return tradeDao.save(trade);
    }

    public void deleteTrade(Long tradeId) throws TradeNotFoundException, WalletCryptoNotFoundException {
        var trade = tradeDao.findById(tradeId).orElseThrow(TradeNotFoundException::new);
        var wallet = trade.getWallet();
        if (trade.getTransactionType().equals(TransactionType.BUY)) {
            var quantity = wallet.getCashWallet().getQuantity();
            wallet.getCashWallet().setQuantity(quantity.add(trade.getQuantity()));
        } else {
            cryptoSymbol = trade.getCryptocurrency().getSymbol();
            var walletCrypto = fetchWalletCrypto(wallet, cryptoSymbol);
            walletCrypto.setQuantity(walletCrypto.getQuantity().add(trade.getQuantity()));
        }
        walletDao.save(wallet);
        tradeDao.deleteById(tradeId);
    }

    public DealDto makeDeal(Long userId, Long tradeId, BigDecimal quantity) throws InvalidUserIdException, TradeNotFoundException, TradeWithYourselfException, NotEnoughFoundsException, WalletCryptoNotFoundException, CloneNotSupportedException, OfferIsCloseException {
        var user2 = userDao.findById(userId).orElseThrow(InvalidUserIdException::new);
        var wallet2 = user2.getWallet();
        var trade = tradeDao.findById(tradeId).orElseThrow(TradeNotFoundException::new);
        if (!trade.isOpen()) {
            throw new OfferIsCloseException();
        }
        if (trade.getWallet().getId() == wallet2.getId()) {
            throw new TradeWithYourselfException();
        }
        if (trade.getQuantity().compareTo(quantity) < 0) {
            quantity = trade.getQuantity();
        }
        if (trade.getTransactionType().equals(TransactionType.BUY)) {
            processDealBuy(trade, wallet2, quantity);
        } else {
            processDealSell(trade, wallet2, quantity);
        }
        DealDto deal = DealDto.builder()
                .cryptoSymbol(cryptoSymbol)
                .transactionType(trade.getTransactionType())
                .quantity(quantity)
                .price(trade.getPrice())
                .value(quantity.multiply(trade.getPrice()))
                .build();
        saveTransaction(trade.getWallet().getUser(), user2, deal);
        return deal;

    }

    private void processDealBuy(Trade trade, Wallet wallet2, BigDecimal quantity) throws NotEnoughFoundsException, WalletCryptoNotFoundException {
        cryptoSymbol = trade.getCryptocurrency().getSymbol();
        var cashWallet2 = wallet2.getCashWallet();
        var walletCrypto1 = fetchWalletCrypto(trade.getWallet(), cryptoSymbol);
        var walletCrypto2 = fetchWalletCrypto(wallet2, cryptoSymbol);

        if (walletCrypto2.getQuantity().compareTo(quantity) < 0) {
            throw new NotEnoughFoundsException();
        }
        walletCrypto1.setQuantity(walletCrypto1.getQuantity().add(quantity));
        walletCrypto2.setQuantity(walletCrypto2.getQuantity().subtract(quantity));
        cashWallet2.setQuantity(cashWallet2.getQuantity().add(trade.getPrice().multiply(quantity)));

        updateTradeAfterDeal(trade, quantity);
    }


    public void processDealSell(Trade trade, Wallet wallet2, BigDecimal quantity) throws WalletCryptoNotFoundException, NotEnoughFoundsException {
        cryptoSymbol = trade.getCryptocurrency().getSymbol();
        var wallet1 = trade.getWallet();
        var walletCrypto2 = fetchWalletCrypto(wallet2, cryptoSymbol);
        var cashWallet1 = wallet1.getCashWallet();
        var cashWallet2 = wallet2.getCashWallet();
        var value = trade.getPrice().multiply(quantity);
        if (cashWallet2.getQuantity().compareTo(value) < 0) {
            throw new NotEnoughFoundsException();
        }
        cashWallet1.setQuantity(cashWallet1.getQuantity().add(value));
        cashWallet2.setQuantity(cashWallet2.getQuantity().subtract(value));
        walletCrypto2.setQuantity(walletCrypto2.getQuantity().add(quantity));

        updateTradeAfterDeal(trade, quantity);
    }

    private void updateTradeAfterDeal(Trade trade, BigDecimal quantity) {
        trade.setQuantity(trade.getQuantity().subtract(quantity));
        trade.setValue(trade.getQuantity().multiply(trade.getPrice()));
        if (trade.getQuantity().doubleValue() == 0.00) {
            trade.setOpen(false);
            trade.setCloseDate(LocalDateTime.now());
        }
        tradeDao.save(trade);
    }

    private WalletCrypto fetchWalletCrypto(Wallet wallet, String symbol) throws WalletCryptoNotFoundException {
        return wallet.getCryptosList().stream()
                .filter(wCrypto -> wCrypto.getCryptocurrency().getSymbol().equals(symbol))
                .findAny().orElseThrow(WalletCryptoNotFoundException::new);
    }

    private void saveTransaction(User user1, User user2, DealDto deal2) throws CloneNotSupportedException {
        DealDto deal1 = deal2.clone();
        if (deal2.getTransactionType().equals(TransactionType.BUY)) {
            deal2.setTransactionType(TransactionType.SELL);
        } else {
            deal2.setTransactionType(TransactionType.BUY);
        }
        historyService.saveTransaction(user1, deal1);
        historyService.saveTransaction(user2, deal2);
    }
}
