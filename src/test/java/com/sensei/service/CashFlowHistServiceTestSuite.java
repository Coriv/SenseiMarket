package com.sensei.service;

import com.sensei.entity.CashFlowHistory;
import com.sensei.entity.TransactionType;
import com.sensei.entity.User;
import com.sensei.repository.CashFlowHistoryDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CashFlowHistServiceTestSuite {

    @InjectMocks
    private CashFlowHistService service;
    @Mock
    private CashFlowHistoryDao historyDao;

    @Test
    void getAllSuite() {
        //Given
        User user = new User();
        CashFlowHistory cashFlowHistory = new CashFlowHistory();
        cashFlowHistory.setUser(user);
        cashFlowHistory.setType(TransactionType.BUY);
        cashFlowHistory.setQuantityUSD(BigDecimal.valueOf(100));
        cashFlowHistory.setQuantityPLN(BigDecimal.valueOf(400));
        cashFlowHistory.setTime(LocalDateTime.now());
        List<CashFlowHistory> historyList = Arrays.asList(cashFlowHistory);
        when(historyDao.findAll()).thenReturn(historyList);
        //When
        var resultList = service.getAll();
        //Then
        assertEquals(resultList.size(), 1);
        assertEquals(resultList.get(0).getType(), TransactionType.BUY);
        assertEquals(resultList.get(0).getQuantityUSD(), BigDecimal.valueOf(100));
        assertEquals(resultList.get(0).getQuantityPLN(), BigDecimal.valueOf(400));
        assertEquals(resultList.get(0).getTime(), cashFlowHistory.getTime());
    }

    @Test
    void saveSuite() {
        User user = new User();
        CashFlowHistory cashFlowHistory = new CashFlowHistory();
        cashFlowHistory.setUser(user);
        cashFlowHistory.setType(TransactionType.BUY);
        cashFlowHistory.setQuantityUSD(BigDecimal.valueOf(100));
        cashFlowHistory.setQuantityPLN(BigDecimal.valueOf(400));
        cashFlowHistory.setTime(LocalDateTime.now());
        when(historyDao.save(any(CashFlowHistory.class))).thenReturn(cashFlowHistory);
        //When
        var result = service.save(cashFlowHistory);
        //Then
        assertTrue(result instanceof CashFlowHistory);
        assertEquals(result.getId(), cashFlowHistory.getId());
        assertEquals(result.getUser().getId(), user.getId());
        assertEquals(result.getType(), TransactionType.BUY);
        assertEquals(result.getQuantityUSD(), BigDecimal.valueOf(100));
        assertEquals(result.getQuantityPLN(), BigDecimal.valueOf(400));
        assertEquals(result.getTime(), cashFlowHistory.getTime());

    }
}