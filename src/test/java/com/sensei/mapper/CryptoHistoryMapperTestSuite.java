package com.sensei.mapper;

import com.sensei.dto.CryptoHistoryDto;
import com.sensei.entity.CryptoHistory;
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
class CryptoHistoryMapperTestSuite {

    @Autowired
    private CryptoHistMapper mapper;

    @Test
    void mapToCryptoFlowHistoryDto() {
        //Given
        User user = new User();
        CryptoHistory cryptoHistory = new CryptoHistory();
        cryptoHistory.setUser(user);
        cryptoHistory.setType(OperationType.DEBIT);
        cryptoHistory.setQuantity(BigDecimal.valueOf(100));
        cryptoHistory.setSymbol("BTC");
        cryptoHistory.setTime(LocalDateTime.now());
        cryptoHistory.setAddressTo("Address");
        //when
        CryptoHistoryDto resultDto = mapper.mapToCryptoFlowHistoryDto(cryptoHistory);
        //Then
        assertEquals(resultDto.getId(), cryptoHistory.getId());
        assertEquals(resultDto.getType(), OperationType.DEBIT);
        assertEquals(resultDto.getUserId(), user.getId());
        assertEquals(resultDto.getTime(), cryptoHistory.getTime());
        assertEquals(resultDto.getSymbol(), "BTC");
        assertEquals(resultDto.getQuantity(), BigDecimal.valueOf(100));
        assertEquals(resultDto.getAddressTo(), "Address");
    }

    @Test
    void mapToCryptoFlowHistList() {
        //given
        User user = new User();
        CryptoHistory cryptoHistory = new CryptoHistory();
        cryptoHistory.setUser(user);
        cryptoHistory.setType(OperationType.DEBIT);
        cryptoHistory.setQuantity(BigDecimal.valueOf(100));
        cryptoHistory.setSymbol("BTC");
        cryptoHistory.setTime(LocalDateTime.now());
        cryptoHistory.setAddressTo("Address");
        List<CryptoHistory> history = Arrays.asList(cryptoHistory);
        //when
        List<CryptoHistoryDto> resultListDto = mapper.mapToCryptoFlowHistList(history);
        //then
        assertEquals(resultListDto.size(), 1);
        assertEquals(resultListDto.get(0).getId(), cryptoHistory.getId());
        assertEquals(resultListDto.get(0).getType(), OperationType.DEBIT);
        assertEquals(resultListDto.get(0).getUserId(), user.getId());
        assertEquals(resultListDto.get(0).getTime(), cryptoHistory.getTime());
        assertEquals(resultListDto.get(0).getSymbol(), "BTC");
        assertEquals(resultListDto.get(0).getQuantity(), BigDecimal.valueOf(100));
        assertEquals(resultListDto.get(0).getAddressTo(), "Address");
    }
}