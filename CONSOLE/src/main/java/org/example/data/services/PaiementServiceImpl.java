package org.example.data.services;

import java.util.List;

import org.example.data.core.interfaces.PaiementRepository;
import org.example.data.entities.Paiement;

public class PaiementServiceImpl implements PaiementService{
    private PaiementRepository paiementRepository;

    public PaiementServiceImpl(PaiementRepository paiementRepository) {
        this.paiementRepository = paiementRepository;
    }

    @Override
    public void create(Paiement objet) {
        paiementRepository.insert(objet);
    }

    @Override
    public List<Paiement> findAll() {
        return paiementRepository.selectAll();
    }

    @Override
    public boolean update(Paiement objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Paiement getBy(String objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBy'");
    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }
    
}
