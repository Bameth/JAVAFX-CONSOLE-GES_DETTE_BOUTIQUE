package org.example.data.repositories.JPA;

import org.example.data.core.config.RepositoryJPAImpl;
import org.example.data.entities.Paiement;
import org.example.data.core.interfaces.PaiementRepository;

public class PaiementRepositoryJPAImpl extends RepositoryJPAImpl<Paiement> implements PaiementRepository {
    public PaiementRepositoryJPAImpl() {
        super(Paiement.class);
    }
}
