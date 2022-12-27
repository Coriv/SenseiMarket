package com.sensei.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @Column(updatable = false)
    private TransactionType transactionType;

    @NotNull
    @Column(updatable = false)
    private String cryptoPair;

    @PositiveOrZero
    @Column(updatable = false)
    private BigDecimal quantity;

    @PositiveOrZero
    @Column(updatable = false)
    private BigDecimal price;

    @PositiveOrZero
    @Column(updatable = false)
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(updatable = false)
    private User user;

    @NotNull
    @Column(updatable = false)
    private LocalDateTime transactionTime;
}
