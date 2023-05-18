package com.sensei.history;

import com.sensei.history.TradeHistoryDto;
import com.sensei.history.TradeHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionHistoryMapper {
    public TradeHistoryDto mapToTransactionDto(TradeHistory tradeHistory) {
        return new TradeHistoryDto(
                tradeHistory.getId(),
                tradeHistory.getTransactionType(),
                tradeHistory.getCryptocurrency(),
                tradeHistory.getQuantity(),
                tradeHistory.getPrice(),
                tradeHistory.getValue(),
                tradeHistory.getUser().getId(),
                tradeHistory.getTransactionTime());
    }

    public List<TradeHistoryDto> mapToTransactionsListDto(List<TradeHistory> transactions) {
        return transactions.stream()
                .map(this::mapToTransactionDto)
                .collect(Collectors.toList());
    }
}

