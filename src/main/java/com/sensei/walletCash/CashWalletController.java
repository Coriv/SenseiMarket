package com.sensei.walletCash;

import com.sensei.exception.CashWalletNotFoundException;
import com.sensei.exception.NotEnoughFoundsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/wallet/cash")
public class CashWalletController {

    private final CashWalletService cashService;
    private final CashWalletMapper cashMapper;

    @GetMapping("/{cashWalletId}")
    public ResponseEntity<CashWalletDto> fetchCashWallet(@PathVariable Long cashWalletId) throws CashWalletNotFoundException {
        var cashWallet = cashService.getCashWallet(cashWalletId);
        return ResponseEntity.ok(cashMapper.mapToCashWalletDto(cashWallet));
    }

    @PutMapping("/withdraw/{cashWalletId}")
    public ResponseEntity<CashWalletDto> withdrawMoney(
            @PathVariable Long cashWalletId,
            @RequestBody WithdrawDto withDrawDto) throws CashWalletNotFoundException, NotEnoughFoundsException {
        var cashWallet = cashService.withdrawMoney(cashWalletId, withDrawDto);
        return ResponseEntity.ok(cashMapper.mapToCashWalletDto(cashWallet));
    }

    @PutMapping("/deposit/{cashWalletId}")
    public ResponseEntity<CashWalletDto> depositMoney(
            @PathVariable Long cashWalletId,
            @RequestParam BigDecimal quantity) throws CashWalletNotFoundException {
        var cashWallet = cashService.depositMoney(cashWalletId, quantity);
        return ResponseEntity.ok(cashMapper.mapToCashWalletDto(cashWallet));
    }
}
