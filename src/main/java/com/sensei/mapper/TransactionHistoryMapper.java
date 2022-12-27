package com.sensei.mapper;

import com.sensei.dto.TransactionHistoryDto;
import com.sensei.entity.TransactionHistory;
import com.sensei.entity.TransactionType;
import com.sensei.repository.TransactionHistoryDao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionHistoryMapper {

    private final TransactionHistoryDao transactionsDao;

    public TransactionHistoryDto mapToTransactionDto(TransactionHistory transactionHistory) {
        return new TransactionHistoryDto(
                transactionHistory.getId(),
                transactionHistory.getTransactionType(),
                transactionHistory.getCryptoPair(),
                transactionHistory.getQuantity(),
                transactionHistory.getPrice(),
                transactionHistory.getValue(),
                transactionHistory.getUser().getId(),
                transactionHistory.getTransactionTime());
    }

    public List<TransactionHistoryDto> mapToTransactionsListDto(List<TransactionHistory> transactions) {
        return transactions.stream()
                .map(this::mapToTransactionDto)
                .collect(Collectors.toList());
    }
}

