package com.sensei.trade;

import com.sensei.cryptocurrency.Cryptocurrency;
import com.sensei.wallet.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY,
            optional = false)
    @NotNull
    private Wallet wallet;

    @NotNull
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY,
            optional = false)
    private Cryptocurrency cryptocurrency;

    @PositiveOrZero
    private BigDecimal quantity;

    @PositiveOrZero
    private BigDecimal price;

    @PositiveOrZero
    private BigDecimal value;

    private boolean open = true;

    @PastOrPresent
    @Column(updatable = false)
    private LocalDateTime openDate;

    private LocalDateTime closeDate;
}
