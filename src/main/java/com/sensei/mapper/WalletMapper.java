package com.sensei.mapper;

import com.sensei.dto.WalletDto;
import com.sensei.entity.CashWallet;
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
@RequiredArgsConstructor
public class WalletMapper {

    private final WalletDao walletDao;
    private final UserDao userDao;
    private final WalletCryptoDao walletCryptoDao;
    private final TradeDao tradeDao;
    private final CashWalletDao cashWalletDao;
    public Wallet mapToWallet(WalletDto walletDto) throws InvalidUserIdException {
        Wallet wallet;
        Long walletId = walletDto.getId();
        if (walletId != null) {
            wallet = walletDao.findById(walletDto.getId()).orElse(new Wallet());
            List<WalletCrypto> cryptos = walletCryptoDao.findAllByWalletId(walletId);
            wallet.setCryptosList(cryptos);
            List<Trade> trades = tradeDao.findAllByWalletId(walletId);
            wallet.setTrades(trades);
        } else {
            wallet = new Wallet();
        }
        wallet.setUser(userDao.findById(walletDto.getUserId()).orElseThrow(InvalidUserIdException::new));
        wallet.setActive(walletDto.isActive());
        return wallet;
    }

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
