package com.sensei.service;

import com.sensei.dto.WithdrawDto;
import com.sensei.entity.CashWallet;
import com.sensei.entity.Wallet;
import com.sensei.exception.CashWalletNotFoundException;
import com.sensei.exception.NotEnoughFoundsException;
import com.sensei.externalService.NbpService;
import com.sensei.repository.CashWalletDao;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class CashWalletDbServiceTestSuite {
    @InjectMocks
    private CashWalletDbService cashWalletDbService;
    @Mock
    private CashWalletDao cashWalletDao;
    @Mock
    private NbpService nbpService;

    @Test
    void getCashWalletTest() throws CashWalletNotFoundException {
        //Given
        Wallet wallet = new Wallet();
        CashWallet cashWallet = new CashWallet();
        cashWallet.setWallet(wallet);
        cashWallet.setQuantity(BigDecimal.valueOf(1000));
        when(cashWalletDao.findById(anyLong())).thenReturn(Optional.of(cashWallet));
        //When
        var resultCashWallet = cashWalletDbService.getCashWallet(5L);
        //Then
        assertEquals(resultCashWallet.getWallet().getId(), wallet.getId());
        assertEquals(resultCashWallet.getQuantity(), BigDecimal.valueOf(1000));
    }

    @Test
    void withdrawMoreMoneyThenIsInWalletTest() throws CashWalletNotFoundException, NotEnoughFoundsException {
        //Given
        WithdrawDto withdrawDto = new WithdrawDto();
        withdrawDto.setAccountNumber("account");
        withdrawDto.setQuantity(BigDecimal.valueOf(100));
        WithdrawDto wdDtoTooMuch = new WithdrawDto();
        wdDtoTooMuch.setAccountNumber("ACCOUNT");
        wdDtoTooMuch.setQuantity(BigDecimal.valueOf(10000));
        Wallet wallet = new Wallet();
        CashWallet cashWallet = new CashWallet();
        cashWallet.setWallet(wallet);
        cashWallet.setQuantity(BigDecimal.valueOf(100));
        when(cashWalletDao.findById(any())).thenReturn(Optional.of(cashWallet));
        when(nbpService.exchangeUsdToPln(any())).thenReturn(BigDecimal.valueOf(400));
        //When
        cashWalletDbService.withdrawMoney(1L, withdrawDto);
        //Then
        assertThrows(NotEnoughFoundsException.class, () -> cashWalletDbService.withdrawMoney(5L, wdDtoTooMuch));
        verify(nbpService, times(1)).exchangeUsdToPln(BigDecimal.valueOf(100));
    }

    @Test
    void depositMoneyTest() throws CashWalletNotFoundException, NotEnoughFoundsException {
        //Given
        Wallet wallet = new Wallet();
        CashWallet cashWallet = new CashWallet();
        cashWallet.setWallet(wallet);
        cashWallet.setQuantity(BigDecimal.valueOf(100));
        when(cashWalletDao.findById(anyLong())).thenReturn(Optional.of(cashWallet));
        when(nbpService.exchangePlnToUsd(any())).thenReturn(BigDecimal.valueOf(200));
        var quantity = BigDecimal.valueOf(100);
        //When
        cashWalletDbService.depositMoney(1L, quantity);
        //Then
        verify(cashWalletDao, times(1)).save(cashWallet);
    }
}