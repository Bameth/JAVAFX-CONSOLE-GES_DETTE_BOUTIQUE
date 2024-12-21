package org.example.data.services;

import java.util.List;

import org.example.data.core.interfaces.Service;
import org.example.data.entities.User;
import org.example.data.enums.Role;
import org.example.data.enums.TypeEtat;


public interface UserService extends Service<User> {
    List<User> findByEtat(TypeEtat etat);

    List<User> findByRole(Role role);

    User findByLogin(String login,String password);

}
