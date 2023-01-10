package com.sensei.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class CashHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(updatable = false)
    private User user;

    @Column(updatable = false)
    @NotNull
    private OperationType type;

    @Column(updatable = false)
    @PositiveOrZero
    private BigDecimal quantityUSD;

    @Column(updatable = false)
    @PositiveOrZero
    private BigDecimal quantityPLN;

    @Column(updatable = false)
    private String toAccount;

    @Column(updatable = false)
    private LocalDateTime time;
}
