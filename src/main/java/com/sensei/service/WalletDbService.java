package com.sensei.service;

import com.sensei.config.AdminConfig;
import com.sensei.entity.*;
import com.sensei.exception.InvalidUserIdException;
import com.sensei.exception.UserNotVerifyException;
import com.sensei.exception.WalletAlreadyExistException;
import com.sensei.exception.WalletNotFoundException;
import com.sensei.repository.CryptocurrencyDao;
import com.sensei.repository.UserDao;
import com.sensei.repository.WalletDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class WalletDbService {

    private final WalletDao walletDao;
    private final UserDao userDao;
    private final CryptocurrencyDao cryptocurrencyDao;
    private final AdminConfig adminConfig;

    public Wallet createWallet(Long userId) throws InvalidUserIdException, WalletAlreadyExistException, UserNotVerifyException {
        User User = userDao.findById(userId).orElseThrow(InvalidUserIdException::new);
        Wallet wallet;
        if (User.getWallet() != null) {
            throw new WalletAlreadyExistException();
        } else {
            wallet = new Wallet();
            wallet.setUser(User);
            User.setWallet(wallet);
            CashWallet cashWallet = new CashWallet();
            cashWallet.setWallet(wallet);
            wallet.setCashWallet(cashWallet);

            List<Cryptocurrency> cryptosList = cryptocurrencyDao.findAll();
            for (Cryptocurrency crypto : cryptosList) {
                WalletCrypto walletCrypto = new WalletCrypto();
                walletCrypto.setWallet(wallet);
                walletCrypto.setCryptocurrency(crypto);
                walletCrypto.setAddress(adminConfig.getAddress() + userId + wallet.getId() + walletCrypto.getId());
                wallet.getCryptosList().add(walletCrypto);
            }
            if (User.getPESEL() != null && User.getIdCard() != null) {
                throw new UserNotVerifyException();
            } else {
                wallet.setActive(true);
            }
            return walletDao.save(wallet);
        }
    }

    public List<WalletCrypto> getCryptosBySymbol(Long walletId, String[] symbols) throws WalletNotFoundException {
        Wallet wallet = walletDao.findById(walletId).orElseThrow(WalletNotFoundException::new);
        if (symbols[0].equalsIgnoreCase("ALL")) {
            return wallet.getCryptosList();
        } else {
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

    public Map<String, BigDecimal> getWalletContent(Long walletId) throws WalletNotFoundException {
        Wallet wallet = walletDao.findById(walletId).orElseThrow(WalletNotFoundException::new);
        Map<String, BigDecimal> walletContent = new LinkedHashMap<>();

        CashWallet cashWallet = wallet.getCashWallet();
        walletContent.put(cashWallet.getCurrency(), cashWallet.getQuantity());

        for (WalletCrypto crypto : wallet.getCryptosList()) {
            walletContent.put(crypto.getCryptocurrency().getSymbol(), crypto.getQuantity());
        }
        return walletContent;
    }
}
