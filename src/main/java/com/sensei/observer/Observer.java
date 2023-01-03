package com.sensei.observer;

import com.sensei.entity.Cryptocurrency;

public interface Observer {
    void update(Cryptocurrency cryptocurrency);
    String getEmail();
}
