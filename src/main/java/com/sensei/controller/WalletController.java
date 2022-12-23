package com.sensei.controller;

import com.sensei.dto.WalletCryptoDto;
import com.sensei.entity.WalletCrypto;
import com.sensei.exception.InvalidUserIdException;
import com.sensei.exception.WalletNotFoundException;
import com.sensei.mapper.WalletCryptoMapper;
import com.sensei.service.WalletDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletDbService walletDbService;
    private final WalletCryptoMapper walletCryptoMapper;

    @PostMapping
    public ResponseEntity<Void> createWallet(@RequestParam Long userId) throws InvalidUserIdException {
        walletDbService.createWallet(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<List<WalletCryptoDto>> fetchAllCryptosInWallet(@PathVariable Long walletId) throws WalletNotFoundException {
        List<WalletCrypto> cryptos = walletDbService.getListOfCrypto(walletId);
        List<WalletCryptoDto> cryptosDto = walletCryptoMapper.mapToWalletCryptoListDto(cryptos);
        return ResponseEntity.ok(cryptosDto);
    }

    @GetMapping(value = "/{walletId}", params = {"symbols"})
    public ResponseEntity<List<WalletCryptoDto>> fetchCryptosBySymbol(@PathVariable Long walletId, @RequestParam String... symbols) throws WalletNotFoundException {
        List<WalletCrypto> cryptos = walletDbService.getCryptosBySymbol(walletId, symbols);
        return ResponseEntity.ok(walletCryptoMapper.mapToWalletCryptoListDto(cryptos));
    }


}
