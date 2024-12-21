package org.example.data.controllers;

import java.util.List;
import java.util.Scanner;
import org.example.data.entities.Dept;
import org.example.data.services.ArticleServiceImpl;
import org.example.data.services.ClientServiceImpl;
import org.example.data.services.DeptService;
import org.example.data.services.DeptServiceImpl;
import org.example.data.views.DeptView;

public class DeptController {
    private DeptService deptService;
    private DeptView deptView;
    private ClientServiceImpl clientService;
    private ArticleServiceImpl articleService;
    private Scanner scanner;

    public DeptController(DeptService deptService, Scanner scanner,ClientServiceImpl clientService) {
        this.deptService = deptService;
        this.clientService = clientService;
        this.scanner = scanner;
        this.deptView = new DeptView(scanner, clientService, (DeptServiceImpl) deptService, (ArticleServiceImpl)articleService, null);
    }

    public void createDept() {
        Dept newDept = deptView.saisie();
        if (newDept != null) {
            deptService.create(newDept);
            System.out.println("Dette créée avec succès.");
        }
    }

    public void listDepts() {
        List<Dept> depts = deptService.findAll();
        for (Dept dept : depts) {
            System.out.println(dept);
        }
    }
}
