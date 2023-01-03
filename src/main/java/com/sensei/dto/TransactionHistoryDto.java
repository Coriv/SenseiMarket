package com.sensei.dto;

import com.sensei.entity.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionHistoryDto {
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