package com.sensei.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
public class AdminConfig {

    @Value("${binance.api}")
    private String binanceApi;

    @Value("${crypto.stablecoins}")
    private String stablecoin;

    @Value("${nbp.api.usd}")
    private String USD_RATE;

    @Value("${sensei.market.token.address}")
    private String address;
}
