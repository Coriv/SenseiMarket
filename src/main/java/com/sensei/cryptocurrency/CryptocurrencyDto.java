package com.sensei.cryptocurrency;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CryptocurrencyDto {

    @NotNull
    private String symbol;
    @NotNull
    private String name;
}
