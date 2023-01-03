package com.sensei.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(updatable = false)
    private String symbol;
    @PositiveOrZero
    @Column(updatable = false)
    private BigDecimal bidPrice;
    @PositiveOrZero
    @Column(updatable = false)
    private BigDecimal askPrice;
    @NotNull
    @Column(updatable = false)
    private BigDecimal priceChangePercent24h;
    @PositiveOrZero
    @Column(updatable = false)
    private BigDecimal volume;
    @NotNull
    @Column(updatable = false)
    private LocalDateTime time;
}
