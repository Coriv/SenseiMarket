package com.sensei.service;

import com.sensei.entity.Trade;
import com.sensei.entity.User;
import com.sensei.entity.WalletCrypto;
import com.sensei.exception.InvalidUserIdException;
import com.sensei.exception.TradeNotFoundException;
import com.sensei.repository.TradeDao;
import com.sensei.repository.UserDao;
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

    public List<Trade> findOpenTrades(String symbol) {
        if (symbol == null) {
            return tradeDao.findAllByOpenIsTrue();
        }
        return tradeDao.findAllByOpenIsTrue().stream()
                .filter(trade -> trade.getCryptoPair().getSymbol().equals(symbol))
                .collect(Collectors.toList());
    }

    public List<Trade> findAllUserTrades(Long userId, boolean showClosed) throws InvalidUserIdException {
        User user = userDao.findById(userId).orElseThrow(InvalidUserIdException::new);
        if (showClosed == true) {
            return user.getWallet().getTrades();
        }
        return user.getWallet().getTrades().stream()
                .filter(trade -> trade.isOpen() == true)
                .collect(Collectors.toList());
    }

    public Trade createNewTrade(Trade trade) {


        trade.setOpenDate(LocalDateTime.now());
        return tradeDao.save(trade);
    }

    public void deleteTrade(Long tradeId) throws TradeNotFoundException {
        tradeDao.findById(tradeId).orElseThrow(TradeNotFoundException::new);
        tradeDao.deleteById(tradeId);
    }
}
