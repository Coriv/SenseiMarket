package com.sensei.externalService;

import com.sensei.dto.CryptoPairDto;
import com.sensei.exception.EmptyCryptocurrencyDatabaseException;
import com.sensei.externalService.BinancePairsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BinancePairsServiceTestSuite {
    @Autowired
    private BinancePairsService binanceService;

    @Test
    void getForObject() throws EmptyCryptocurrencyDatabaseException {
        List<CryptoPairDto> pair = binanceService.getPairLastPrice();
        assertEquals(pair.get(0).getSymbol(), "BTCUSDT");
        System.out.println(pair);
    }
}