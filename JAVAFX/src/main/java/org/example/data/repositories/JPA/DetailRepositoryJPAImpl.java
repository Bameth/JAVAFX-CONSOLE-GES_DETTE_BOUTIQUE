package org.example.data.repositories.JPA;

import org.example.data.core.config.RepositoryJPAImpl;
import org.example.data.core.interfaces.DetailRepository;
import org.example.data.entities.Detail;

public class DetailRepositoryJPAImpl extends RepositoryJPAImpl<Detail> implements DetailRepository {

    public DetailRepositoryJPAImpl() {
        super(Detail.class);
    }
    
}
