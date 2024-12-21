package org.example.data.services;

import java.util.List;

import org.example.data.entities.User;
import org.example.data.core.interfaces.UserRepository;
import org.example.data.enums.TypeEtat;
import org.example.data.enums.Role;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public void create(User user) {
        userRepository.insert(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.selectAll();
    }

    @Override
    public boolean update(User user) {
        return userRepository.update(user);
    }

    @Override
    public User getBy(String value) {
        return userRepository.selectByName(value);
    }

    @Override
    public List<User> findByEtat(TypeEtat etat) {
        return userRepository.selectByEtat(etat);
    }

    @Override
    public List<User> findByRole(Role role) {
        return userRepository.selectByRole(role);
    }

    @Override
    public int count() {
        return userRepository.count();
    }

    @Override
    public User findByLogin(String login,String password) {
        User user = userRepository.selectByLogin(login);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
