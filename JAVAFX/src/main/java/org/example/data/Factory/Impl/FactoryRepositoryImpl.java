package org.example.data.Factory.Impl;

import org.example.data.Factory.FactoryRepository;
import org.example.data.core.config.*;
import org.example.data.entities.*;
import org.example.data.repositories.DB.*;
import org.example.data.repositories.JPA.*;
import org.example.data.repositories.list.*;

public class FactoryRepositoryImpl<T> implements FactoryRepository<T> {
    private final Repository<T> repository;
    private static final ConfigYaml configYaml = new ConfigYaml();

    public FactoryRepositoryImpl(Class<T> clazz) {
        String repositoryType = getRepositoryType(clazz);
        this.repository = createRepository(clazz, repositoryType);
    }

    private String getRepositoryType(Class<T> clazz) {
        if (Client.class.isAssignableFrom(clazz)) {
            return configYaml.getRepositoryType("client");
        } else if (User.class.isAssignableFrom(clazz)) {
            return configYaml.getRepositoryType("user");
        } else if (Article.class.isAssignableFrom(clazz)) {
            return configYaml.getRepositoryType("article");
        } else if (Dept.class.isAssignableFrom(clazz)) {
            return configYaml.getRepositoryType("dept");
        } else if (Detail.class.isAssignableFrom(clazz)) {
            return configYaml.getRepositoryType("detail");
        }
        throw new IllegalArgumentException("Unsupported entity type: " + clazz.getSimpleName());
    }

    private Repository<T> createRepository(Class<T> clazz, String repositoryType) {
        switch (repositoryType) {
            case "JPA":
                return createJPARepository(clazz);
            case "DB":
                return createDBRepository(clazz);
            case "LIST":
                return createListRepository(clazz);
            default:
                throw new IllegalArgumentException("Unsupported repository type: " + repositoryType);
        }
    }

    @SuppressWarnings("unchecked")
    private Repository<T> createJPARepository(Class<T> clazz) {
        if (Client.class.isAssignableFrom(clazz)) {
            return (Repository<T>) new ClientRepositoryJPAImpl();
        } else if (User.class.isAssignableFrom(clazz)) {
            return (Repository<T>) new UserRepositoryJPAImpl();
        } else if (Article.class.isAssignableFrom(clazz)) {
            return (Repository<T>) new ArticleRepositoryJPAImpl();
        } else if (Dept.class.isAssignableFrom(clazz)) {
            return (Repository<T>) new DeptRepositoryJPAImpl();
        } else if (Detail.class.isAssignableFrom(clazz)) {
            return (Repository<T>) new DetailRepositoryJPAImpl();
        }
        throw new IllegalArgumentException("Unsupported entity type for JPA: " + clazz.getSimpleName());
    }

    @SuppressWarnings("unchecked")
    private Repository<T> createDBRepository(Class<T> clazz) {
        if (Client.class.isAssignableFrom(clazz)) {
            return (Repository<T>) new ClientRepositoryBd();
        } else if (User.class.isAssignableFrom(clazz)) {
            return (Repository<T>) new UserRepositoryBd();
        } else if (Article.class.isAssignableFrom(clazz)) {
            return (Repository<T>) new ArticleRepositoryBd();
        } else if (Dept.class.isAssignableFrom(clazz)) {
            return (Repository<T>) new DeptRepositoryBd();
        } else if (Detail.class.isAssignableFrom(clazz)) {
            return (Repository<T>) new DetailRepositoryBd();
        }
        throw new IllegalArgumentException("Unsupported entity type for DB: " + clazz.getSimpleName());
    }

    @SuppressWarnings("unchecked")
    private Repository<T> createListRepository(Class<T> clazz) {
        if (Client.class.isAssignableFrom(clazz)) {
            return (Repository<T>) new ClientRepositoryList();
        } else if (User.class.isAssignableFrom(clazz)) {
            return (Repository<T>) new UserRepositoryList();
        } else if (Article.class.isAssignableFrom(clazz)) {
            return (Repository<T>) new ArticleRepositoryList();
        }
        throw new IllegalArgumentException("Unsupported entity type for LIST: " + clazz.getSimpleName());
    }

    @Override
    public Repository<T> createRepository() {
        return repository;
    }
}