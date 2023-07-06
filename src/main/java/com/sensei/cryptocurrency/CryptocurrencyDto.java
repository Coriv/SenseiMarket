package com.sensei.cryptocurrency;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CryptocurrencyDto {

    private final String symbol;
    private final String name;
}
