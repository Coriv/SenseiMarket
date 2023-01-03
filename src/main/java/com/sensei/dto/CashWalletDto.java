package com.sensei.dto;

import com.sensei.entity.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@AllArgsConstructor
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
