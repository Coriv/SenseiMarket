package com.sensei.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WalletCryptoDto {

    private Long id;
    private BigDecimal quantity;
    private Long walletId;
    private String cryptocurrencySymbol;
}
