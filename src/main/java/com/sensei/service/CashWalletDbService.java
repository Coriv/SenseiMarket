package com.sensei.service;

import com.sensei.dto.WithdrawDto;
import com.sensei.entity.*;
import com.sensei.exception.CashWalletNotFoundException;
import com.sensei.exception.NotEnoughFoundsException;
import com.sensei.externalService.NbpService;
import com.sensei.repository.CashWalletDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CashWalletDbService {

    private final CashWalletDao cashWalletDao;
    private final NbpService nbpService;
    private final CashFlowHistService cashFlowHistService;

    public CashWallet getCashWallet(Long cashWalletId) throws CashWalletNotFoundException {
        return cashWalletDao.findById(cashWalletId).orElseThrow(CashWalletNotFoundException::new);
    }

    public CashWallet withdrawMoney(Long cashWalletId, WithdrawDto withDrawDto) throws CashWalletNotFoundException, NotEnoughFoundsException {
        var cashWallet = cashWalletDao.findById(cashWalletId).orElseThrow(CashWalletNotFoundException::new);
        var user = cashWallet.getWallet().getUser();
        if (cashWallet.getQuantity().compareTo(withDrawDto.getQuantity()) < 0) {
            log.error("Not enough funds to process withdraw for user: " + user.getUsername() +
                    ". Actual account balance: " + cashWallet.getQuantity());
            throw new NotEnoughFoundsException();
        }
        cashWallet.setQuantity(cashWallet.getQuantity().subtract(withDrawDto.getQuantity()));
        saveWithdrawToHistory(user, withDrawDto.getAccountNumber(), withDrawDto.getQuantity());
        return cashWalletDao.save(cashWallet);
    }

    private void saveWithdrawToHistory(User user, String accountNumber, BigDecimal quantityUSD) {
        var quantityPLN = nbpService.exchangeUsdToPln(quantityUSD);
        CashFlowHistory history = new CashFlowHistory();
        history.setUser(user);
        history.setType(OperationType.DEBIT);
        history.setQuantityUSD(quantityUSD);
        history.setQuantityPLN(quantityPLN);
        history.setTime(LocalDateTime.now());
        history.setToAccount(accountNumber);
        cashFlowHistService.save(history);
        log.info(quantityUSD + " USD was recalculated to PLN: " + quantityPLN +
                " and has been sent to account: " + accountNumber);
    }

    public CashWallet depositMoney(Long cashWalletId, BigDecimal quantityPLN) throws CashWalletNotFoundException {
        var cashWallet = cashWalletDao.findById(cashWalletId).orElseThrow(CashWalletNotFoundException::new);
        var user = cashWallet.getWallet().getUser();
        var quantityUSD = nbpService.exchangePlnToUsd(quantityPLN);
        cashWallet.setQuantity(cashWallet.getQuantity().add(quantityUSD));
        saveDepositToHistory(user, quantityUSD, quantityPLN);
        return cashWalletDao.save(cashWallet);
    }

    private void saveDepositToHistory(User user, BigDecimal quantityUSD, BigDecimal quantityPLN) {
        CashFlowHistory cashFlowHistory = new CashFlowHistory();
        cashFlowHistory.setUser(user);
        cashFlowHistory.setType(OperationType.CREDIT);
        cashFlowHistory.setQuantityUSD(quantityUSD);
        cashFlowHistory.setQuantityPLN(quantityPLN);
        cashFlowHistory.setTime(LocalDateTime.now());
        cashFlowHistService.save(cashFlowHistory);
        log.info("New founds have benn added to User: " + user.getUsername() + ": " + quantityUSD + " USD");
    }
}
