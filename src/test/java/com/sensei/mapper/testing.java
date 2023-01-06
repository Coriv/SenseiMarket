package com.sensei.mapper;

import com.sensei.dto.UserDto;
import com.sensei.entity.User;
import com.sensei.exception.InvalidUserIdException;
import com.sensei.exception.UserNotVerifyException;
import com.sensei.exception.WalletAlreadyExistException;
import com.sensei.repository.UserDao;
import com.sensei.service.UserDbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootTest
public class testing {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDbService userDbService;

    @Test
    void save() throws UserNotVerifyException, WalletAlreadyExistException, InvalidUserIdException {
        UserDto userDto = UserDto.builder()
                .firstName("Security")
                .lastName("Krakow")
                .username("Krakow")
                .password("Krakow")
                .email("test@test.pl")
                .active(true)
                .build();
        var user = userMapper.mapToUser(userDto);
        userDbService.createUser(user);
    }

    @Test
    void saveNewUser() {
        User user = new User();
        user.setFirstName("TEST");
        user.setLastName("TEST");
        user.setUsername("Konin");
        user.setPassword(passwordEncoder.encode("Konin"));
        user.setDateOfJoin(LocalDateTime.now());
        user.setEmail("test@test.pl");
        userDao.save(user);
    }
}
