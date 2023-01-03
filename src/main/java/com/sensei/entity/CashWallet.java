package com.sensei.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String currency = "$ USD";

    @OneToOne(cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER)
    private Wallet wallet;

    @PositiveOrZero
    private BigDecimal quantity = BigDecimal.valueOf(0);
}
