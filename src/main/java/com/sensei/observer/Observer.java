package com.sensei.observer;

import com.sensei.cryptocurrency.Cryptocurrency;

public interface Observer {
    void update(Cryptocurrency cryptocurrency);
    String getEmail();
}
