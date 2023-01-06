package com.sensei.mapper;

import com.sensei.dto.CryptoFlowHistoryDto;
import com.sensei.entity.CryptoFlowHistory;
import com.sensei.entity.OperationType;
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
class CryptoFlowHistoryMapperTestSuite {

    @Autowired
    private CryptoFlowHistoryMapper mapper;

    @Test
    void mapToCryptoFlowHistoryDto() {
        //Given
        User user = new User();
        CryptoFlowHistory cryptoFlowHistory = new CryptoFlowHistory();
        cryptoFlowHistory.setUser(user);
        cryptoFlowHistory.setType(OperationType.DEBIT);
        cryptoFlowHistory.setQuantity(BigDecimal.valueOf(100));
        cryptoFlowHistory.setSymbol("BTC");
        cryptoFlowHistory.setTime(LocalDateTime.now());
        cryptoFlowHistory.setAddressTo("Address");
        //when
        CryptoFlowHistoryDto resultDto = mapper.mapToCryptoFlowHistoryDto(cryptoFlowHistory);
        //Then
        assertEquals(resultDto.getId(), cryptoFlowHistory.getId());
        assertEquals(resultDto.getType(), OperationType.DEBIT);
        assertEquals(resultDto.getUserId(), user.getId());
        assertEquals(resultDto.getTime(), cryptoFlowHistory.getTime());
        assertEquals(resultDto.getSymbol(), "BTC");
        assertEquals(resultDto.getQuantity(), BigDecimal.valueOf(100));
        assertEquals(resultDto.getAddressTo(), "Address");
    }

    @Test
    void mapToCryptoFlowHistList() {
        //given
        User user = new User();
        CryptoFlowHistory cryptoFlowHistory = new CryptoFlowHistory();
        cryptoFlowHistory.setUser(user);
        cryptoFlowHistory.setType(OperationType.DEBIT);
        cryptoFlowHistory.setQuantity(BigDecimal.valueOf(100));
        cryptoFlowHistory.setSymbol("BTC");
        cryptoFlowHistory.setTime(LocalDateTime.now());
        cryptoFlowHistory.setAddressTo("Address");
        List<CryptoFlowHistory> history = Arrays.asList(cryptoFlowHistory);
        //when
        List<CryptoFlowHistoryDto> resultListDto = mapper.mapToCryptoFlowHistList(history);
        //then
        assertEquals(resultListDto.size(), 1);
        assertEquals(resultListDto.get(0).getId(), cryptoFlowHistory.getId());
        assertEquals(resultListDto.get(0).getType(), OperationType.DEBIT);
        assertEquals(resultListDto.get(0).getUserId(), user.getId());
        assertEquals(resultListDto.get(0).getTime(), cryptoFlowHistory.getTime());
        assertEquals(resultListDto.get(0).getSymbol(), "BTC");
        assertEquals(resultListDto.get(0).getQuantity(), BigDecimal.valueOf(100));
        assertEquals(resultListDto.get(0).getAddressTo(), "Address");
    }
}