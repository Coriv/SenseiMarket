package com.sensei.service;

import com.sensei.entity.Trade;
import com.sensei.entity.TransactionType;
import com.sensei.entity.Wallet;
import com.sensei.entity.WalletCrypto;
import com.sensei.exception.InvalidUserIdException;
import com.sensei.exception.NotEnoughFoundsException;
import com.sensei.exception.TradeNotFoundException;
import com.sensei.exception.UnknownErrorException;
import com.sensei.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeDbService {

    private final TradeDao tradeDao;
    private final UserDao userDao;
    private final WalletDao walletDao;

    public List<Trade> findOpenTrades(String symbol) {
        if (symbol == null) {
            return tradeDao.findAllByOpenIsTrue();
        }
        return tradeDao.findAllByOpenIsTrue().stream()
                .filter(trade -> trade.getCryptocurrency().getSymbol().equals(symbol))
                .collect(Collectors.toList());
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

    public Trade createNewTradeSell(Trade trade) throws UnknownErrorException, NotEnoughFoundsException {
        var wallet = trade.getWallet();
        var cryptos = wallet.getCryptosList().stream()
                .filter(crypto -> crypto.getCryptocurrency().getSymbol().equals(trade.getCryptocurrency().getSymbol()))
                .collect(Collectors.toList());
        if (cryptos.size() != 1) {
            throw new UnknownErrorException();
        }
        WalletCrypto crypto = cryptos.get(0);
        var quantity = crypto.getQuantity();
        if (quantity.compareTo(trade.getQuantity()) < 0) {
            throw new NotEnoughFoundsException();
        } else {
            crypto.setQuantity(quantity.subtract(trade.getQuantity()));
        }
        trade.setOpenDate(LocalDateTime.now());
        return tradeDao.save(trade);
    }

    public void deleteTrade(Long tradeId) throws TradeNotFoundException, UnknownErrorException {
        var trade = tradeDao.findById(tradeId).orElseThrow(TradeNotFoundException::new);
        var wallet = trade.getWallet();
        if (trade.getTransactionType().equals(TransactionType.BUY)) {
            var quantity= wallet.getCashWallet().getQuantity();
            wallet.getCashWallet().setQuantity(quantity.add(trade.getQuantity()));
        } else {
            List<WalletCrypto> cryptos = wallet.getCryptosList().stream()
                    .filter(crypto -> crypto.getCryptocurrency().getSymbol().equals(trade.getCryptocurrency().getSymbol()))
                    .collect(Collectors.toList());
            if (cryptos.size() != 1) {
                throw new UnknownErrorException();
            }
            var crypto = cryptos.get(0);
            var quantity = crypto.getQuantity();
            crypto.setQuantity(quantity.add(trade.getQuantity()));
        }
        walletDao.save(wallet);
        tradeDao.deleteById(tradeId);
    }
}
