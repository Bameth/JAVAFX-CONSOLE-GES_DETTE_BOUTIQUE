package org.example.data.services;

import java.util.List;

import org.example.data.core.interfaces.DetailRepository;
import org.example.data.entities.Detail;

public class DetailServiceImpl implements DetailService {
    DetailRepository detailRepository;

    public DetailServiceImpl(DetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    @Override
    public void create(Detail objet) {
        detailRepository.insert(objet);
    }

    @Override
    public List<Detail> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public boolean update(Detail objet) {
        return detailRepository.update(objet);
    }
    @Override
    public Detail getBy(String objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBy'");
    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }
    @Override
    public Detail findById(int objet) {
        return detailRepository.selectById(objet);
    }
    
}
