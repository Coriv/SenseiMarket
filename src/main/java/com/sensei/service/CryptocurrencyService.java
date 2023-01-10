package com.sensei.service;

import com.sensei.dto.UserDto;
import com.sensei.entity.Cryptocurrency;
import com.sensei.exception.CryptoIsObjectOfTradingException;
import com.sensei.exception.CryptocurrencyNotFoundException;
import com.sensei.mailService.MailService;
import com.sensei.mapper.UserMapper;
import com.sensei.observer.Observable;
import com.sensei.observer.Observer;
import com.sensei.repository.CryptocurrencyDao;
import com.sensei.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptocurrencyService implements Observable {

    private final CryptocurrencyDao cryptocurrencyDao;
    private final UserDao userDao;
    private final UserMapper userMapper;
    private final MailService mailService;
    private List<Observer> observers = new ArrayList<>();

    public Cryptocurrency findCryptocurrencyBySymbol(String symbol) throws CryptocurrencyNotFoundException {
        return cryptocurrencyDao.findBySymbol(symbol).orElseThrow(CryptocurrencyNotFoundException::new);
    }

    public List<Cryptocurrency> findAll() {
        return cryptocurrencyDao.findAll();
    }

    public Cryptocurrency add(Cryptocurrency cryptocurrency) {
        if(observers.size() == 0) {
            updateObserversList();
        }
        var savedCryptocurrency = cryptocurrencyDao.save(cryptocurrency);
        notifyObserves(savedCryptocurrency);
        return savedCryptocurrency;
    }

    public void deleteBySymbol(String symbol) throws CryptocurrencyNotFoundException, CryptoIsObjectOfTradingException {
        Cryptocurrency crypto = cryptocurrencyDao.findBySymbol(symbol).orElseThrow(CryptocurrencyNotFoundException::new);
        if (!crypto.getWalletCryptoList().isEmpty()) {
            throw new CryptoIsObjectOfTradingException();
        }
        cryptocurrencyDao.deleteBySymbol(symbol);
    }

    private void updateObserversList() {
        List<UserDto> observers = userMapper.mapToUserDtoList(userDao.findAll());
        observers.stream()
                .filter(user -> user.isNotification() == true)
                .forEach(this::registryObserver);
    }

    @Override
    public void registryObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObserves(Cryptocurrency cryptocurrency) {
        for (Observer observer : observers) {
            mailService.prepareNewCryptocurrencyMessage(observer.getEmail(), cryptocurrency);
            observer.update(cryptocurrency);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public List<Observer> getObservers() {
        return observers;
    }
}
