package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.data.entities.User;
import org.example.data.services.ClientServiceImpl;
import org.example.data.services.UserService;
import org.example.data.services.UserServiceImpl;

import java.util.List;

public class UsersController {

    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> loginColumn;
    @FXML
    private TableColumn<User, String> roleColumn;
    @FXML
    private Button createUserButton;
    @FXML
    private Button editUserButton;
    @FXML
    private Button deleteUserButton;

    private UserService userService;
    private ObservableList<User> usersList;

    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        loginColumn.setCellValueFactory(cellData -> cellData.getValue().loginProperty());
        roleColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
        loadUsers();
    }

    public void loadUsers() {
        List<User> users = userService.findAll();
        usersList = FXCollections.observableArrayList(users);
        usersTable.setItems(usersList);
    }

    @FXML
    private void openCreateUserDialog() {
        // Implémentez la logique pour ouvrir le dialogue de création d'utilisateur
    }

    @FXML
    private void openEditUserDialog() {
        // Implémentez la logique pour ouvrir le dialogue de modification d'utilisateur
    }

    @FXML
    private void deleteUser() {
        // Implémentez la logique pour supprimer un utilisateur sélectionné
    }

    public void setUserService(UserServiceImpl userService2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUserService'");
    }

    public void setClientService(ClientServiceImpl clientService) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setClientService'");
    }
}
