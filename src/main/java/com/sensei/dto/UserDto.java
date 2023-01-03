package com.sensei.dto;

import com.sensei.entity.Cryptocurrency;
import com.sensei.mailService.MailService;
import com.sensei.observer.Observer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class UserDto implements Observer {

    @NotNull
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String username;
    @NotNull
    private String password;
    private String PESEL;
    private String idCard;
    private LocalDateTime dateOfJoin;
    @Email
    @NotNull
    private String email;
    private boolean active;
    private Long walletId;
    private boolean notification;

    @Override
    public void update(Cryptocurrency cryptocurrency) {
        log.info("Notification about new cryptocurrency has been sent to user with ID: " + this.getId() + ".");
    }
    @Override
    public String getEmail() {
        return this.email;
    }
}

