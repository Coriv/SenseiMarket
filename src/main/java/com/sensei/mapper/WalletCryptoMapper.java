package com.sensei.mapper;

import com.sensei.dto.WalletCryptoDto;
import com.sensei.entity.WalletCrypto;
import com.sensei.exception.CryptocurrencyNotFoundException;
import com.sensei.exception.WalletNotFoundException;
import com.sensei.repository.CryptocurrencyDao;
import com.sensei.repository.WalletCryptoDao;
import com.sensei.repository.WalletDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletCryptoMapper {

    private final WalletCryptoDao walletCryptoDao;
    private final WalletDao walletDao;
    private final CryptocurrencyDao cryptocurrencyDao;

    public WalletCrypto mapToWalletCrypto(WalletCryptoDto walletCryptoDto) throws WalletNotFoundException, CryptocurrencyNotFoundException {
        WalletCrypto walletCrypto;
        if (walletCryptoDto.getId() != null) {
            walletCrypto = walletCryptoDao.findById(walletCryptoDto.getId()).orElse(new WalletCrypto());
        } else {
            walletCrypto = new WalletCrypto();
        }
        walletCrypto.setId(walletCryptoDto.getId());
        walletCrypto.setWallet(walletDao.findById(walletCryptoDto.getWalletId()).orElseThrow(WalletNotFoundException::new));
        walletCrypto.setCryptocurrency(cryptocurrencyDao.findBySymbol(walletCryptoDto.getCryptocurrencySymbol()).orElseThrow(CryptocurrencyNotFoundException::new));
        walletCrypto.setQuantity(walletCryptoDto.getQuantity());

        return walletCrypto;
    }

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
