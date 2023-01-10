package com.sensei.controller;

import com.sensei.dto.CashHistoryDto;
import com.sensei.dto.CryptoHistoryDto;
import com.sensei.dto.TradeHistoryDto;
import com.sensei.entity.TradeHistory;
import com.sensei.entity.TransactionType;
import com.sensei.mapper.CashHistMapper;
import com.sensei.mapper.CryptoHistMapper;
import com.sensei.mapper.TransactionHistoryMapper;
import com.sensei.service.CashHistService;
import com.sensei.service.CryptoHistService;
import com.sensei.service.TradeHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/history")
public class HistoryController {

    private final TradeHistoryService historyService;
    private final TransactionHistoryMapper historyMapper;
    private final CashHistService cashHistService;
    private final CashHistMapper cashHistMapper;
    private final CryptoHistService cryptoHistService;
    private final CryptoHistMapper cryptoHistMapper;

    @GetMapping("/{userId}")
    public ResponseEntity<List<TradeHistoryDto>> fetchUserHistoryOptionalParams
            (@PathVariable Long userId,
             @RequestParam(value = "symbol", required = false) String symbol,
             @RequestParam(value = "type", required = false) TransactionType type,
             @RequestParam(value = "maxValue", required = false) BigDecimal maxValue,
             @RequestParam(value = "minValue", required = false) BigDecimal minValue) {
        List<TradeHistory> history =
                historyService.findByUserIdOptionalParams(userId, symbol, type, maxValue, minValue);
        return ResponseEntity.ok(historyMapper.mapToTransactionsListDto(history));
    }

    @GetMapping("/cash/{userId}")
    public ResponseEntity<List<CashHistoryDto>> fetchCashFlowHistoryByUser(
            @PathVariable Long userId) {
        var cashFlowList = cashHistService.getAllByUserId(userId);
        return ResponseEntity.ok(cashHistMapper.mapToCashHistoryDtoList(cashFlowList));
    }

    @GetMapping("/crypto/{userId}")
    public ResponseEntity<List<CryptoHistoryDto>> fetchCryptoFlowHistoryByUser(
            @PathVariable Long userId) {
        var cryptoFlowList = cryptoHistService.getAllByUserId(userId);
        return ResponseEntity.ok(cryptoHistMapper.mapToCryptoFlowHistList(cryptoFlowList));
    }
}
