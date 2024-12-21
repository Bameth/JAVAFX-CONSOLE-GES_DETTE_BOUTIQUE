package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import org.example.data.entities.Client;
import org.example.data.entities.User;
import org.example.data.enums.Role;
import org.example.data.enums.TypeEtat;
import org.example.data.services.ClientServiceImpl;
import org.example.data.services.UserService;
import org.example.data.services.UserServiceImpl;

import java.util.List;

public class UserController {

    @FXML
    private TextField surnameField, phoneField, addressField, nameField, loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<Role> roleComboBox;
    @FXML
    private CheckBox createUserCheckBox;
    private UserServiceImpl userService;
    private ClientServiceImpl clientService;
    private ObservableList<User> usersList;
    private ObservableList<Client> clientsList;

    public void loadClients() {
        List<Client> clients = clientService.findAll();
        clientsList = FXCollections.observableArrayList(clients);
    }

    @FXML
    private void initialize() {
        createUserCheckBox.setOnAction(event -> {
            boolean isSelected = createUserCheckBox.isSelected();
            nameField.setVisible(isSelected);
            loginField.setVisible(isSelected);
            passwordField.setVisible(isSelected);
            roleComboBox.setVisible(isSelected);
        });
    }

    @FXML
    private void handleCreateClient() {
        if (surnameField.getText().isEmpty() || phoneField.getText().isEmpty() || addressField.getText().isEmpty()) {
            showError("Champs manquants", "Veuillez remplir tous les champs obligatoires");
            return;
        }

        Client newClient = new Client(surnameField.getText(), phoneField.getText(), addressField.getText());

        if (createUserCheckBox.isSelected()) {
            if (nameField.getText().isEmpty() || loginField.getText().isEmpty() || passwordField.getText().isEmpty()
                    || roleComboBox.getValue() == null) {
                showError("Champs utilisateur manquants", "Veuillez remplir tous les champs utilisateur");
                return;
            }

            User newUser = new User();
            newUser.setName(nameField.getText());
            newUser.setLogin(loginField.getText());
            newUser.setPassword(passwordField.getText());
            newUser.setRole(roleComboBox.getValue());
            newUser.setTypeEtat(TypeEtat.ACTIVER);

            newClient.setUser(newUser);
            userService.create(newUser);
        }

        clientService.create(newClient);
        loadClients();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

}
