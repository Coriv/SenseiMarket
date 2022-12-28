package com.sensei.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CryptocurrencyDto {

    private Long id;
    @NotNull
    private String symbol;
    private String name;
}
