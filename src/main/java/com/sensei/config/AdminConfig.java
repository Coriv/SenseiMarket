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

    @Value("{${crypto.stablecoins}")
    private String stablecoin;
}
