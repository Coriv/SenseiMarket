package com.sensei.controller;

import com.sensei.dto.WalletCryptoDto;
import com.sensei.dto.WithdrawDto;
import com.sensei.exception.NotEnoughFoundsException;
import com.sensei.exception.WalletCryptoNotFoundException;
import com.sensei.mapper.WalletCryptoMapper;
import com.sensei.service.WalletCryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/wallet/crypto")
public class WalletCryptoController {

    private final WalletCryptoService walletCryptoService;
    private final WalletCryptoMapper walletCryptoMapper;

    @GetMapping("/{walletCryptoId}")
    public ResponseEntity<WalletCryptoDto> fetchWalletCrypto(@PathVariable Long walletCryptoId) throws WalletCryptoNotFoundException {
        var walletCrypto = walletCryptoService.findById(walletCryptoId);
        return ResponseEntity.ok(walletCryptoMapper.mapToWalletCryptoDto(walletCrypto));
    }

    @PutMapping("/{walletCryptoId}/deposit")
    public ResponseEntity<String> depositCryptocurrency(@PathVariable Long walletCryptoId) throws WalletCryptoNotFoundException {
        var address = walletCryptoService.getWalletCryptoAddress(walletCryptoId);
        return ResponseEntity.ok(address);
    }

    @PutMapping("/{walletCryptoId}/withdraw")
    public ResponseEntity<WalletCryptoDto> withdrawCryptocurrency(
            @PathVariable Long walletCryptoId,
            @RequestBody WithdrawDto withDrawDto) throws WalletCryptoNotFoundException, NotEnoughFoundsException {
        var walletCrypto = walletCryptoService.withdrawCrypto(walletCryptoId, withDrawDto);
        return ResponseEntity.ok(walletCryptoMapper.mapToWalletCryptoDto(walletCrypto));
    }
}

