package com.sensei.controller;

import com.sensei.dto.WalletCryptoDto;
import com.sensei.exception.NotEnoughFoundsException;
import com.sensei.exception.WalletCryptoNotFoundException;
import com.sensei.mapper.WalletCryptoMapper;
import com.sensei.service.WalletCryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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

    @GetMapping("/{walletCryptoId}/deposit")
    public ResponseEntity<String> depositCryptocurrency(@PathVariable Long walletCryptoId) throws WalletCryptoNotFoundException {
        var address = walletCryptoService.getWalletCryptoAddress(walletCryptoId);
        return ResponseEntity.ok(address);
    }

    @PutMapping("/{walletCryptoId}/withdraw")
    public ResponseEntity<String> withdrawCryptocurrency(
            @PathVariable Long walletCryptoId,
            @RequestParam BigDecimal quantity,
            @RequestParam String address) throws WalletCryptoNotFoundException, NotEnoughFoundsException {
        var info = walletCryptoService.withdrawCrypto(walletCryptoId, quantity, address);
        return ResponseEntity.ok(info);
    }
}

