package com.sensei.walletCash;

import com.sensei.walletCash.CashWalletDto;
import com.sensei.walletCash.CashWallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashWalletMapper {

    public CashWalletDto mapToCashWalletDto(CashWallet cashWallet) {
        return CashWalletDto.builder()
                .id(cashWallet.getId())
                .walletId(cashWallet.getWallet().getId())
                .currency(cashWallet.getCurrency())
                .quantity(cashWallet.getQuantity())
                .build();
    }
}
