package org.example.data.core.interfaces;

import java.util.List;

public interface View<T> {
    T saisie();

    void affiche(List<T> datas);
}
