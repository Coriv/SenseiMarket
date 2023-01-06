package com.sensei.controller;

import com.sensei.dto.TradeHistoryDto;
import com.sensei.entity.TradeHistory;
import com.sensei.entity.TransactionType;
import com.sensei.mapper.TransactionHistoryMapper;
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
}
