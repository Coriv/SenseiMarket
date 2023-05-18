package com.sensei.mapper;

import com.sensei.history.CashHistMapper;
import com.sensei.history.CashHistory;
import com.sensei.trade.OperationType;
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
class CashHistMapperTestSuite {

    @Autowired
    private CashHistMapper mapper;

    @Test
    void mapToCashHistoryDtoTest() {
        //Given
        User user = new User();
        CashHistory cashHistory = new CashHistory();
        cashHistory.setUser(user);
        cashHistory.setType(OperationType.CREDIT);
        cashHistory.setQuantityUSD(BigDecimal.valueOf(100));
        cashHistory.setQuantityPLN(BigDecimal.valueOf(400));
        cashHistory.setToAccount("12345432452343");
        cashHistory.setTime(LocalDateTime.now());
        //when
        var cashFlowDto = mapper.mapToCashHistoryDto(cashHistory);
        //then
        assertEquals(cashFlowDto.getId(), cashHistory.getId());
        assertEquals(cashFlowDto.getUserId(), user.getId());
        assertEquals(cashFlowDto.getType(), OperationType.CREDIT);
        assertEquals(cashFlowDto.getQuantityUSD(), BigDecimal.valueOf(100));
        assertEquals(cashFlowDto.getQuantityPLN(), BigDecimal.valueOf(400));
        assertEquals(cashFlowDto.getToAccount(), "12345432452343");
        assertEquals(cashFlowDto.getTime(), cashHistory.getTime());
    }

    @Test
    void mapToCashHistoryListTest() {
        //Given
        User user = new User();
        CashHistory cashHistory = new CashHistory();
        cashHistory.setUser(user);
        cashHistory.setType(OperationType.DEBIT);
        cashHistory.setQuantityUSD(BigDecimal.valueOf(100));
        cashHistory.setQuantityPLN(BigDecimal.valueOf(400));
        cashHistory.setTime(LocalDateTime.now());
        List<CashHistory> history  = Arrays.asList(cashHistory);
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