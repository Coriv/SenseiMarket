package com.sensei.wallet;

import com.sensei.config.AdminConfig;
import com.sensei.cryptocurrency.Cryptocurrency;
import com.sensei.exception.InvalidUserIdException;
import com.sensei.exception.UserNotVerifyException;
import com.sensei.exception.WalletAlreadyExistException;
import com.sensei.exception.WalletNotFoundException;
import com.sensei.cryptocurrency.CryptocurrencyDao;
import com.sensei.user.UserDao;
import com.sensei.wallet.WalletDao;
import com.sensei.user.User;
import com.sensei.wallet.Wallet;
import com.sensei.walletCash.CashWallet;
import com.sensei.walletCrypto.WalletCrypto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletDao walletDao;
    private final UserDao userDao;
    private final CryptocurrencyDao cryptocurrencyDao;
    private final AdminConfig adminConfig;

    public Wallet createWallet(Long userId) throws InvalidUserIdException, WalletAlreadyExistException, UserNotVerifyException {
        User user = userDao.findById(userId).orElseThrow(InvalidUserIdException::new);
        Wallet wallet;
        if (user.getWallet() != null) {
            throw new WalletAlreadyExistException();
        } else {
            wallet = new Wallet();
            wallet.setUser(user);
            user.setWallet(wallet);
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
            if (user.getPESEL() == null || user.getIdCard() == null) {
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

    public Wallet getWalletContent(Long walletId) throws WalletNotFoundException {
        return walletDao.findById(walletId).orElseThrow(WalletNotFoundException::new);
    }
}
