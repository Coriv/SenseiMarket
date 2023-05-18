package com.sensei.history;

import com.sensei.trade.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TradeHistoryDto {
    private final Long id;
    @NotNull
    private final TransactionType transactionType;
    @NotNull
    private final String cryptocurrency;
    @PositiveOrZero
    private final BigDecimal quantity;
    @PositiveOrZero
    private final BigDecimal price;
    @PositiveOrZero
    private final BigDecimal value;
    @NotNull
    private final Long userId;
    @NotNull
    private final LocalDateTime transactionTime;
}