package com.sensei.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawDto {
    private String accountNumber;
    private BigDecimal quantity;
}
