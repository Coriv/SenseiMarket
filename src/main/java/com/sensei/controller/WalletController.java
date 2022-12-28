package com.sensei.controller;

import com.sensei.dto.WalletCryptoDto;
import com.sensei.entity.WalletCrypto;
import com.sensei.exception.InvalidUserIdException;
import com.sensei.exception.UserNotVerifyException;
import com.sensei.exception.WalletAlreadyExistException;
import com.sensei.exception.WalletNotFoundException;
import com.sensei.mapper.WalletCryptoMapper;
import com.sensei.service.WalletDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletDbService walletDbService;
    private final WalletCryptoMapper walletCryptoMapper;
    private Long userId;

    @PostMapping
    public ResponseEntity<Void> createWallet(@RequestParam Long userId) throws InvalidUserIdException, WalletAlreadyExistException, UserNotVerifyException {
        this.userId = userId;
        walletDbService.createWallet(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<Map<String, BigDecimal>> fetchWalletContent(@PathVariable Long walletId) throws WalletNotFoundException {
        Map<String, BigDecimal> walletContent = walletDbService.getWalletContent(walletId);
        return ResponseEntity.ok(walletContent);
    }

    @GetMapping(value = "/{walletId}", params = {"symbols"})
    public ResponseEntity<List<WalletCryptoDto>> fetchCryptosBySymbol(@PathVariable Long walletId, @RequestParam String... symbols) throws WalletNotFoundException {
        List<WalletCrypto> cryptos = walletDbService.getCryptosBySymbol(walletId, symbols);
        return ResponseEntity.ok(walletCryptoMapper.mapToWalletCryptoListDto(cryptos));
    }

}
