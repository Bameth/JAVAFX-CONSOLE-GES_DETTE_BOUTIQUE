package org.example.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import org.example.data.entities.Client;
import org.example.data.entities.Dept;
import org.example.data.enums.EtatDette;
import org.example.data.enums.TypeDette;
import org.example.data.services.ClientServiceImpl;
import org.example.data.services.DeptServiceImpl;
import org.example.data.services.UserServiceImpl;

import java.io.IOException;
import java.util.List;

public class BoutiquierController {

    @FXML
    private TableView<Client> clientsTable;
    @FXML
    private TableColumn<Client, String> surnameColumn;
    @FXML
    private TableColumn<Client, String> phoneColumn;
    @FXML
    private TableColumn<Client, String> addressColumn;
    @FXML
    private TableColumn<Client, String> userColumn;
    @FXML
    private TableView<Dept> debtsTable;
    @FXML
    private TableColumn<Dept, String> clientColumn;
    @FXML
    private TableColumn<Dept, String> amountColumn;
    @FXML
    private TableColumn<Dept, String> stateColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Button createClientButton;
    @FXML
    private Button registerDebtButton;

    private ClientServiceImpl clientService;
    private UserServiceImpl userService;
    private DeptServiceImpl deptService;

    private ObservableList<Client> clientsList;
    private ObservableList<Dept> debtsList;

    public BoutiquierController(ClientServiceImpl clientService, UserServiceImpl userService,
            DeptServiceImpl deptService) {
        this.clientService = clientService;
        this.userService = userService;
        this.deptService = deptService;
    }

    @FXML
    public void initialize() {
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        userColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserName()));

        clientColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getClient().getSurname()));
        amountColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMontant())));
        stateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEtat().toString()));

        loadClients();
        loadDebts();

        createClientButton.setOnAction(event -> openCreateClientDialog());
        registerDebtButton.setOnAction(event -> openRegisterDebtDialog());
    }

    private void loadClients() {
        List<Client> clients = clientService.findAll();
        clientsList = FXCollections.observableArrayList(clients);
        clientsTable.setItems(clientsList);
    }

    private void loadDebts() {
        List<Dept> debts = deptService.findAll();
        debtsList = FXCollections.observableArrayList(debts);
        debtsTable.setItems(debtsList);
    }

    @FXML
    private void filterClients() {
        FilteredList<Client> filteredData = new FilteredList<>(clientsList, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(client -> {
                if (newValue == null || newValue.isEmpty())
                    return true;
                String lowerCaseFilter = newValue.toLowerCase();
                return client.getPhone().toLowerCase().contains(lowerCaseFilter);
            });
        });
        clientsTable.setItems(filteredData);
    }

    @FXML
    private void openCreateClientDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/createClientDialog.fxml"));
            Parent root = loader.load();
            CreateClientController controller = loader.getController();
            controller.setClientService(clientService); // Pass the necessary service
            Stage stage = new Stage();
            stage.setTitle("Créer un Client");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openRegisterDebtDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/createDebtDialog.fxml"));
            Parent root = loader.load();
            CreateDebtController controller = loader.getController();
            controller.setDeptService(deptService); // Pass the necessary service
            Stage stage = new Stage();
            stage.setTitle("Enregistrer une Dette");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToClients() {
        // Navigation vers l'écran de gestion des clients
    }

    @FXML
    private void navigateToDebts() {
        // Navigation vers l'écran de gestion des dettes
    }

    @FXML
    private void markDebtAsPaid(Dept dept) {
        dept.setEtat(EtatDette.SOLDEES);
        deptService.update(dept);
        loadDebts();
    }

    @FXML
    private void filterUnpaidDebts() {
        FilteredList<Dept> unpaidDebts = new FilteredList<>(debtsList, dept -> dept.getEtat() == EtatDette.NONSOLDEES);
        debtsTable.setItems(unpaidDebts);
    }

    @FXML
    private void filterDebtRequests() {
        FilteredList<Dept> debtRequests = new FilteredList<>(debtsList,
                dept -> dept.getTypeDette() == TypeDette.ENCOURS);
        debtsTable.setItems(debtRequests);
    }
}
