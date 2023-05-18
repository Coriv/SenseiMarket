package com.sensei.cryptoPrice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CryptoPriceDto {
    private Long id;
    private String symbol;
    private String bidPrice;
    private String askPrice;
    @JsonProperty("priceChangePercent")
    private String priceChangePercent24h;
    private String volume;
    private LocalDateTime time;
}
