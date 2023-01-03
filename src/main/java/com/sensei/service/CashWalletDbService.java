package com.sensei.service;

import com.sensei.entity.CashWallet;
import com.sensei.exception.CashWalletNotFoundException;
import com.sensei.exception.NotEnoughFoundsException;
import com.sensei.externalService.NbpService;
import com.sensei.repository.CashWalletDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class CashWalletDbService {

    private final CashWalletDao cashWalletDao;
    private final NbpService nbpService;

    public CashWallet getCashWallet(Long cashWalletId) throws CashWalletNotFoundException {
        return cashWalletDao.findById(cashWalletId).orElseThrow(CashWalletNotFoundException::new);
    }

    public String withdrawMoney(Long cashWalletId, BigDecimal accountNumber, BigDecimal quantityUSD) throws CashWalletNotFoundException, NotEnoughFoundsException {
        var cashWallet = cashWalletDao.findById(cashWalletId).orElseThrow(CashWalletNotFoundException::new);
        if(cashWallet.getQuantity().compareTo(quantityUSD) < 0) {
            throw new NotEnoughFoundsException();
        }
        cashWallet.getQuantity().subtract(quantityUSD);
        cashWalletDao.save(cashWallet);
        var quantityPLN = nbpService.exchangeUsdToPln(quantityUSD);
        return quantityPLN + " PLN has been sent to the account: " + accountNumber;
    }

    private void withdrawMoney(BigDecimal accountNumber, BigDecimal quantity) {
        log.info(quantity + " USD was recalculated to PLN and has been sent to account: " + accountNumber);
    }

    public String depositMoney(Long cashWalletId, BigDecimal quantityPLN) throws CashWalletNotFoundException {
        var cashWallet = cashWalletDao.findById(cashWalletId).orElseThrow(CashWalletNotFoundException::new);
        var quantityUSD = nbpService.exchangePlnToUsd(quantityPLN);
        cashWallet.getQuantity().add(quantityUSD);
        log.info("New founds have benn added to CashWallet number: " + cashWallet.getId() + ": " + quantityUSD + " USD");
        return quantityUSD + " USD has been credited to your wallet";
    }
}
