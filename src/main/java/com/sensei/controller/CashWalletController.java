package com.sensei.controller;

import com.sensei.dto.CashWalletDto;
import com.sensei.exception.CashWalletNotFoundException;
import com.sensei.exception.NotEnoughFoundsException;
import com.sensei.mapper.CashWalletMapper;
import com.sensei.service.CashWalletDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/wallet/cash")
public class CashWalletController {

    private final CashWalletDbService cashService;
    private final CashWalletMapper cashMapper;

    @GetMapping("/{cashWalletId}")
    public ResponseEntity<CashWalletDto> fetchCashWallet(@PathVariable Long cashWalletId) throws CashWalletNotFoundException {
        var cashWallet = cashService.getCashWallet(cashWalletId);
        return ResponseEntity.ok(cashMapper.mapToCashWalletDto(cashWallet));
    }

    @PutMapping("/{cashWalletId}/withdraw")
    public ResponseEntity<String> withdrawMoney(
            @PathVariable Long cashWalletId,
            @RequestParam BigDecimal accountNumber,
            @RequestParam BigDecimal quantity) throws CashWalletNotFoundException, NotEnoughFoundsException {
        var info = cashService.withdrawMoney(cashWalletId, accountNumber, quantity);
        return ResponseEntity.ok(info);
    }

    @PutMapping("/{cashWalletId}/deposit")
    public ResponseEntity<String> depositMoney(
            @PathVariable Long cashWalletId,
            @RequestParam BigDecimal quantity) throws CashWalletNotFoundException {
        var info = cashService.depositMoney(cashWalletId, quantity);
        return ResponseEntity.ok(info);
    }
}
