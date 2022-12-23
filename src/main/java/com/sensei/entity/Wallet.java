package com.sensei.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private boolean active = false;

    @OneToOne(mappedBy = "wallet")
    private User user;

    @OneToMany(targetEntity = WalletCrypto.class,
            mappedBy = "wallet",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<WalletCrypto> cryptosList = new ArrayList<>();

}
