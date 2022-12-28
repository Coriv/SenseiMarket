package com.sensei.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private boolean active = false;

    @OneToOne(mappedBy = "wallet",
            optional = false)
    private User user;

    @OneToMany(targetEntity = WalletCrypto.class,
            mappedBy = "wallet",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<WalletCrypto> cryptosList = new ArrayList<>();

    @OneToMany(targetEntity = Trade.class,
            mappedBy = "wallet",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    private List<Trade> trades = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private CashWallet cashWallet;

}
