package com.sensei.dto;

import com.sensei.entity.CryptoPair;
import com.sensei.entity.TransactionType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TradeDto {
    private Long id;
    @NotNull
    private TransactionType transactionType;
    @NotNull
    private String cryptoPair;
    @PositiveOrZero
    private BigDecimal quantity;
    @PositiveOrZero
    private BigDecimal price;
    @PositiveOrZero
    private BigDecimal value;
    private boolean open;
    private Long walletId;
    @PastOrPresent
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
}