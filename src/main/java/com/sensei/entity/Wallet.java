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
    private boolean isActive;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",
            referencedColumnName = "id",
            unique = true)
    @NotNull
    private User user;

    @OneToMany(targetEntity = WalletCrypto.class,
            mappedBy = "wallet",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER)
    private List<WalletCrypto> cryptosList = new ArrayList<>();

}
