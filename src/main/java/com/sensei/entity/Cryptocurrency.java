package com.sensei.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cryptocurrency {

    @Id
    @NotNull
    @Column(unique = true)
    private String symbol;

    @Column
    @NotNull
    private String name;

    @OneToMany(targetEntity = WalletCrypto.class,
            mappedBy = "cryptocurrency",
            fetch = FetchType.EAGER)
    private List<WalletCrypto> walletCryptoList = new ArrayList<>();
}
