package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import org.example.data.core.interfaces.ClientRepository;
import org.example.data.entities.Client;
import org.example.data.repositories.JPA.ClientRepositoryJPAImpl;
import org.example.data.services.ClientServiceImpl;
import org.example.data.services.UserServiceImpl;

import java.util.List;

public class ClientController {

    @FXML
    private TableView<Client> clientsTable;
    @FXML
    private TableColumn<Client, String> surnameColumn;
    @FXML
    private TableColumn<Client, String> phoneColumn;
    @FXML
    private TableColumn<Client, String> addressColumn;
    @FXML
    private TableColumn<Client, String> userNameColumn;
    @FXML
    private Button createClientButton;
    @FXML
    private Button editClientButton;
    @FXML
    private Button deleteClientButton;

    private ClientServiceImpl clientService;
    private ObservableList<Client> clientsList;

    // Constructor
    public ClientController() {
        ClientRepository clientRepository = new ClientRepositoryJPAImpl();
        this.clientService = new ClientServiceImpl(clientRepository);
    }

    public void initialize() {
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        userNameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        loadClients();
    }

    public void loadClients() {
        List<Client> clients = clientService.findAll();
        clientsList = FXCollections.observableArrayList(clients);
        clientsTable.setItems(clientsList);
    }

    @FXML
    private void openCreateClientDialog() {
        // Implémentez la logique pour ouvrir le dialogue de création de client
    }

    @FXML
    private void openEditClientDialog() {
        // Implémentez la logique pour ouvrir le dialogue de modification de client
    }

    @FXML
    private void deleteClient() {
        // Implémentez la logique pour supprimer un client sélectionné
    }

    public void setClientService(ClientServiceImpl clientService) {
        this.clientService = clientService;
        loadClients();
    }

    public void setUserService(UserServiceImpl userService) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUserService'");
    }

@FXML
public void handleMouseEnter() {
    System.out.println("Mouse entered");
}

@FXML
public void handleMouseExit() {
    System.out.println("Mouse exited");
}
}