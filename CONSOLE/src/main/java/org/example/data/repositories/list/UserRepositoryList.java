package org.example.data.repositories.list;

import org.example.data.core.config.RepositoryListImpl;
import org.example.data.core.interfaces.UserRepository;
import org.example.data.entities.User;
import org.example.data.enums.TypeEtat;
import org.example.data.enums.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepositoryList extends RepositoryListImpl<User> implements UserRepository {
    @Override
    public User selectByName(String name) {
        return list.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> selectByEtat(TypeEtat etat) {
        return list.stream()
                .filter(user -> user.getTypeEtat() == etat)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> selectByRole(Role role) {
        return list.stream()
                .filter(user -> user.getRole() == role)
                .collect(Collectors.toList());
    }

    @Override
    public boolean update(User user) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == user.getId()) {
                list.set(i, user);
                return true;
            }
        }
        return false;
    }

    public User selectByLoginn(String login, String password) {
        return list.stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User selectByLogin(String login) {
        return list.stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }

}
