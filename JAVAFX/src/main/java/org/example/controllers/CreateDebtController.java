package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.example.data.entities.Dept;
import org.example.data.enums.EtatDette;

import java.util.stream.Collectors;

import org.example.data.entities.Client;
import org.example.data.services.DeptServiceImpl;

public class CreateDebtController {

    @FXML
    private ComboBox<Client> clientComboBox;

    @FXML
    private TextField amountField;

    @FXML
    private ComboBox<String> debtStateComboBox;

    @FXML
    private Button createDebtButton;

    private DeptServiceImpl deptService;

    public void setDeptService(DeptServiceImpl deptService) {
        this.deptService = deptService;
    }

    @FXML
    public void initialize() {
        // Charger les clients dans le ComboBox
        loadClients();

        // Initialiser les états de la dette
        debtStateComboBox.getItems().addAll("Non payé", "Payé", "En retard");
        debtStateComboBox.setValue("Non payé");

        // Gestion de l'action du bouton "Créer la dette"
        createDebtButton.setOnAction(event -> handleCreateDebt());
    }

    private void loadClients() {
        // Charger la liste des clients dans le ComboBox
        // Par exemple, on pourrait récupérer la liste des clients via un service
        clientComboBox.getItems()
                .setAll(deptService.findAll().stream().map(Dept::getClient).collect(Collectors.toList()));
    }

    private void handleCreateDebt() {
        try {
            // Vérifier les entrées
            Client client = clientComboBox.getValue();
            if (client == null || amountField.getText().isEmpty()) {
                showError("Erreur", "Tous les champs doivent être remplis.");
                return;
            }

            // Récupérer les informations saisies
            double amount = Double.parseDouble(amountField.getText());
            String state = debtStateComboBox.getValue();

            // Créer la dette
            Dept dept = new Dept();
            dept.setClient(client);
            dept.setMontant(amount);
            dept.setEtat(EtatDette.valueOf(state)); // Assuming EtatDette is an enum
            deptService.create(dept);

            // Message de succès
            showSuccess("Succès", "La dette a été enregistrée avec succès.");

            // Fermer la fenêtre après l'enregistrement
            createDebtButton.getScene().getWindow().hide();
        } catch (NumberFormatException e) {
            showError("Erreur", "Le montant doit être un nombre valide.");
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
