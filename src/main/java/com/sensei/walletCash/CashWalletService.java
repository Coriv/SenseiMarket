package com.sensei.walletCash;

import com.sensei.exception.CashWalletNotFoundException;
import com.sensei.exception.NotEnoughFoundsException;
import com.sensei.externalService.NbpService;
import com.sensei.history.CashHistService;
import com.sensei.history.CashHistory;
import com.sensei.trade.OperationType;
import com.sensei.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CashWalletService {

    private final CashWalletDao cashWalletDao;
    private final NbpService nbpService;
    private final CashHistService cashHistService;

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
        CashHistory history = new CashHistory();
        history.setUser(user);
        history.setType(OperationType.DEBIT);
        history.setQuantityUSD(quantityUSD);
        history.setQuantityPLN(quantityPLN);
        history.setTime(LocalDateTime.now());
        history.setToAccount(accountNumber);
        cashHistService.save(history);
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
        CashHistory cashHistory = new CashHistory();
        cashHistory.setUser(user);
        cashHistory.setType(OperationType.CREDIT);
        cashHistory.setQuantityUSD(quantityUSD);
        cashHistory.setQuantityPLN(quantityPLN);
        cashHistory.setTime(LocalDateTime.now());
        cashHistService.save(cashHistory);
        log.info("New founds have benn added to User: " + user.getUsername() + ": " + quantityUSD + " USD");
    }
}
