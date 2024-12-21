package org.example.data.core.config;

import java.util.List;


public interface Repository<T>  {
    boolean insert(T objet);
    boolean update(T objet);
    boolean delete(int id);
    List<T> selectAll();
    T selectById(int id);
    int count();
}
