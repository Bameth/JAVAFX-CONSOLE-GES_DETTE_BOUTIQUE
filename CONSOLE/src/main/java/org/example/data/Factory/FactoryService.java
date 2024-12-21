package org.example.data.Factory;

import org.example.data.core.interfaces.Service;

public interface FactoryService<T> {
    Service<T> createService();
}
