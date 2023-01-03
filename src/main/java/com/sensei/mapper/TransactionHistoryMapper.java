package com.sensei.mapper;

import com.sensei.dto.TransactionHistoryDto;
import com.sensei.entity.TransactionHistory;
import com.sensei.repository.TransactionHistoryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionHistoryMapper {
    public TransactionHistoryDto mapToTransactionDto(TransactionHistory transactionHistory) {
        return new TransactionHistoryDto(
                transactionHistory.getId(),
                transactionHistory.getTransactionType(),
                transactionHistory.getCryptocurrency(),
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

