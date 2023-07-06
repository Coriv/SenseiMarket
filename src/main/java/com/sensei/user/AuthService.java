package com.sensei.user;

import com.sensei.config.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final JwtService jwtService;

    public AuthResponse authenticate(AuthDto authDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDto.username(),
                        authDto.password()
                )
        );
        var user = userDao.findByUsername(authDto.username())
                .orElseThrow();
        var jwt = jwtService.generateToken(user);
        return AuthResponse
                .builder()
                .token(jwt)
                .build();
    }
}
