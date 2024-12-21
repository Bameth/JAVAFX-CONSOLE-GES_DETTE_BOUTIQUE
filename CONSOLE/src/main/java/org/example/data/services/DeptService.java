package org.example.data.services;

import java.util.List;

import org.example.data.core.interfaces.Service;
import org.example.data.entities.Dept;
import org.example.data.enums.TypeDette;

public interface DeptService extends Service<Dept> {
    Dept findById(int id);
    List<Dept> findDebtsByClientId(int clientId);
    List<Dept> findAllDeptNonSoldees();
    List<Dept> findAllMyDeptNonSoldees(int clientId);
    List<Dept> findAllMyDebts(int id);
    List<Dept> findByEtat(TypeDette etat);
    public List<Dept> findCanceledDebtsByClientId(int clientId);
}
