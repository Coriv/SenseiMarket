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
public class WalletCrypto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @PositiveOrZero
    private BigDecimal quantity = BigDecimal.valueOf(0);

    @ManyToOne(cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    @NotNull
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "cryptocurrency_id")
    @NotNull
    private Cryptocurrency cryptocurrency;

    @NotNull
    private String address;
}
