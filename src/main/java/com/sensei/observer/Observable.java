package com.sensei.observer;

import com.sensei.entity.Cryptocurrency;

import java.util.List;

public interface Observable {
    void registryObserver(Observer observer);
    void notifyObserves(Cryptocurrency cryptocurrency);
    void removeObserver(Observer observer);
}
