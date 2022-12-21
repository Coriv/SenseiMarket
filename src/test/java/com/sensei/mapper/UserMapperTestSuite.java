package com.sensei.mapper;

import com.sensei.domain.UserDto;
import com.sensei.entity.User;
import com.sensei.entity.Wallet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserMapperTestSuite {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void mapToUserTest() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .firstName("Sebastian")
                .lastName("Boron")
                .dateOfJoin(LocalDateTime.now())
                .isActive(true)
                .username("Coriver")
                .password("Password")
                .email("sebastian@kodilla.com")
                .build();

        User user = userMapper.mapToUser(userDto);

        assertEquals(user.getFirstName(), "Sebastian");
        assertEquals(user.getLastName(), "Boron");
        assertEquals(user.getDateOfJoin(), userDto.getDateOfJoin());
        assertTrue(user.isActive());
        assertEquals(user.getUsername(), "Coriver");
        assertEquals(user.getPassword(), "Password");
        assertEquals(user.getEmail(), "sebastian@kodilla.com");
    }

    @Test
    public void mapToUserDtoTest() {
        Wallet wallet = new Wallet();

        User user = new User();
        user.setFirstName("Sebastian");
        user.setLastName("Boron");
        user.setDateOfJoin(LocalDateTime.now());
        user.setActive(true);
        user.setUsername("Coriver");
        user.setPassword("Password");
        user.setEmail("sebastian@kodilla.com");
        user.setWallet(wallet);

        UserDto userDto = userMapper.mapToUserDto(user);

        assertEquals(userDto.getFirstName(), "Sebastian");
        assertEquals(userDto.getLastName(), "Boron");
        assertEquals(userDto.getDateOfJoin(), userDto.getDateOfJoin());
        assertTrue(userDto.isActive());
        assertEquals(userDto.getUsername(), "Coriver");
        assertEquals(userDto.getPassword(), "Password");
        assertEquals(userDto.getEmail(), "sebastian@kodilla.com");
        assertEquals(userDto.getWalletId(), wallet.getId());
    }
}