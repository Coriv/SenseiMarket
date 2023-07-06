package com.sensei.wallet;

import com.sensei.walletCash.CashWallet;
import com.sensei.trade.Trade;
import com.sensei.walletCrypto.WalletCrypto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.client.RestTemplate;

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

    @NotNull
    private boolean active = false;

    @OneToOne(mappedBy = "wallet",
            optional = false)
    private com.sensei.user.User User;

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
            fetch = FetchType.EAGER)
    private CashWallet cashWallet;

}
