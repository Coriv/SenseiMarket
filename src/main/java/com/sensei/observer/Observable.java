package com.sensei.observer;

import com.sensei.cryptocurrency.Cryptocurrency;

public interface Observable {
    void registryObserver(Observer observer);
    void notifyObserves(Cryptocurrency cryptocurrency);
    void removeObserver(Observer observer);
}
