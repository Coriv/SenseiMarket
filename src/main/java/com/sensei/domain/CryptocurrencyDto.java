package com.sensei.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CryptocurrencyDto {

    @NotNull
    private String symbol;
    private String name;
    private List<Long> walletsCryptoList;
}
