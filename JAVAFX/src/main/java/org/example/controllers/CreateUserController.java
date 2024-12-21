package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import org.example.data.entities.Client;
import org.example.data.entities.User;
import org.example.data.enums.Role;
import org.example.data.services.UserServiceImpl;

public class CreateUserController {
    private UserServiceImpl userService;

    @FXML private TextField nameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField roleField;

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @FXML
    private void handleCreateUser() {
        User user = new User();
        user.setName(nameField.getText());
        user.setLogin(usernameField.getText());
        user.setPassword(passwordField.getText());
        user.setRole(Role.valueOf(roleField.getText()));
        userService.create(user);
    }

    public void setClient(Client client) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setClient'");
    }
}
