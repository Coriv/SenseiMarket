package com.sensei.service;

import com.sensei.entity.User;
import com.sensei.entity.Wallet;
import com.sensei.entity.WalletCrypto;
import com.sensei.exception.InvalidUserIdException;
import com.sensei.exception.WalletNotFoundException;
import com.sensei.repository.UserDao;
import com.sensei.repository.WalletCryptoDao;
import com.sensei.repository.WalletDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class WalletDbService {

    private final WalletDao walletDao;
    private final UserDao userDao;
    private final WalletCryptoDao walletCryptoDao;

    public Wallet createWallet(Long userId) throws InvalidUserIdException {
        User user = userDao.findById(userId).orElseThrow(InvalidUserIdException::new);
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        user.setWallet(wallet);
        if (user.getPESEL() != null && user.getIdCard() != null) {
            wallet.setActive(true);
        }
        return walletDao.save(wallet);
    }

    public List<WalletCrypto> getListOfCrypto(Long walletId) throws WalletNotFoundException {
        walletDao.findById(walletId).orElseThrow(WalletNotFoundException::new);
        return walletCryptoDao.findAllByWalletId(walletId);
    }

    public List<WalletCrypto> getCryptosBySymbol(Long walletId, String[] symbols) throws WalletNotFoundException {
        Wallet wallet = walletDao.findById(walletId).orElseThrow(WalletNotFoundException::new);
        List<WalletCrypto> filteredList = new ArrayList<>();
        for (String symbol : symbols) {
            List<WalletCrypto> filterResult = wallet.getCryptosList().stream()
                    .filter(n -> n.getCryptocurrency().getSymbol().contains(symbol))
                    .collect(Collectors.toList());
            filteredList.addAll(filterResult);
        }
        return filteredList;
    }
}
