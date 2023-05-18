package com.sensei.mapper;

import com.sensei.wallet.WalletMapper;
import com.sensei.walletCash.CashWallet;
import com.sensei.user.User;
import com.sensei.wallet.Wallet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WalletMapperTestSuite {
    @Autowired
    private WalletMapper walletMapper;

    @Test
    void mapToWalletDtoTest() {
        Wallet wallet = new Wallet();
        User user = new User();
        user.setId(55L);
        wallet.setUser(user);
        CashWallet cashWallet = new CashWallet();
        cashWallet.setId(90L);
        wallet.setActive(true);
        wallet.setCashWallet(cashWallet);
        //when
        var walletDto = walletMapper.mapToWalletDto(wallet);
        //then
        assertTrue(walletDto.isActive());
        assertEquals(walletDto.getUserId(), 55L);
        assertEquals(walletDto.getCashWalletId(), 90L);
        assertEquals(walletDto.getTrades().size(), 0);
        assertEquals(walletDto.getWalletsCryptoList().size(), 0);
    }
}