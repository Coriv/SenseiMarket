package com.sensei.service;

import com.sensei.dto.WithdrawDto;
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

    public CashWallet withdrawMoney(Long cashWalletId, WithdrawDto withDrawDto) throws CashWalletNotFoundException, NotEnoughFoundsException {
        var cashWallet = cashWalletDao.findById(cashWalletId).orElseThrow(CashWalletNotFoundException::new);
        if (cashWallet.getQuantity().compareTo(withDrawDto.getQuantity()) < 0) {
            throw new NotEnoughFoundsException();
        }
        cashWallet.setQuantity(cashWallet.getQuantity().subtract(withDrawDto.getQuantity()));
        proceedWithdrawMoney(withDrawDto.getAccountNumber(), withDrawDto.getQuantity());
        return cashWalletDao.save(cashWallet);
    }

    private void proceedWithdrawMoney(String accountNumber, BigDecimal quantityUSD) {
        var quantityPLN = nbpService.exchangeUsdToPln(quantityUSD);
        log.info(quantityUSD + " USD was recalculated to PLN: " + quantityPLN +
                " and has been sent to account: " + accountNumber);
    }

    public CashWallet depositMoney(Long cashWalletId, BigDecimal quantityPLN) throws CashWalletNotFoundException {
        var cashWallet = cashWalletDao.findById(cashWalletId).orElseThrow(CashWalletNotFoundException::new);
        var quantityUSD = nbpService.exchangePlnToUsd(quantityPLN);
        cashWallet.setQuantity(cashWallet.getQuantity().add(quantityUSD));
        log.info("New founds have benn added to CashWallet number: " + cashWallet.getId() + ": " + quantityUSD + " USD");
        return cashWalletDao.save(cashWallet);
    }
}
