package com.sensei.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

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
    private BigDecimal quantity;

    @ManyToOne(cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "cryptocurrency_id")
    private Cryptocurrency cryptocurrency;
}
