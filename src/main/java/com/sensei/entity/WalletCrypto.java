package com.sensei.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WalletCrypto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @PositiveOrZero
    private BigDecimal quantity;

    @ManyToOne(cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cryptocurrency_id")
    private Cryptocurrency cryptocurrency;
}
