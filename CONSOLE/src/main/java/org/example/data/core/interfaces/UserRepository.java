package org.example.data.core.interfaces;

import org.example.data.core.config.Repository;
import org.example.data.entities.User;
import org.example.data.enums.TypeEtat;
import org.example.data.enums.Role;

import java.util.List;

public interface UserRepository extends Repository<User> {
    User selectByName(String name);
    List<User> selectByEtat(TypeEtat etat);
    List<User> selectByRole(Role role);
    User selectByLogin(String login);
}
