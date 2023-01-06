package com.sensei.service;

import com.sensei.entity.CryptoFlowHistory;
import com.sensei.entity.OperationType;
import com.sensei.entity.User;
import com.sensei.repository.CryptoFlowHistoryDao;
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
class CryptoFlowHistServiceTestSuite {

    @InjectMocks
    private CryptoFlowHistService cryptoFlowService;
    @Mock
    private CryptoFlowHistoryDao cryptoFlowDao;

    @Test
    void getAllByUserIdTest() {
        //given
        User user = new User();
        CryptoFlowHistory cryptoFlowHistory = new CryptoFlowHistory();
        cryptoFlowHistory.setUser(user);
        cryptoFlowHistory.setType(OperationType.DEBIT);
        cryptoFlowHistory.setQuantity(BigDecimal.valueOf(100));
        cryptoFlowHistory.setSymbol("BTC");
        cryptoFlowHistory.setTime(LocalDateTime.now());
        cryptoFlowHistory.setAddressTo("Address");
        var cryptoFlowHistoryList = Arrays.asList(cryptoFlowHistory);
        when(cryptoFlowDao.findAllByUser(user.getId())).thenReturn(cryptoFlowHistoryList);
        //when
        var resultList = cryptoFlowService.getAllByUserId(user.getId());
        //then
        assertEquals(resultList.size(), 1);
        assertEquals(resultList.get(0).getId(), cryptoFlowHistory.getId());
        assertEquals(resultList.get(0).getType(), OperationType.DEBIT);
        assertEquals(resultList.get(0).getUser().getId(), user.getId());
        assertEquals(resultList.get(0).getTime(), cryptoFlowHistory.getTime());
        assertEquals(resultList.get(0).getSymbol(), "BTC");
        assertEquals(resultList.get(0).getQuantity(), BigDecimal.valueOf(100));
        assertEquals(resultList.get(0).getAddressTo(), "Address");
    }

    @Test
    void saveTest() {
        //given
        User user = new User();
        CryptoFlowHistory cryptoFlowHistory = new CryptoFlowHistory();
        cryptoFlowHistory.setUser(user);
        cryptoFlowHistory.setType(OperationType.DEBIT);
        cryptoFlowHistory.setQuantity(BigDecimal.valueOf(100));
        cryptoFlowHistory.setSymbol("BTC");
        cryptoFlowHistory.setTime(LocalDateTime.now());
        cryptoFlowHistory.setAddressTo("Address");
        when(cryptoFlowDao.save(any(CryptoFlowHistory.class))).thenReturn(cryptoFlowHistory);
        //when
        CryptoFlowHistory result = cryptoFlowService.save(cryptoFlowHistory);
        //then
        assertEquals(result.getId(), cryptoFlowHistory.getId());
        assertEquals(result.getType(), OperationType.DEBIT);
        assertEquals(result.getUser().getId(), user.getId());
        assertEquals(result.getTime(), cryptoFlowHistory.getTime());
        assertEquals(result.getSymbol(), "BTC");
        assertEquals(result.getQuantity(), BigDecimal.valueOf(100));
        assertEquals(result.getAddressTo(), "Address");
    }
}