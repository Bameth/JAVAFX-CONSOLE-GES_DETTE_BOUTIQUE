package org.example.data.Factory;

import org.example.data.core.interfaces.View;

public interface FactoryView<T> {
    View<T> createView();
}
