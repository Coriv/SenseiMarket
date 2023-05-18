package com.sensei.service;

import com.sensei.history.CashHistService;
import com.sensei.history.CashHistory;
import com.sensei.trade.OperationType;
import com.sensei.user.User;
import com.sensei.history.CashHistoryDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CashHistServiceTestSuite {

    @InjectMocks
    private CashHistService service;
    @Mock
    private CashHistoryDao historyDao;

    @Test
    void getAllSuite() {
        //Given
        User user = new User();
        CashHistory cashHistory = new CashHistory();
        cashHistory.setUser(user);
        cashHistory.setType(OperationType.CREDIT);
        cashHistory.setQuantityUSD(BigDecimal.valueOf(100));
        cashHistory.setQuantityPLN(BigDecimal.valueOf(400));
        cashHistory.setTime(LocalDateTime.now());
        List<CashHistory> historyList = Arrays.asList(cashHistory);
        when(historyDao.findAllByUserId(user.getId())).thenReturn(historyList);
        //When
        var resultList = service.getAllByUserId(user.getId());
        //Then
        assertEquals(resultList.size(), 1);
        assertEquals(resultList.get(0).getType(), OperationType.CREDIT);
        assertEquals(resultList.get(0).getQuantityUSD(), BigDecimal.valueOf(100));
        assertEquals(resultList.get(0).getQuantityPLN(), BigDecimal.valueOf(400));
        assertEquals(resultList.get(0).getTime(), cashHistory.getTime());
    }

    @Test
    void saveSuite() {
        User user = new User();
        CashHistory cashHistory = new CashHistory();
        cashHistory.setUser(user);
        cashHistory.setType(OperationType.CREDIT);
        cashHistory.setQuantityUSD(BigDecimal.valueOf(100));
        cashHistory.setQuantityPLN(BigDecimal.valueOf(400));
        cashHistory.setTime(LocalDateTime.now());
        when(historyDao.save(any(CashHistory.class))).thenReturn(cashHistory);
        //When
        var result = service.save(cashHistory);
        //Then
        assertTrue(result instanceof CashHistory);
        assertEquals(result.getId(), cashHistory.getId());
        assertEquals(result.getUser().getId(), user.getId());
        assertEquals(result.getType(), OperationType.CREDIT);
        assertEquals(result.getQuantityUSD(), BigDecimal.valueOf(100));
        assertEquals(result.getQuantityPLN(), BigDecimal.valueOf(400));
        assertEquals(result.getTime(), cashHistory.getTime());

    }
}