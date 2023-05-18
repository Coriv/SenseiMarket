package com.sensei.history;

import com.sensei.trade.OperationType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CryptoHistoryDto {
    @NotNull
    private final Long id;
    @NotNull
    private final Long userId;
    @NotNull
    private final String symbol;
    @NotNull
    private final OperationType type;
    @NotNull
    private final BigDecimal quantity;
    @NotNull
    private final String addressTo;
    @NotNull
    private final LocalDateTime time;
}