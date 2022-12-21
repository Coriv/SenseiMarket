package com.sensei.mapper;

import com.sensei.domain.WalletDto;
import com.sensei.entity.Wallet;
import com.sensei.entity.WalletCrypto;
import com.sensei.exception.WalletWithoutUserException;
import com.sensei.repository.UserDao;
import com.sensei.repository.WalletCryptoDao;
import com.sensei.repository.WalletDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletMapper {

    private final WalletDao walletDao;
    private final UserDao userDao;
    private final WalletCryptoDao walletCryptoDao;

    public Wallet mapToWallet(WalletDto walletDto) throws WalletWithoutUserException {
        Wallet wallet;
        if (walletDto.getId() != null) {
            wallet = walletDao.findById(walletDto.getId()).orElse(new Wallet());
        } else {
            wallet = new Wallet();
        }
        wallet.setUser(userDao.findById(walletDto.getUserId()).orElseThrow(WalletWithoutUserException::new));
        wallet.setActive(walletDto.isActive());
        //List<WalletCrypto> walletCryptoList = walletCryptoDao.findAllById(walletDto.getWalletsCryptoList());
        //wallet.setCryptosList(walletCryptoList);
        return wallet;
    }

    public WalletDto mapToWalletDto(Wallet wallet) {
        return WalletDto.builder()
                .id(wallet.getId())
                .userId(wallet.getUser().getId())
                .isActive(wallet.isActive())
                .walletsCryptoList(wallet.getCryptosList().stream()
                        .map(WalletCrypto::getId)
                        .collect(Collectors.toList()))
                .build();
    }
}
