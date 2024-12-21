package org.example.data.Factory.Repo;

import org.example.data.core.interfaces.DeptRepository;
import org.example.data.repositories.DB.ClientRepositoryBd;
import org.example.data.repositories.DB.DeptRepositoryBd;
import org.example.data.repositories.DB.UserRepositoryBd;
import org.example.data.repositories.JPA.DeptRepositoryJPAImpl;
import org.example.data.repositories.JPA.UserRepositoryJPAImpl;
import org.example.data.repositories.list.UserRepositoryList;
import org.example.data.services.DeptService;
import org.example.data.services.DeptServiceImpl;
import org.example.data.services.UserServiceImpl;

public class FactoryDept {
    private DeptRepository deptRepository;
    private DeptService deptService;
    private DeptRepositoryBd deptRepositoryBd;
    private DeptRepositoryJPAImpl deptRepositoryJPAImpl;
    
    // Constructeur pour les repos JPA
    public FactoryDept() {
        DeptRepositoryBd deptRepositoryBd = new DeptRepositoryBd();
        this.deptRepository = (DeptRepository) new DeptRepositoryJPAImpl();
        this.deptRepository = deptRepository;
        deptService = new DeptServiceImpl(deptRepository);

    }

}
