package com.sensei.service;

import com.sensei.dto.UserDto;
import com.sensei.entity.Cryptocurrency;
import com.sensei.entity.WalletCrypto;
import com.sensei.exception.CryptoIsObjectOfTradingException;
import com.sensei.exception.CryptocurrencyNotFoundException;
import com.sensei.mailService.MailService;
import com.sensei.mapper.UserMapper;
import com.sensei.repository.CryptocurrencyDao;
import com.sensei.repository.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CryptocurrencyServiceTestSuite {
    @InjectMocks
    private CryptocurrencyService dbService;
    @Mock
    private CryptocurrencyDao cryptoDao;
    @Mock
    private UserDao userDao;
    @Mock
    private UserMapper userMapper;
    @Mock
    private MailService mailService;
    @Test
    void findCryptocurrencyBySymbolTest() throws CryptocurrencyNotFoundException {
        //Given
        Cryptocurrency bitcoin = new Cryptocurrency();
        bitcoin.setSymbol("BTC");
        bitcoin.setName("Bitcoin");
        when(cryptoDao.findBySymbol("BTC")).thenReturn(Optional.of(bitcoin));
        //When
        Cryptocurrency resultCrypto = dbService.findCryptocurrencyBySymbol("BTC");
        //Then
        assertEquals(resultCrypto.getSymbol(), "BTC");
        assertEquals(resultCrypto.getName(), "Bitcoin");
    }

    @Test
    void findAllTest() {
        //Given
        Cryptocurrency bitcoin = new Cryptocurrency();
        bitcoin.setSymbol("BTC");
        bitcoin.setName("Bitcoin");
        Cryptocurrency ethereum = new Cryptocurrency();
        ethereum.setSymbol("ETC");
        ethereum.setName("Ethereum");
        List<Cryptocurrency> cryptos = Arrays.asList(bitcoin, ethereum);

        when(cryptoDao.findAll()).thenReturn(cryptos);
        //When
        List<Cryptocurrency> resultCryptos = dbService.findAll();
        //Then
        assertEquals(resultCryptos.size(), 2);
        assertEquals(resultCryptos.get(0).getSymbol(), "BTC");
        assertEquals(resultCryptos.get(1).getName(), "Ethereum");
    }

    @Test
    void addTest() {
        //Given
        Cryptocurrency bitcoin = new Cryptocurrency();
        bitcoin.setSymbol("BTC");
        bitcoin.setName("Bitcoin");
        when(cryptoDao.save(any(Cryptocurrency.class))).thenReturn(bitcoin);
        //When
        Cryptocurrency resultCrypto = dbService.add(bitcoin);
        //Then
        assertEquals(resultCrypto.getSymbol(), "BTC");
        assertEquals(resultCrypto.getName(), "Bitcoin");
    }

    @Test
    void deleteBySymbolTest() {
        Cryptocurrency bitcoin = new Cryptocurrency();
        bitcoin.setSymbol("BTC");
        bitcoin.setName("Bitcoin");
        WalletCrypto walletCrypto = new WalletCrypto();
        walletCrypto.setCryptocurrency(bitcoin);
        walletCrypto.setQuantity(new BigDecimal("213"));
        bitcoin.setWalletCryptoList(Arrays.asList(walletCrypto));

        when(cryptoDao.findBySymbol(any())).thenReturn(Optional.of(bitcoin));
        //When&Then
        assertThrows(CryptoIsObjectOfTradingException.class, () -> dbService.deleteBySymbol("BTC"));
    }
    @Test
    void registryAndRemoveObserverTest() {
        //Given
        UserDto userDto = UserDto.builder().build();
        int sizeBefore = dbService.getObservers().size();
        //When
        dbService.registryObserver(userDto);
        int sizeAfterAdd = dbService.getObservers().size();
        dbService.removeObserver(userDto);
        int sizeAfterRemove = dbService.getObservers().size();
        //Then
        assertEquals(sizeBefore + 1, sizeAfterAdd);
        assertEquals(sizeAfterRemove, sizeBefore);
        assertEquals(sizeAfterRemove + 1, sizeAfterAdd);
    }
    @Test
    void notifyObserves() {
        Cryptocurrency btc = new Cryptocurrency("BTC", "Bitcoin");
        UserDto userDto = UserDto.builder().build();
        //when
        dbService.registryObserver(userDto);
        int quantity = dbService.getObservers().size();
        dbService.notifyObserves(btc);
        dbService.removeObserver(userDto);
        //then
        verify(mailService, times(quantity)).prepareNewCryptocurrencyMessage(any(), any());
    }
}