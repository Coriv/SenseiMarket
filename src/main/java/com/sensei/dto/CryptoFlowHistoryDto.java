package com.sensei.dto;

import com.sensei.entity.OperationType;
import com.sensei.entity.TransactionType;
import com.sensei.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CryptoFlowHistoryDto  {
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