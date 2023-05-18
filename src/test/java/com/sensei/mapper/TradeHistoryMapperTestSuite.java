package com.sensei.mapper;

import com.sensei.history.TradeHistoryDto;
import com.sensei.history.TradeHistory;
import com.sensei.history.TransactionHistoryMapper;
import com.sensei.trade.TransactionType;
import com.sensei.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TradeHistoryMapperTestSuite {

    @Autowired
    private TransactionHistoryMapper transactionMapper;

    @Test
    void mapToTransactionDtoTest() {
        //Given
        User user = new User();
        TradeHistory transaction = new TradeHistory();
        transaction.setTransactionType(TransactionType.SELL);
        transaction.setCryptocurrency("BTC");
        transaction.setQuantity(BigDecimal.valueOf(100));
        transaction.setPrice(BigDecimal.valueOf(5));
        transaction.setValue(BigDecimal.valueOf(500));
        transaction.setUser(user);
        transaction.setTransactionTime(LocalDateTime.now());
        //When
        TradeHistoryDto transactionDto = transactionMapper.mapToTransactionDto(transaction);
        //Then
        assertEquals(transactionDto.getId(), transactionDto.getId());
        assertEquals(transactionDto.getTransactionType(), TransactionType.SELL);
        assertEquals(transactionDto.getCryptocurrency(), "BTC");
        assertEquals(transactionDto.getQuantity(), BigDecimal.valueOf(100));
        assertEquals(transactionDto.getPrice(), BigDecimal.valueOf(5));
        assertEquals(transactionDto.getValue(), BigDecimal.valueOf(500));
        assertEquals(transactionDto.getUserId(), user.getId());
        assertEquals(transactionDto.getTransactionTime(), transaction.getTransactionTime());
    }

    @Test
    void mapToTransactionsListDtoTest() {
        User user = new User();
        TradeHistory transaction = new TradeHistory();
        transaction.setTransactionType(TransactionType.SELL);
        transaction.setCryptocurrency("BTC");
        transaction.setQuantity(BigDecimal.valueOf(100));
        transaction.setPrice(BigDecimal.valueOf(5));
        transaction.setValue(BigDecimal.valueOf(500));
        transaction.setUser(user);
        transaction.setTransactionTime(LocalDateTime.now());
        List<TradeHistory> transactions = Arrays.asList(transaction);
        //When
        List<TradeHistoryDto> transactionsDto = transactionMapper.mapToTransactionsListDto(transactions);
        //Then
        System.out.println(transactionsDto);
        assertEquals(transactionsDto.size(), 1);
        assertEquals(transactionsDto.get(0).getId(), transaction.getId());
        assertEquals(transactionsDto.get(0).getTransactionType(), TransactionType.SELL);
        assertEquals(transactionsDto.get(0).getCryptocurrency(), "BTC");
        assertEquals(transactionsDto.get(0).getQuantity(), BigDecimal.valueOf(100));
        assertEquals(transactionsDto.get(0).getPrice(), BigDecimal.valueOf(5));
        assertEquals(transactionsDto.get(0).getValue(), BigDecimal.valueOf(500));
        assertEquals(transactionsDto.get(0).getUserId(), user.getId());
        assertEquals(transactionsDto.get(0).getTransactionTime(), transaction.getTransactionTime());
    }
}