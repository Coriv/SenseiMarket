package com.sensei.mapper;

import com.sensei.dto.WalletDto;
import com.sensei.entity.Trade;
import com.sensei.entity.Wallet;
import com.sensei.entity.WalletCrypto;
import com.sensei.exception.InvalidUserIdException;
import com.sensei.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
