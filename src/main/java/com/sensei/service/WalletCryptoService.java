package com.sensei.service;

import com.sensei.dto.WithdrawDto;
import com.sensei.entity.*;
import com.sensei.exception.NotEnoughFoundsException;
import com.sensei.exception.WalletCryptoNotFoundException;
import com.sensei.repository.WalletCryptoDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletCryptoService {

    private final WalletCryptoDao walletCryptoDao;
    private final CryptoHistService cryptoHistService;

    public WalletCrypto findById(Long walletCryptoId) throws WalletCryptoNotFoundException {
        return walletCryptoDao.findById(walletCryptoId).orElseThrow(WalletCryptoNotFoundException::new);
    }

    public WalletCrypto withdrawCrypto(Long walletCryptoId, WithdrawDto withdrawDto) throws WalletCryptoNotFoundException, NotEnoughFoundsException {
        var walletCrypto = walletCryptoDao.findById(walletCryptoId).orElseThrow(WalletCryptoNotFoundException::new);
        var quantityUSD = withdrawDto.getQuantity();
        var user = walletCrypto.getWallet().getUser();
        if(walletCrypto.getQuantity().compareTo(quantityUSD) < 0) {
            log.error("Not enough founds to process withdraw for user: " + user.getUsername()
            + ". Actual balance: " + walletCrypto.getQuantity());
            throw new NotEnoughFoundsException();
        }
        walletCrypto.setQuantity(walletCrypto.getQuantity().subtract(quantityUSD));
        processWithdraw(user, walletCrypto.getCryptocurrency(), withdrawDto);
        return walletCryptoDao.save(walletCrypto);
    }

    private void processWithdraw(User user, Cryptocurrency crypto, WithdrawDto withdrawDto) {
        CryptoHistory withdraw = new CryptoHistory();
        withdraw.setUser(user);
        withdraw.setType(OperationType.DEBIT);
        withdraw.setSymbol(crypto.getSymbol());
        withdraw.setQuantity(withdrawDto.getQuantity());
        withdraw.setAddressTo(withdrawDto.getAccountNumber());
        withdraw.setTime(LocalDateTime.now());
        cryptoHistService.save(withdraw);
        log.info(withdrawDto.getQuantity() + " " + crypto.getSymbol() + " has been successfully sent on the address: " + withdrawDto.getAccountNumber());
    }

    public String getWalletCryptoAddress(Long walletCryptoId) throws WalletCryptoNotFoundException {
        var walletCrypto = walletCryptoDao.findById(walletCryptoId).orElseThrow(WalletCryptoNotFoundException::new);
        var info = "To deposit " + walletCrypto.getCryptocurrency().getName() + " on the exchange, " +
                "transfer found to the provided address: \n" + walletCrypto.getAddress();
        return info;
    }
}
