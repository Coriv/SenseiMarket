package com.sensei.externalService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class NbpServiceTestSuite {
    @Autowired
    private NbpService nbpService;

    @Test
    void exchangePlnToUsdTest() {
        //Given
        BigDecimal valuePLN = BigDecimal.valueOf(1000);
        //When
        BigDecimal valueUSD = nbpService.exchangePlnToUsd(valuePLN);
        //Then
        assertTrue(valueUSD.compareTo(BigDecimal.valueOf(250)) < 0);
    }

    @Test
    void exchangeUsdToPlnTest() {
        //Given
        BigDecimal valuePLN = BigDecimal.valueOf(1000);
        //When
        BigDecimal valueUSD = nbpService.exchangeUsdToPln(valuePLN);
        //Then
        assertTrue(valueUSD.compareTo(BigDecimal.valueOf(4000)) > 0);
    }
}