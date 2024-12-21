package org.example.data.core.interfaces;

import java.util.List;

public interface Service<T> {
    void create(T objet);
    List<T> findAll();
    boolean update(T objet);
    T getBy(String objet);
    int count();
}
