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
@NoArgsConstructor
@AllArgsConstructor
public class Cryptocurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String symbol;

    @NotNull
    @Column(unique = true)
    private String name;

    @OneToMany(targetEntity = CryptoPrice.class,
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "crypto")
    private List<CryptoPrice> cryprosPrice;


    @OneToMany(targetEntity = WalletCrypto.class,
            mappedBy = "cryptocurrency",
            fetch = FetchType.EAGER)
    private List<WalletCrypto> walletCryptoList = new ArrayList<>();

    @OneToMany(targetEntity = Trade.class,
            mappedBy = "cryptocurrency",
            fetch = FetchType.LAZY)
    private List<Trade> trades = new ArrayList<>();

}
