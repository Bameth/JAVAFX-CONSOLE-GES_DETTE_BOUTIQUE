package org.example.data.Factory;

import org.example.data.core.config.Repository;

public interface FactoryRepository<T> {
    Repository<T> createRepository();
}
