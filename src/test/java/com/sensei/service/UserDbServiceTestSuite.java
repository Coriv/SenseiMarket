package com.sensei.service;

import com.sensei.entity.User;
import com.sensei.entity.Cryptocurrency;
import com.sensei.entity.Wallet;
import com.sensei.entity.WalletCrypto;
import com.sensei.exception.InvalidUserIdException;
import com.sensei.exception.NotEmptyWalletException;
import com.sensei.repository.UserDao;
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
class UserDbServiceTestSuite {

    @InjectMocks
    private UserDbService dbService;
    @Mock
    private UserDao userDao;

    @Test
    void findUserById() throws InvalidUserIdException {
        //Given
        User User = new User();
        User.setFirstName("Sebastian");
        User.setLastName("Boron");
        User.setDateOfJoin(LocalDateTime.now());
        User.setActive(true);
        User.setUsername("Coriver");
        User.setPassword("Password");
        User.setEmail("sebastian@kodilla.com");
        User.setPESEL("12345678910");
        User.setIdCard("AZC2133");

        when(userDao.findById(Mockito.any())).thenReturn(Optional.of(User));
        //When
        User foundUser = dbService.findUserById(User.getId());
        //Then
        assertEquals(foundUser.getId(), User.getId());
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
        User User2 = new User();
        User2.setFirstName("Marek");
        User2.setLastName("Kowalski");
        List<User> users = Arrays.asList(user, User2);

        when(userDao.findAll()).thenReturn(users);
        //When
        List<User> resultList = dbService.getAllUsers();
        //Then
        assertEquals(resultList.size(), 2);
        assertEquals(resultList.get(0).getFirstName(), "Sebastian");
        assertEquals(resultList.get(1).getLastName(), "Kowalski");
    }

    @Test
    void saveUserTest() {
        //Given
        User user = new User();
        user.setFirstName("Sebastian");
        user.setLastName("Brown");

        when(userDao.save(any(User.class))).thenReturn(user);
        //When
        User resultUser = dbService.save(user);
        //Then
        assertEquals(resultUser.getFirstName(), "Sebastian");
        assertEquals(resultUser.getLastName(), "Brown");
    }

    @Test
    void blockUserTest() {
        User User = new User();
        User.setFirstName("Sebastian");
        User.setLastName("Brown");
        User.setActive(false);

        when(userDao.save(any(User.class))).thenReturn(User);
        //When
        User resultUser = dbService.blockUser(User);
        //Then
        assertTrue(resultUser.isActive());
    }

    @Test
    void deleteUserTest() {
        //Given
        User User = new User();
        User.setFirstName("Sebastian");
        User.setLastName("Boron");
        Wallet wallet = new Wallet();
        wallet.setActive(true);
        wallet.setUser(User);
        User.setWallet(wallet);
        Cryptocurrency crypto1 = new Cryptocurrency();
        crypto1.setSymbol("BTC");
        crypto1.setName("Bitcoin");
        WalletCrypto walletCrypto = new WalletCrypto();
        walletCrypto.setWallet(wallet);
        walletCrypto.setCryptocurrency(crypto1);
        walletCrypto.setQuantity(new BigDecimal("1200"));
        crypto1.getWalletCryptoList().add(walletCrypto);
        wallet.getCryptosList().add(walletCrypto);

        when(userDao.findById(any())).thenReturn(Optional.of(User));
        //When
        assertThrows(NotEmptyWalletException.class, () -> dbService.deleteUser(User.getId()));
    }
}