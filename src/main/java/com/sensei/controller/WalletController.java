package com.sensei.controller;

import com.sensei.dto.WalletCryptoDto;
import com.sensei.dto.WalletDto;
import com.sensei.entity.WalletCrypto;
import com.sensei.exception.InvalidUserIdException;
import com.sensei.exception.UserNotVerifyException;
import com.sensei.exception.WalletAlreadyExistException;
import com.sensei.exception.WalletNotFoundException;
import com.sensei.mapper.WalletCryptoMapper;
import com.sensei.mapper.WalletMapper;
import com.sensei.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final WalletCryptoMapper walletCryptoMapper;
    private final WalletMapper walletMapper;

    @PostMapping
    public ResponseEntity<Void> createWallet(@RequestParam Long userId) throws InvalidUserIdException, WalletAlreadyExistException, UserNotVerifyException {
        walletService.createWallet(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<WalletDto> fetchWalletContent(@PathVariable Long walletId) throws WalletNotFoundException {
        var wallet = walletService.getWalletContent(walletId);
        return ResponseEntity.ok(walletMapper.mapToWalletDto(wallet));
    }


    @GetMapping(value = "/{walletId}", params = {"symbols"})
    public ResponseEntity<List<WalletCryptoDto>> fetchCryptosOptionalBySymbol
            (@PathVariable Long walletId,
             @RequestParam (required = false) String... symbols) throws WalletNotFoundException {
        List<WalletCrypto> cryptos = walletService.getCryptosBySymbol(walletId, symbols);
        return ResponseEntity.ok(walletCryptoMapper.mapToWalletCryptoListDto(cryptos));
    }
}
