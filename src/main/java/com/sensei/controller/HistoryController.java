package com.sensei.controller;

import com.sensei.dto.CashFlowHistoryDto;
import com.sensei.dto.CryptoFlowHistoryDto;
import com.sensei.dto.TradeHistoryDto;
import com.sensei.entity.TradeHistory;
import com.sensei.entity.TransactionType;
import com.sensei.mapper.CashFlowHistMapper;
import com.sensei.mapper.CryptoFlowHistoryMapper;
import com.sensei.mapper.TransactionHistoryMapper;
import com.sensei.service.CashFlowHistService;
import com.sensei.service.CryptoFlowHistService;
import com.sensei.service.TransactionHistoryDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/history")
public class HistoryController {

    private final TransactionHistoryDbService historyService;
    private final TransactionHistoryMapper historyMapper;
    private final CashFlowHistService cashFlowHistService;
    private final CashFlowHistMapper cashFlowHistMapper;
    private final CryptoFlowHistService cryptoFlowHistService;
    private final CryptoFlowHistoryMapper cryptoFlowHistoryMapper;

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
    public ResponseEntity<List<CashFlowHistoryDto>> fetchCashFlowHistoryByUser(
            @PathVariable Long userId) {
        var cashFlowList = cashFlowHistService.getAllByUserId(userId);
        return ResponseEntity.ok(cashFlowHistMapper.mapToCashHistoryDtoList(cashFlowList));
    }

    @GetMapping("/crypto/{userId}")
    public ResponseEntity<List<CryptoFlowHistoryDto>> fetchCryptoFlowHistoryByUser(
            @PathVariable Long userId) {
        var cryptoFlowList = cryptoFlowHistService.getAllByUserId(userId);
        return ResponseEntity.ok(cryptoFlowHistoryMapper.mapToCryptoFlowHistList(cryptoFlowList));
    }
}
