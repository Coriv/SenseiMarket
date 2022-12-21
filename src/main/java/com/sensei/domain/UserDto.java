package com.sensei.domain;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String PESEL;
    private String idCard;
    private LocalDateTime dateOfJoin;
    @Email
    private String email;
    private boolean isActive;
    private Long walletId;

}
