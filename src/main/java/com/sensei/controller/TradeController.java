package com.sensei.controller;

import com.sensei.dto.TradeDto;
import com.sensei.entity.Trade;
import com.sensei.exception.CryptoPairDoesNotFoundException;
import com.sensei.exception.InvalidUserIdException;
import com.sensei.exception.TradeNotFoundException;
import com.sensei.exception.TransactionDoesNotBellowToAnyWalletException;
import com.sensei.mapper.TradeMapper;
import com.sensei.service.TradeDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeDbService tradeDbService;
    private final TradeMapper tradeMapper;

    @GetMapping
    public ResponseEntity<List<TradeDto>> fetchOpenTradesOptionalBySymbol
            (@RequestParam(value = "symbol", required = false) String symbol) {
        List<Trade> trades = tradeDbService.findOpenTrades(symbol);
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
    public ResponseEntity<Void> createTrade(@RequestBody TradeDto tradeDto) throws CryptoPairDoesNotFoundException, TransactionDoesNotBellowToAnyWalletException {
        Trade trade = tradeMapper.mapToTrade(tradeDto);
        tradeDbService.createNewTrade(trade);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTrade(@RequestParam Long tradeId) throws TradeNotFoundException {
        tradeDbService.deleteTrade(tradeId);
        return ResponseEntity.ok().build();
    }

}
