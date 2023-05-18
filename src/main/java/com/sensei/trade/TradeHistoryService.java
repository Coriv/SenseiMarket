package com.sensei.trade;

import com.sensei.history.TradeHistory;
import com.sensei.user.User;
import com.sensei.history.TradeHistoryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeHistoryService {

    private final TradeHistoryDao historyDao;

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
