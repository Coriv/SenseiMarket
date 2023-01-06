package com.sensei.service;

import com.sensei.dto.DealDto;
import com.sensei.entity.TradeHistory;
import com.sensei.entity.TransactionType;
import com.sensei.entity.User;
import com.sensei.repository.TransactionHistoryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionHistoryDbService {

    private final TransactionHistoryDao historyDao;

    public void saveTransaction(User user, DealDto dealDto) {
        TradeHistory transaction = new TradeHistory();
        transaction.setUser(user);
        transaction.setTransactionType(dealDto.getTransactionType());
        transaction.setCryptocurrency(dealDto.getCryptoSymbol());
        transaction.setQuantity(dealDto.getQuantity());
        transaction.setPrice(dealDto.getPrice());
        transaction.setValue(dealDto.getValue());
        transaction.setTransactionTime(LocalDateTime.now());
        historyDao.save(transaction);
    }

    public List<TradeHistory> findByUserIdOptionalParams(
            Long userId, String symbol, TransactionType type, BigDecimal maxValue, BigDecimal minValue) {
        List<TradeHistory> history = historyDao.findAllByUserId(userId);
        if (symbol != null) {
            history = history.stream()
                    .filter(transaction -> transaction.getCryptocurrency().equals(symbol))
                    .collect(Collectors.toList());
        }
        if (type != null) {
            history = history.stream()
                    .filter(transaction -> transaction.getTransactionTime().equals(type))
                    .collect(Collectors.toList());
        }
        if (maxValue != null) {
            history = history.stream()
                    .filter(transaction -> transaction.getValue().compareTo(maxValue) <= 0)
                    .collect(Collectors.toList());
        }
        if (minValue != null) {
            history = history.stream()
                    .filter(transaction -> transaction.getValue().compareTo(minValue) >= 0)
                    .collect(Collectors.toList());
        }
        return history;
    }
}
