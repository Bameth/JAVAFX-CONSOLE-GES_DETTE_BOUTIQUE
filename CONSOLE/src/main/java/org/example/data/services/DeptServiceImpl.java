package org.example.data.services;

import java.util.List;

import org.example.data.core.interfaces.DeptRepository;
import org.example.data.entities.Dept;
import org.example.data.enums.TypeDette;

public class DeptServiceImpl implements DeptService {
    private DeptRepository deptRepository;

    public DeptServiceImpl(DeptRepository deptRepository) {
        this.deptRepository = deptRepository;
    }

    @Override
    public void create(Dept objet) {
        if (objet == null) {
            System.err.println("Erreur: l'objet Dept est nul, insertion impossible.");
            return;
        }
        deptRepository.insert(objet);
    }

    @Override
    public List<Dept> findAll() {
        return deptRepository.selectAll();
    }

    @Override
    public List<Dept> findAllDeptNonSoldees() {
        return deptRepository.selectAllDeptNonSoldees();
    }

    @Override
    public boolean update(Dept objet) {
        return deptRepository.update(objet);
    }

    @Override
    public Dept getBy(String objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBy'");
    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public Dept findById(int id) {
        return deptRepository.selectById(id);
    }

    public List<Dept> findDebtsByClientId(int clientId) {
        return deptRepository.selectByClientId(clientId);
    }

    public void delete(int id) {
        deptRepository.delete(id);
    }

    @Override
    public List<Dept> findAllMyDeptNonSoldees(int clientId) {
        return deptRepository.selectAllMyDeptNonSoldees(clientId);
    }

    @Override
    public List<Dept> findAllMyDebts(int id) {
        return deptRepository.selectAllMyDept(id);
    }

    @Override
    public List<Dept> findByEtat(TypeDette etat) {
       return deptRepository.selectByEtat(etat);
    }

    @Override
    public List<Dept> findCanceledDebtsByClientId(int clientId) {
        return deptRepository.selectAllMyDeptAnnuler(clientId);
    }    
}
