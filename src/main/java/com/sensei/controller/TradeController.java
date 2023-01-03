package com.sensei.controller;

import com.sensei.dto.DealDto;
import com.sensei.dto.TradeDto;
import com.sensei.entity.Trade;
import com.sensei.entity.TransactionType;
import com.sensei.exception.*;
import com.sensei.mapper.TradeMapper;
import com.sensei.service.TradeDbService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/v1/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeDbService tradeDbService;
    private final TradeMapper tradeMapper;

    @GetMapping
    public ResponseEntity<List<TradeDto>> fetchOpenTradesOptionalBySymbolAndType
            (@RequestParam(value = "symbol", required = false) String symbol,
             @RequestParam(value = "type", required = false) TransactionType type) {
        List<Trade> trades = tradeDbService.findOpenTrades(symbol, type);
        return ResponseEntity.ok(tradeMapper.mapToTradesListDto(trades));
    }

    @GetMapping(params = "userId")
    public ResponseEntity<List<TradeDto>> fetchTradesByUserId(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "showClosed", required = false, defaultValue = "false") boolean showClosed)
            throws InvalidUserIdException {
        List<Trade> trades = tradeDbService.findAllUserTrades(userId, showClosed);
        return ResponseEntity.ok(tradeMapper.mapToTradesListDto(trades));
    }

    @PostMapping
    public ResponseEntity<Void> createTrade(@Valid @RequestBody TradeDto tradeDto) throws CryptocurrencyNotFoundException, NotEnoughFoundsException, WalletNotFoundException, WalletCryptoNotFoundException {
        Trade trade = tradeMapper.mapToTrade(tradeDto);
        if (trade.getTransactionType().equals(TransactionType.BUY)) {
            tradeDbService.createNewTradeBuy(trade);
        } else {
            tradeDbService.createNewTradeSell(trade);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{tradeId}/delete")
    public ResponseEntity<Void> deleteTrade(@PathVariable Long tradeId) throws TradeNotFoundException, WalletCryptoNotFoundException {
        tradeDbService.deleteTrade(tradeId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/{tradeId}")
    public ResponseEntity<DealDto> makeDeal(@PathVariable Long userId,
                                            @PathVariable Long tradeId,
                                            @RequestParam BigDecimal quantity)
            throws InvalidUserIdException, WalletCryptoNotFoundException, TradeNotFoundException, TradeWithYourselfException, NotEnoughFoundsException, CloneNotSupportedException, OfferIsCloseException {
        DealDto dealDto = tradeDbService.makeDeal(userId, tradeId, quantity);
        return ResponseEntity.ok(dealDto);
    }


}
