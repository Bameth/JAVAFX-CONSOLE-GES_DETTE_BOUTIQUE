package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.example.data.entities.Client;
import org.example.data.entities.User;
import org.example.data.services.ClientServiceImpl;
import org.example.data.services.UserServiceImpl;

public class CreateClientController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField usernameField;

    @FXML
    private Button createClientButton;

    private ClientServiceImpl clientService;
    private UserServiceImpl userService;

    public void setClientService(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @FXML
    public void initialize() {
        createClientButton.setOnAction(event -> handleCreateClient());
    }

    private void handleCreateClient() {
        try {
            // Vérification des champs
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            String phone = phoneField.getText().trim();
            String username = usernameField.getText().trim();

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || username.isEmpty()) {
                showError("Erreur", "Tous les champs doivent être remplis.");
                return;
            }

            // Créer un utilisateur
            User user = new User();
            user.setLogin(username);
            // Set other fields as needed
            userService.create(user);

            // Créer un client
            Client client = new Client(name, address, phone, user);
            clientService.create(client);

            // Afficher un message de succès
            showSuccess("Succès", "Le client a été créé avec succès.");

            // Fermer la fenêtre après l'enregistrement
            createClientButton.getScene().getWindow().hide();
        } catch (Exception e) {
            showError("Erreur", "Une erreur est survenue lors de la création du client.");
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
