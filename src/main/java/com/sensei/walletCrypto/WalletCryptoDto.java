package com.sensei.walletCrypto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WalletCryptoDto {

    private Long id;
    @PositiveOrZero
    private BigDecimal quantity;
    @NotNull
    private Long walletId;
    @NotNull
    private String cryptocurrencySymbol;
}
