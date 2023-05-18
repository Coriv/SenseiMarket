package com.sensei.cryptocurrency;

import com.sensei.trade.Trade;
import com.sensei.walletCrypto.WalletCrypto;
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
    public Cryptocurrency(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }
    @Id
    @NotNull
    @Column(unique = true)
    private String symbol;

    @NotNull
    @Column(unique = true)
    private String name;

    @OneToMany(targetEntity = WalletCrypto.class,
            mappedBy = "cryptocurrency",
            fetch = FetchType.EAGER)
    private List<WalletCrypto> walletCryptoList = new ArrayList<>();

    @OneToMany(targetEntity = Trade.class,
            mappedBy = "cryptocurrency",
            fetch = FetchType.LAZY)
    private List<Trade> trades = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cryptocurrency)) return false;

        Cryptocurrency that = (Cryptocurrency) o;

        return symbol.equals(that.symbol);
    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }
}
