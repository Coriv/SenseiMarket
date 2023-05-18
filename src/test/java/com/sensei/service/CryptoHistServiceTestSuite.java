package com.sensei.service;

import com.sensei.history.CryptoHistService;
import com.sensei.history.CryptoHistory;
import com.sensei.trade.OperationType;
import com.sensei.user.User;
import com.sensei.history.CryptoHistoryDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CryptoHistServiceTestSuite {

    @InjectMocks
    private CryptoHistService cryptoFlowService;
    @Mock
    private CryptoHistoryDao cryptoFlowDao;

    @Test
    void getAllByUserIdTest() {
        //given
        User user = new User();
        CryptoHistory cryptoHistory = new CryptoHistory();
        cryptoHistory.setUser(user);
        cryptoHistory.setType(OperationType.DEBIT);
        cryptoHistory.setQuantity(BigDecimal.valueOf(100));
        cryptoHistory.setSymbol("BTC");
        cryptoHistory.setTime(LocalDateTime.now());
        cryptoHistory.setAddressTo("Address");
        var cryptoFlowHistoryList = Arrays.asList(cryptoHistory);
        when(cryptoFlowDao.findAllByUserId(user.getId())).thenReturn(cryptoFlowHistoryList);
        //when
        var resultList = cryptoFlowService.getAllByUserId(user.getId());
        //then
        assertEquals(resultList.size(), 1);
        assertEquals(resultList.get(0).getId(), cryptoHistory.getId());
        assertEquals(resultList.get(0).getType(), OperationType.DEBIT);
        assertEquals(resultList.get(0).getUser().getId(), user.getId());
        assertEquals(resultList.get(0).getTime(), cryptoHistory.getTime());
        assertEquals(resultList.get(0).getSymbol(), "BTC");
        assertEquals(resultList.get(0).getQuantity(), BigDecimal.valueOf(100));
        assertEquals(resultList.get(0).getAddressTo(), "Address");
    }

    @Test
    void saveTest() {
        //given
        User user = new User();
        CryptoHistory cryptoHistory = new CryptoHistory();
        cryptoHistory.setUser(user);
        cryptoHistory.setType(OperationType.DEBIT);
        cryptoHistory.setQuantity(BigDecimal.valueOf(100));
        cryptoHistory.setSymbol("BTC");
        cryptoHistory.setTime(LocalDateTime.now());
        cryptoHistory.setAddressTo("Address");
        when(cryptoFlowDao.save(any(CryptoHistory.class))).thenReturn(cryptoHistory);
        //when
        CryptoHistory result = cryptoFlowService.save(cryptoHistory);
        //then
        assertEquals(result.getId(), cryptoHistory.getId());
        assertEquals(result.getType(), OperationType.DEBIT);
        assertEquals(result.getUser().getId(), user.getId());
        assertEquals(result.getTime(), cryptoHistory.getTime());
        assertEquals(result.getSymbol(), "BTC");
        assertEquals(result.getQuantity(), BigDecimal.valueOf(100));
        assertEquals(result.getAddressTo(), "Address");
    }
}