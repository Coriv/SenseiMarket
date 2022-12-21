package com.sensei.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(min = 2)
    @NotNull
    private String firstName;

    @Column
    @Size(min = 2)
    @NotNull
    private String lastName;

    @Column
    @NotNull
    private String username;

    @Column
    @NotNull
    private String password;

    @Column(length = 11)
    @Size(min = 11, max = 11, message = "Pesel have to has exactly 11 numbers")
    private String PESEL;

    @Column
    private String idCard;

    @Column
    @NotNull
    private LocalDateTime dateOfJoin;

    @Column
    @Email
    @NotNull
    private String email;

    @Column
    @NotNull
    private boolean isActive;

    @JoinColumn(unique = true)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Wallet wallet;
}
