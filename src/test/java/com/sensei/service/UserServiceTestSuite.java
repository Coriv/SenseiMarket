package com.sensei.service;

import com.sensei.cryptocurrency.Cryptocurrency;
import com.sensei.user.User;
import com.sensei.wallet.Wallet;
import com.sensei.walletCrypto.WalletCrypto;
import com.sensei.exception.*;
import com.sensei.user.UserDao;
import com.sensei.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTestSuite {

    @InjectMocks
    private UserService dbService;
    @Mock
    private UserDao userDao;

    @Test
    void findUserById() throws InvalidUserIdException {
        //Given
        User user = new User();
        user.setFirstName("Sebastian");
        user.setLastName("Boron");
        user.setDateOfJoin(LocalDateTime.now());
        user.setActive(true);
        user.setUsername("Coriver");
        user.setPassword("Password");
        user.setEmail("sebastian@kodilla.com");
        user.setPESEL("12345678910");
        user.setIdCard("AZC2133");

        when(userDao.findById(Mockito.any())).thenReturn(Optional.of(user));
        //When
        User foundUser = dbService.findUserById(user.getId());
        //Then
        assertEquals(foundUser.getId(), user.getId());
        assertEquals(foundUser.getFirstName(), "Sebastian");
        assertTrue(foundUser.isActive());
        assertEquals(foundUser.getEmail(), "sebastian@kodilla.com");
        assertEquals(foundUser.getUsername(), "Coriver");
    }

    @Test
    void getAllUsersTest() {
        //Given
        User user = new User();
        user.setFirstName("Sebastian");
        user.setLastName("Brown");
        User user2 = new User();
        user2.setFirstName("Marek");
        user2.setLastName("Kowalski");
        List<User> users = Arrays.asList(user, user2);

        when(userDao.findAll()).thenReturn(users);
        //When
        List<User> resultList = dbService.getAllUsers();
        //Then
        assertEquals(resultList.size(), 2);
        assertEquals(resultList.get(0).getFirstName(), "Sebastian");
        assertEquals(resultList.get(1).getLastName(), "Kowalski");
    }

    @Test
    void createUserTest() throws UserNotVerifyException, WalletAlreadyExistException, InvalidUserIdException {
        //Given
        User user = new User();
        user.setFirstName("Sebastian");
        user.setLastName("Brown");
        when(userDao.save(any(User.class))).thenReturn(user);
        //When
        //todo check if tests are fit with new user creation User resultUser = dbService.createUser(user);
        //Then
        //assertEquals(resultUser.getFirstName(), "Sebastian");
        //assertEquals(resultUser.getLastName(), "Brown");
    }

    @Test
    public void updateUserTest() {
        //Given
        User user = new User();
        user.setFirstName("Sebastian");
        user.setLastName("Brown");
        when(userDao.save(any(User.class))).thenReturn(user);
        //When
        User resultUser = dbService.updateUser(user);
        //Then
        assertEquals(resultUser.getFirstName(), "Sebastian");
        assertEquals(resultUser.getLastName(), "Brown");
    }

    @Test
    void blockUserTest() {
        User user = new User();
        user.setFirstName("Sebastian");
        user.setLastName("Brown");
        user.setActive(false);

        when(userDao.save(any(User.class))).thenReturn(user);
        //When
        User resultUser = dbService.blockUser(user);
        //Then
        assertTrue(resultUser.isActive());
    }

    @Test
    void deleteUserTest() {
        //Given
        User user = new User();
        user.setFirstName("Sebastian");
        user.setLastName("Boron");
        Wallet wallet = new Wallet();
        wallet.setActive(true);
        wallet.setUser(user);
        user.setWallet(wallet);
        Cryptocurrency crypto1 = new Cryptocurrency();
        crypto1.setSymbol("BTC");
        crypto1.setName("Bitcoin");
        WalletCrypto walletCrypto = new WalletCrypto();
        walletCrypto.setWallet(wallet);
        walletCrypto.setCryptocurrency(crypto1);
        walletCrypto.setQuantity(new BigDecimal("1200"));
        crypto1.getWalletCryptoList().add(walletCrypto);
        wallet.getCryptosList().add(walletCrypto);

        when(userDao.findById(any())).thenReturn(Optional.of(user));
        //When
        assertThrows(NotEmptyWalletException.class, () -> dbService.deleteUser(user.getId()));
    }

    @Test
    void getUsernamesTest() {
        User user = new User();
        user.setFirstName("Sebastian");
        user.setLastName("Brown");
        User user2 = new User();
        user2.setFirstName("Marek");
        user2.setLastName("Kowalski");
        List<User> users = Arrays.asList(user, user2);
        when(userDao.findAll()).thenReturn(users);
        //When
        List<String> usernames = dbService.getAllUsernames();
        //Then
        assertEquals(usernames.size(), 2);
    }

    @Test
    void findWalletIdTest() throws UserNotFoundException {
        User user = new User();
        user.setUsername("dd");
        user.setId(10L);
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setId(66L);
        user.setWallet(wallet);
        //When
        when(userDao.findByUsername(any())).thenReturn(Optional.of(user));
        var walletId = dbService.findWalletId("dd");
        //then
        assertEquals(walletId, 66L);
    }
}