package org.example.data.repositories.list;

import org.example.data.entities.User;
import org.example.data.enums.TypeEtat;
import org.example.data.core.config.RepositoryListImpl;
import org.example.data.core.interfaces.UserRepository;
import org.example.data.enums.Role;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryList extends RepositoryListImpl<User> implements UserRepository {
    @Override
    public User selectByName(String name) {
        for (User user : list) { 
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> selectByEtat(TypeEtat etat) {
        List<User> filteredUsers = new ArrayList<>();
        for (User user : list) {
            if (user.getTypeEtat() == etat) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }

    @Override
    public List<User> selectByRole(Role role) {
        List<User> filteredUsers = new ArrayList<>();
        for (User user : list) {
            if (user.getRole() == role) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }
    @Override
    public boolean update(User user) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(user.getName())) {
                list.set(i, user);
                return true;
            }
        }
        return false;
    }

    @Override
    public User selectByLogin(String login) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectByLogin'");
    }
}
