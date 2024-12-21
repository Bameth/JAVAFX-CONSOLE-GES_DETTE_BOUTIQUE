package org.example.data.controllers;

import java.util.List;

import org.example.data.entities.User;
import org.example.data.enums.Role;
import org.example.data.services.UserServiceImpl;
import org.example.data.views.UserView;

public class UserController {
    private final UserServiceImpl userService;
    private final UserView userView;

    public UserController(UserServiceImpl userService, UserView userView) {
        this.userService = userService;
        this.userView = userView;
    }

    public void createUser() {
        User user = userView.saisie();
        userService.create(user);
    }

    public void changeUserStatus() {
        User user = userView.Status();
        if (user != null) {
            userService.update(user);
        }
    }

    public void displayUsersByRole() {
        Role role = userView.saisieRole();
        List<User> users = userService.findByRole(role);
        userView.displayUsers(users);
    }
}

