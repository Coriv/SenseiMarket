package com.sensei.mapper;

import com.sensei.domain.WalletCryptoDto;
import com.sensei.entity.WalletCrypto;
import com.sensei.exception.WalletCryptoDoesNotBelowToAnyWallet;
import com.sensei.exception.WalletCryptoDoestNotHaveCorrectCrypto;
import com.sensei.repository.CryptocurrencyDao;
import com.sensei.repository.WalletCryptoDao;
import com.sensei.repository.WalletDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletCryptoMapper {

    private final WalletCryptoDao walletCryptoDao;
    private final WalletDao walletDao;
    private final CryptocurrencyDao cryptocurrencyDao;

    public WalletCrypto mapToWalletCrypto(WalletCryptoDto walletCryptoDto) throws WalletCryptoDoesNotBelowToAnyWallet, WalletCryptoDoestNotHaveCorrectCrypto {
        WalletCrypto walletCrypto;
        if (walletCryptoDto.getId() != null) {
            walletCrypto = walletCryptoDao.findById(walletCryptoDto.getId()).orElse(new WalletCrypto());
        } else {
            walletCrypto = new WalletCrypto();
        }
        walletCrypto.setId(walletCryptoDto.getId());
        walletCrypto.setWallet(walletDao.findById(walletCryptoDto.getWalletId()).orElseThrow(WalletCryptoDoesNotBelowToAnyWallet::new));
        walletCrypto.setCryptocurrency(cryptocurrencyDao.findById(walletCryptoDto.getCryptocurrencySymbol()).orElseThrow(WalletCryptoDoestNotHaveCorrectCrypto::new));
        walletCrypto.setQuantity(walletCryptoDto.getQuantity());

        return walletCrypto;
    }

    public WalletCryptoDto mapToWalletCryptoDto(WalletCrypto walletCrypto) {
        return WalletCryptoDto.builder()
                .id(walletCrypto.getId())
                .walletId(walletCrypto.getId())
                .cryptocurrencySymbol(walletCrypto.getCryptocurrency().getSymbol())
                .quantity(walletCrypto.getQuantity())
                .build();
    }
}
