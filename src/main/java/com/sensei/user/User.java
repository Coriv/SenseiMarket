package com.sensei.user;

import com.sensei.history.CashHistory;
import com.sensei.history.CryptoHistory;
import com.sensei.history.TradeHistory;
import com.sensei.wallet.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "Users", indexes = @Index(columnList = "username"))
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2)
    @NotNull
    private String firstName;

    @Size(min = 2)
    @NotNull
    private String lastName;

    @Column(unique = true)
    @NotNull
    private String username;

    @NotNull
    private String password;

    @Column(length = 11)
    @Size(min = 11, max = 11, message = "Pesel have to has exactly 11 numbers")
    private String PESEL;

    private String idCard;

    @Column(updatable = false)
    private LocalDateTime dateOfJoin;

    @Email
    @NotNull
    private String email;

    @NotNull
    private boolean active = true;

    private boolean notification = true;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Wallet wallet;

    @OneToMany(targetEntity = TradeHistory.class,
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    private List<TradeHistory> transactions = new ArrayList<>();

    @OneToMany(targetEntity = CashHistory.class,
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    private List<CashHistory> cashFlows = new ArrayList<>();

    @OneToMany(targetEntity = CryptoHistory.class,
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    private List<CryptoHistory> cryptoFlows = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Role authority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(authority.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
