package com.sensei.mapper;

import com.sensei.dto.CashWalletDto;
import com.sensei.entity.CashWallet;
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
