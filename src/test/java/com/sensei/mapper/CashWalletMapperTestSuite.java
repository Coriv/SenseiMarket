package com.sensei.mapper;

import com.sensei.walletCash.CashWalletDto;
import com.sensei.walletCash.CashWallet;
import com.sensei.wallet.Wallet;
import com.sensei.walletCash.CashWalletMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CashWalletMapperTestSuite {

    @Autowired
    private CashWalletMapper cashWalletMapper;

    @Test
    void mapToCashWalletDtoTest() {
        //Given
        Wallet wallet = new Wallet();
        CashWallet cashWallet = new CashWallet();
        cashWallet.setWallet(wallet);
        cashWallet.setQuantity(BigDecimal.valueOf(1000));
        //when
        CashWalletDto cashWalletDto = cashWalletMapper.mapToCashWalletDto(cashWallet);
        //then
        assertEquals(cashWalletDto.getWalletId(), wallet.getId());
        assertEquals(cashWalletDto.getId(), cashWallet.getId());
        assertEquals(cashWalletDto.getQuantity(), BigDecimal.valueOf(1000));
        assertEquals(cashWalletDto.getCurrency(), cashWallet.getCurrency());
    }
}