package org.example.data.repositories.list;

import java.util.List;

import org.example.data.core.config.RepositoryListImpl;
import org.example.data.core.interfaces.DeptRepository;
import org.example.data.entities.Dept;
import org.example.data.entities.User;
import org.example.data.enums.EtatDette;
import org.example.data.enums.TypeDette;

public class DetteRepositoryListimpl extends RepositoryListImpl<Dept> implements DeptRepository {

    @Override
    public List<Dept> selectByClientId(int clientId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectByClientId'");
    }

    @Override
    public List<Dept> selectAllMyDeptNonSoldees(int clientId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectAllDeptNonSoldees'");
    }

    @Override
    public List<Dept> selectAllDeptNonSoldees() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectAllDeptNonSoldees'");
    }

    @Override
    public List<Dept> selectAllMyDept(int clientId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectAllMyDept'");
    }

    @Override
    public List<Dept> selectByEtat(TypeDette etat) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectByEtat'");
    }

    @Override
    public List<Dept> selectAllMyDeptAnnuler(int clientId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectAllMyDeptAnnuler'");
    }

}