package org.example.data.Factory.Repo;

import org.example.data.core.interfaces.UserRepository;
import org.example.data.repositories.DB.UserRepositoryBd;
import org.example.data.repositories.JPA.UserRepositoryJPAImpl;
import org.example.data.repositories.list.UserRepositoryList;
import org.example.data.services.UserService;
import org.example.data.services.UserServiceImpl;

public class FactoryUser {

    private UserRepository userRepository;

    private UserService userService;

    private UserRepositoryList userRepositoryList;

    private UserRepositoryBd userRepositoryBD;

    private UserRepositoryJPAImpl userRepositoryJPAImpl;

    public FactoryUser() {
        UserRepositoryList userRepositoryList = new UserRepositoryList();
        UserRepositoryBd userRepositoryBD = new UserRepositoryBd();
        this.userRepository = new UserRepositoryJPAImpl();
        this.userRepository = userRepository;
        userService = new UserServiceImpl(userRepository);

    }
}
