package com.sensei.config;

public class Prototype<T> implements Cloneable {
    @Override
    protected T clone() throws CloneNotSupportedException {
        return (T)super.clone();
    }
}
