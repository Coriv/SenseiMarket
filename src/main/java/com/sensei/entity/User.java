package com.sensei.entity;

import com.sensei.mailService.Mail;
import com.sensei.mailService.MailService;
import com.sensei.observer.Observer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Users", indexes = @Index(columnList = "username"))
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDateTime dateOfJoin;

    @Column
    @Email
    @NotNull
    private String email;

    @Column
    @NotNull
    private boolean active = true;

    private boolean notification = true;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Wallet wallet;

    @OneToMany(targetEntity = TransactionHistory.class,
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    private List<TransactionHistory> transactions = new ArrayList<>();
}
