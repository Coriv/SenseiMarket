package com.sensei.wallet;

import com.sensei.wallet.WalletDto;
import com.sensei.trade.Trade;
import com.sensei.wallet.Wallet;
import com.sensei.walletCrypto.WalletCrypto;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class WalletMapper {
    public WalletDto mapToWalletDto(Wallet wallet) {
        return WalletDto.builder()
                .id(wallet.getId())
                .userId(wallet.getUser().getId())
                .active(wallet.isActive())
                .walletsCryptoList(wallet.getCryptosList().stream()
                        .map(WalletCrypto::getId)
                        .collect(Collectors.toList()))
                .trades(wallet.getTrades().stream()
                        .map(Trade::getId)
                        .collect(Collectors.toList()))
                .cashWalletId(wallet.getCashWallet().getId())
                .build();
    }
}
