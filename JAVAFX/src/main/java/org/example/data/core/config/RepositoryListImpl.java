package org.example.data.core.config;

import java.util.ArrayList;
import java.util.List;

public class RepositoryListImpl<T> implements Repository<T> {
    protected List<T> list = new ArrayList<>(); 

    @Override
    public boolean insert(T objet) {
        return list.add(objet);
    }

    @Override
    public boolean update(T objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<T> selectAll() {
        return list;
    }

    @Override
    public T selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }
    
}
