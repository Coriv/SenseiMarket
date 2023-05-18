package com.sensei.walletCash;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CashWalletDto {

    private Long id;
    @Builder.Default
    private String currency = "$ USD";
    private Long walletId;
    @PositiveOrZero
    @Builder.Default
    private BigDecimal quantity = BigDecimal.valueOf(0);
}
