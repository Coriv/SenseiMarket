package com.sensei.controller;

import com.sensei.dto.TransactionHistoryDto;
import com.sensei.entity.TransactionHistory;
import com.sensei.entity.TransactionType;
import com.sensei.mapper.TransactionHistoryMapper;
import com.sensei.service.TransactionHistoryDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/history")
public class HistoryController {

    private final TransactionHistoryDbService historyService;
    private final TransactionHistoryMapper historyMapper;

    @GetMapping(params = "userId")
    public ResponseEntity<List<TransactionHistoryDto>> fetchUserHistoryOptionalParams
            (@RequestParam(value = "userId") Long userId,
             @RequestParam(value = "symbol", required = false) String symbol,
             @RequestParam(value = "type", required = false) TransactionType type,
             @RequestParam(value = "maxValue", required = false) BigDecimal maxValue,
             @RequestParam(value = "minValue", required = false) BigDecimal minValue) {
        List<TransactionHistory> history =
                historyService.findByUserIdOptionalParams(userId, symbol, type, maxValue, minValue);
        return ResponseEntity.ok(historyMapper.mapToTransactionsListDto(history));
    }
}
