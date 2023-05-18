package com.sensei.service;

import com.sensei.walletCash.WithdrawDto;
import com.sensei.history.CashHistService;
import com.sensei.walletCash.CashWallet;
import com.sensei.user.User;
import com.sensei.wallet.Wallet;
import com.sensei.exception.CashWalletNotFoundException;
import com.sensei.exception.NotEnoughFoundsException;
import com.sensei.externalService.NbpService;
import com.sensei.history.CashHistoryDao;
import com.sensei.walletCash.CashWalletDao;
import com.sensei.walletCash.CashWalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CashWalletServiceTestSuite {
    @Autowired
    private CashWalletService cashWalletService;
    @MockBean
    private CashWalletDao cashWalletDao;
    @MockBean
    private NbpService nbpService;
    @Autowired
    private CashHistService cashHistService;
    @MockBean
    private CashHistoryDao cashHistoryDao;

    @Test
    void getCashWalletTest() throws CashWalletNotFoundException {
        //Given
        Wallet wallet = new Wallet();;
        CashWallet cashWallet = new CashWallet();
        cashWallet.setWallet(wallet);
        cashWallet.setQuantity(BigDecimal.valueOf(1000));
        when(cashWalletDao.findById(anyLong())).thenReturn(Optional.of(cashWallet));
        //When
        var resultCashWallet = cashWalletService.getCashWallet(5L);
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
        User user = new User();
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        CashWallet cashWallet = new CashWallet();
        cashWallet.setWallet(wallet);
        cashWallet.setQuantity(BigDecimal.valueOf(100));
        when(cashWalletDao.findById(any())).thenReturn(Optional.of(cashWallet));
        when(nbpService.exchangeUsdToPln(any())).thenReturn(BigDecimal.valueOf(400));
        when(cashHistoryDao.save(any())).thenReturn(null);
        //When
        cashWalletService.withdrawMoney(1L, withdrawDto);
        //Then
        assertThrows(NotEnoughFoundsException.class, () -> cashWalletService.withdrawMoney(5L, wdDtoTooMuch));
        verify(nbpService, times(1)).exchangeUsdToPln(BigDecimal.valueOf(100));
    }

    @Test
    void depositMoneyTest() throws CashWalletNotFoundException {
        //Given
        User user = new User();
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        CashWallet cashWallet = new CashWallet();
        cashWallet.setWallet(wallet);
        cashWallet.setQuantity(BigDecimal.valueOf(100));
        when(cashWalletDao.findById(anyLong())).thenReturn(Optional.of(cashWallet));
        when(nbpService.exchangePlnToUsd(any())).thenReturn(BigDecimal.valueOf(200));
        when(cashHistoryDao.save(any())).thenReturn(null);
        var quantity = BigDecimal.valueOf(100);
        //When
        cashWalletService.depositMoney(1L, quantity);
        //Then
        verify(cashWalletDao, times(1)).save(cashWallet);
    }
}