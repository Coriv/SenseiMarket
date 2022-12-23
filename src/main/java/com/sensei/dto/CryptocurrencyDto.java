package com.sensei.dto;

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
}
