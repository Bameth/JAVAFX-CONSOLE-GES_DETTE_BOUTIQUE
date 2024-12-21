package org.example.data.core.interfaces;

import org.example.data.entities.Dept;
import org.example.data.entities.User;
import org.example.data.enums.EtatDette;
import org.example.data.enums.TypeDette;

import java.util.List;

import org.example.data.core.config.Repository;

public interface DeptRepository extends Repository<Dept> {
    List<Dept> selectByClientId(int clientId);
    List<Dept> selectAllDeptNonSoldees();
    List<Dept> selectAllMyDeptNonSoldees(int clientId);
    List<Dept> selectAllMyDept(int clientId);
    List<Dept> selectByEtat(TypeDette etat);
    List<Dept> selectAllMyDeptAnnuler(int clientId);
}
