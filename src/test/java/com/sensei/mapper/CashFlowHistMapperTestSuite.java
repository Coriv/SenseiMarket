package com.sensei.mapper;

import com.sensei.entity.CashFlowHistory;
import com.sensei.entity.OperationType;
import com.sensei.entity.TransactionType;
import com.sensei.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CashFlowHistMapperTestSuite {

    @Autowired
    private CashFlowHistMapper mapper;

    @Test
    void mapToCashHistoryDtoTest() {
        //Given
        User user = new User();
        CashFlowHistory cashFlowHistory = new CashFlowHistory();
        cashFlowHistory.setUser(user);
        cashFlowHistory.setType(OperationType.CREDIT);
        cashFlowHistory.setQuantityUSD(BigDecimal.valueOf(100));
        cashFlowHistory.setQuantityPLN(BigDecimal.valueOf(400));
        cashFlowHistory.setToAccount("12345432452343");
        cashFlowHistory.setTime(LocalDateTime.now());
        //when
        var cashFlowDto = mapper.mapToCashHistoryDto(cashFlowHistory);
        //then
        assertEquals(cashFlowDto.getId(), cashFlowHistory.getId());
        assertEquals(cashFlowDto.getUserId(), user.getId());
        assertEquals(cashFlowDto.getType(), OperationType.CREDIT);
        assertEquals(cashFlowDto.getQuantityUSD(), BigDecimal.valueOf(100));
        assertEquals(cashFlowDto.getQuantityPLN(), BigDecimal.valueOf(400));
        assertEquals(cashFlowDto.getToAccount(), "12345432452343");
        assertEquals(cashFlowDto.getTime(), cashFlowHistory.getTime());
    }

    @Test
    void mapToCashHistoryListTest() {
        //Given
        User user = new User();
        CashFlowHistory cashFlowHistory = new CashFlowHistory();
        cashFlowHistory.setUser(user);
        cashFlowHistory.setType(OperationType.DEBIT);
        cashFlowHistory.setQuantityUSD(BigDecimal.valueOf(100));
        cashFlowHistory.setQuantityPLN(BigDecimal.valueOf(400));
        cashFlowHistory.setTime(LocalDateTime.now());
        List<CashFlowHistory> history  = Arrays.asList(cashFlowHistory);
        //When
        var historyDto = mapper.mapToCashHistoryDtoList(history);
        //then
        assertEquals(historyDto.size(), 1);
        assertEquals(historyDto.get(0).getQuantityUSD(), BigDecimal.valueOf(100));
        assertEquals(historyDto.get(0).getType(), OperationType.DEBIT);
        assertEquals(historyDto.get(0).getQuantityUSD(), BigDecimal.valueOf(100));
        assertEquals(historyDto.get(0).getQuantityPLN(), BigDecimal.valueOf(400));
    }
}