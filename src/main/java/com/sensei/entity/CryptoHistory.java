package com.sensei.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class CryptoHistory {

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
    private String symbol;

    @Column(updatable = false)
    @NotNull
    private OperationType type;

    @Column(updatable = false)
    @NotNull
    private BigDecimal quantity;

    @Column(updatable = false)
    private String addressTo;

    @Column(updatable = false)
    @NotNull
    private LocalDateTime time;
}
