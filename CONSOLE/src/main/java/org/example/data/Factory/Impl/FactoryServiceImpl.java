package org.example.data.Factory.Impl;

import org.example.data.Factory.FactoryService;
import org.example.data.core.config.*;
import org.example.data.core.interfaces.*;
import org.example.data.entities.*;
import org.example.data.services.*;

public class FactoryServiceImpl<T> implements FactoryService<T> {
    private final Service<T> service;
    private final Repository<T> repository;

    @SuppressWarnings("unchecked")
    public FactoryServiceImpl(Class<T> clazz, Repository<T> repository) {
        this.repository = repository;
        if (Client.class.isAssignableFrom(clazz)) {
            this.service = (Service<T>) new ClientServiceImpl((ClientRepository) repository);
        } else if (User.class.isAssignableFrom(clazz)) {
            this.service = (Service<T>) new UserServiceImpl((UserRepository) repository);
        } else if (Article.class.isAssignableFrom(clazz)) {
            this.service = (Service<T>) new ArticleServiceImpl((ArticleRepository) repository);
        } else if (Dept.class.isAssignableFrom(clazz)) {
            this.service = (Service<T>) new DeptServiceImpl((DeptRepository) repository);
        } else if (Detail.class.isAssignableFrom(clazz)) {
            this.service = (Service<T>) new DetailServiceImpl((DetailRepository) repository);
        } else if (Paiement.class.isAssignableFrom(clazz)) {
            this.service = (Service<T>) new PaiementServiceImpl((PaiementRepository) repository);
        }else {
            throw new IllegalArgumentException("Unsupported entity type: " + clazz.getName());
        }
    }

    @Override
    public Service<T> createService() {
        return service;
    }
}
