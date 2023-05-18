package com.sensei.walletCash;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawDto {
    private String accountNumber;
    private BigDecimal quantity;
}
