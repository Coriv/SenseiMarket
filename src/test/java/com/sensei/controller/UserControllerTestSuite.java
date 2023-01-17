//package com.sensei.controller;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.sensei.config.LocalDateTimeSerializer;
//import com.sensei.dto.UserDto;
//import org.hamcrest.Matchers;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//
//@SpringJUnitWebConfig
//@WebMvcTest(UserController.class)
//class UserControllerTestSuite {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private WebApplicationContext context;
//    @MockBean
//    private UserController userController;
//    private UserDto userDto;
//
//    @BeforeEach
//    void prepareUserDto() {
//        userDto = UserDto.builder()
//                .id(1L)
//                .firstName("Sebastian")
//                .lastName("Boron")
//                .dateOfJoin(LocalDateTime.now())
//                .active(true)
//                .username("Coriver")
//                .password("Password")
//                .email("sebastian@kodilla.com")
//                .build();
//
////        mockMvc = MockMvcBuilders
////                .webAppContextSetup(context)
////                .apply(SecurityMockMvcConfigurers.springSecurity())
////                .build();
//    }
//
//    @Test
//    void getAllUsersTest() throws Exception {
//        UserDto userDto2 = UserDto.builder()
//                .id(3L)
//                .firstName("Pawel")
//                .lastName("Brown")
//                .dateOfJoin(LocalDateTime.now())
//                .active(true)
//                .username("Kordan")
//                .password("Pass")
//                .email("test@test.com")
//                .build();
//
//        List<UserDto> usersDto = Arrays.asList(userDto, userDto2);
//
//        when(userController.fetchAllUsers()).thenReturn(ResponseEntity.ok(usersDto));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("Sebastian")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is("Boron")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].active", Matchers.is(true)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username", Matchers.is("Coriver")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password", Matchers.is("Password")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is("sebastian@kodilla.com")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email", Matchers.is("test@test.com")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].active", Matchers.is(true)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName", Matchers.is("Brown")));
//    }
//
//    @Test
//    void getUserByIdTest() throws Exception {
//        when(userController.fetchUserById(userDto.getId())).thenReturn(ResponseEntity.ok(userDto));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user/" + 1)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Sebastian")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Boron")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.active", Matchers.is(true)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is("Coriver")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("Password")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("sebastian@kodilla.com")));
//    }
//
//    @Test
//    void createUserTest() throws Exception {
//        when(userController.createUser(any(UserDto.class))).thenReturn(ResponseEntity.ok().build());
//
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
//        Gson gson = gsonBuilder.setPrettyPrinting().create();
//        String userJson = gson.toJson(userDto);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .characterEncoding("UTF-8")
//                        .content(userJson))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @Test
//    void blockUserTest() throws Exception {
//        userDto.setActive(false);
//        userController.blockUser(1L);
//        when(userController.blockUser(1L)).thenReturn(ResponseEntity.ok(userDto));
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/user/block?userId=" + 1L)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.active", Matchers.is(false)));
//    }
//
//    @Test
//    void editUserDataTest() throws Exception {
//        UserDto user2 = UserDto.builder().build();
//        user2.setFirstName("Darek");
//        user2.setPESEL("324234523451");
//
//        when(userController.updateUserData(any(UserDto.class))).thenReturn(ResponseEntity.ok(user2));
//
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
//        Gson gson = gsonBuilder.setPrettyPrinting().create();
//        String userJson = gson.toJson(userDto);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("UTF-8")
//                        .content(userJson))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @Test
//    void deleteUserTest() throws Exception {
//        when(userController.deleteUser(userDto.getId())).thenReturn(ResponseEntity.ok().build());
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/user?userId=" + userDto.getId())
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//}