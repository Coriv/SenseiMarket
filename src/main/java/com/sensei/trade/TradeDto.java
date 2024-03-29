package com.sensei.trade;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class TradeDto {
    private Long id;
    @NotNull
    private TransactionType transactionType;
    @NotNull
    private String cryptoSymbol;
    @PositiveOrZero
    private BigDecimal quantity;
    @PositiveOrZero
    private BigDecimal price;
    private BigDecimal value;
    private boolean open;
    @NotNull
    private Long walletId;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
}