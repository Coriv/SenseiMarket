package com.sensei.controller;

import com.sensei.domain.UserDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringJUnitWebConfig(UserController.class)
@WebMvcTest
class UserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserController userController;

    @Test
    void getAllUsersTest() throws Exception {
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

        UserDto userDto2 = UserDto.builder()
                .id(3L)
                .firstName("Pawel")
                .lastName("Brown")
                .dateOfJoin(LocalDateTime.now())
                .isActive(true)
                .username("Kordan")
                .password("Pass")
                .email("test@test.com")
                .build();

        List<UserDto> usersDto = Arrays.asList(userDto, userDto2);

        Mockito.when(userController.getAllUsers()).thenReturn(ResponseEntity.ok(usersDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password", Matchers.is("Password")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email", Matchers.is("test@test.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].active", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName", Matchers.is("Brown")));
    }

    @Test
    void getUserById() {
    }

    @Test
    void createUser() {
    }

    @Test
    void blockUser() {
    }

    @Test
    void editUserData() {
    }

    @Test
    void deleteUser() {
    }
}