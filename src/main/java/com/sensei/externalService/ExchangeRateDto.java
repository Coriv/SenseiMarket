package com.sensei.externalService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateDto {
    private String currency;
    private String code;
    private RatesDto[] rates;
}
