package com.sensei.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoPair {
    @Id
    @NotNull
    private String symbol;
    @PositiveOrZero
    private BigDecimal bidPrice;
    @PositiveOrZero
    private BigDecimal askPrice;
    @NotNull
    private BigDecimal priceChangePercent;
    @PositiveOrZero
    private BigDecimal volume;
    @OneToMany(targetEntity = Trade.class,
            mappedBy = "cryptoPair",
            fetch = FetchType.LAZY)
    private List<Trade> trades = new ArrayList<>();
}
