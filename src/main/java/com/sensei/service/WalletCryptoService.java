package com.sensei.service;

import com.sensei.dto.WithdrawDto;
import com.sensei.entity.Cryptocurrency;
import com.sensei.entity.WalletCrypto;
import com.sensei.exception.NotEnoughFoundsException;
import com.sensei.exception.WalletCryptoNotFoundException;
import com.sensei.repository.WalletCryptoDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletCryptoService {

    private final WalletCryptoDao walletCryptoDao;

    public WalletCrypto findById(Long walletCryptoId) throws WalletCryptoNotFoundException {
        return walletCryptoDao.findById(walletCryptoId).orElseThrow(WalletCryptoNotFoundException::new);
    }

    public String withdrawCrypto(Long walletCryptoId, WithdrawDto withdrawDto) throws WalletCryptoNotFoundException, NotEnoughFoundsException {
        var walletCrypto = walletCryptoDao.findById(walletCryptoId).orElseThrow(WalletCryptoNotFoundException::new);
        var quantityUSD = withdrawDto.getQuantity();
        if(walletCrypto.getQuantity().compareTo(quantityUSD) < 0) {
            throw new NotEnoughFoundsException();
        }
        walletCrypto.setQuantity(walletCrypto.getQuantity().subtract(quantityUSD));
        processWithdraw(walletCrypto.getCryptocurrency(), withdrawDto.getAccountNumber(), quantityUSD);
        walletCryptoDao.save(walletCrypto);
        return quantityUSD + walletCrypto.getCryptocurrency().getSymbol() + " has been successfully sent on the account number: " + withdrawDto.getAccountNumber();
    }

    private void processWithdraw(Cryptocurrency crypto, String address, BigDecimal quantity) {
        log.info(quantity + " " + crypto.getSymbol() + " has been successfully sent on the address: " + address);
    }

    public String getWalletCryptoAddress(Long walletCryptoId) throws WalletCryptoNotFoundException {
        var walletCrypto = walletCryptoDao.findById(walletCryptoId).orElseThrow(WalletCryptoNotFoundException::new);
        var info = "To deposit " + walletCrypto.getCryptocurrency().getName() + " on the exchange, " +
                "transfer found to the provided address: \n" + walletCrypto.getAddress();
        return info;
    }
}
