package com.sensei.service;

import com.sensei.entity.Wallet;
import com.sensei.exception.WalletNotFoundException;
import com.sensei.repository.WalletDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletDbService {

    private final WalletDao walletDao;

    public Wallet createWallet(Wallet wallet) {
        return walletDao.save(wallet);
    }
    public Wallet findWalletById(Long id) throws WalletNotFoundException {
        return walletDao.findById(id).orElseThrow(WalletNotFoundException::new);
    }
}
