package org.example.data.repositories.list;

import java.util.List;
import java.util.stream.Collectors;

import org.example.data.core.config.RepositoryListImpl;
import org.example.data.core.interfaces.DeptRepository;
import org.example.data.entities.Dept;
import org.example.data.enums.EtatDette;
import org.example.data.enums.TypeDette;

public class DetteRepositoryList extends RepositoryListImpl<Dept> implements DeptRepository {

    @Override
    public List<Dept> selectByClientId(int clientId) {
        return list.stream()
                .filter(dept -> dept.getClient().getId() == clientId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dept> selectAllMyDeptNonSoldees(int clientId) {
        return list.stream()
                .filter(dept -> dept.getClient().getId() == clientId && dept.getEtat() == EtatDette.NONSOLDEES)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dept> selectAllDeptNonSoldees() {
        return list.stream()
                .filter(dept -> dept.getEtat() == EtatDette.NONSOLDEES)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dept> selectAllMyDept(int clientId) {
        return list.stream()
                .filter(dept -> dept.getClient().getId() == clientId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dept> selectByEtat(TypeDette etat) {
        return list.stream()
                .filter(dept -> dept.getTypeDette() == etat)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dept> selectAllMyDeptAnnuler(int clientId) {
        return list.stream()
                .filter(dept -> dept.getClient().getId() == clientId && dept.getTypeDette() == TypeDette.ANNULER)
                .collect(Collectors.toList());
    }
}
