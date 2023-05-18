package com.sensei.walletCrypto;

import com.sensei.walletCrypto.WalletCryptoDto;
import com.sensei.walletCrypto.WalletCrypto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletCryptoMapper {
    public WalletCryptoDto mapToWalletCryptoDto(WalletCrypto walletCrypto) {
        return WalletCryptoDto.builder()
                .id(walletCrypto.getId())
                .walletId(walletCrypto.getWallet().getId())
                .cryptocurrencySymbol(walletCrypto.getCryptocurrency().getSymbol())
                .quantity(walletCrypto.getQuantity())
                .build();
    }

    public List<WalletCryptoDto> mapToWalletCryptoListDto(List<WalletCrypto> cryptos) {
        return cryptos.stream()
                .map(this::mapToWalletCryptoDto)
                .collect(Collectors.toList());
    }
}
