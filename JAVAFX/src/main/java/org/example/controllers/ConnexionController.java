package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.data.entities.User;
import org.example.data.enums.Role;
import org.example.data.services.ArticleServiceImpl;
import org.example.data.services.ClientServiceImpl;
import org.example.data.services.UserServiceImpl;

import java.io.IOException;

public class ConnexionController {

    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;

    private UserServiceImpl userService;
    private ClientServiceImpl clientService;
    private ArticleServiceImpl articleService;

    public void setClientService(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    public void setArticleService(ArticleServiceImpl articleService) {
        this.articleService = articleService;
    }

    @FXML
    public void connexion() {
        String loginValue = login.getText();
        String passwordValue = password.getText();

        if (loginValue.isEmpty() || passwordValue.isEmpty()) {
            showAlert("Erreur", "Login et mot de passe ne peuvent pas être vides.");
            return;
        }

        User user = userService.findByLogin(loginValue, passwordValue);

        if (user != null) {
            if (user.getRole() == Role.ADMIN) {
                openAdminView();
            } else if (user.getRole() == Role.CLIENT) {
                openClientView();
            } else if (user.getRole() == Role.BOUTIQUIER) {
                openBoutiquierView();
            } else {
                showAlert("Erreur", "Seul un administrateur peut accéder à cette section.");
            }
        } else {
            showAlert("Erreur", "Login ou mot de passe incorrect.");
        }
    }

    private void openAdminView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/adminView.fxml"));
            Parent root = loader.load();
            AdminController controller = loader.getController();
            controller.setUserService(userService);
            controller.setClientService(clientService);

            Stage stage = (Stage) login.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin - Gestion des clients");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger la vue admin.");
        }
    }

    private void openClientView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ClientView.fxml"));
            Parent root = loader.load();
            ClientController controller = loader.getController();
            controller.setUserService(userService);
            controller.setClientService(clientService);

            Stage stage = (Stage) login.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Client - Gestion des clients");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger la vue client.");
        }
    }

    private void openBoutiquierView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/BoutiquierView.fxml"));
            Parent root = loader.load();
            BoutiquierController controller = loader.getController();
            controller.initialize();

            Stage stage = (Stage) login.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Boutiquier - Gestion des clients");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger la vue Boutiquier.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleMouseEnter(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            ((Button) event.getSource())
                    .setStyle(
                            "-fx-background-color: linear-gradient(to right, #0984e3, #6c5ce7); -fx-background-radius: 12;");
        }
    }

    @FXML
    private void handleMouseExit(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            ((Button) event.getSource())
                    .setStyle(
                            "-fx-background-color: linear-gradient(to right, #6c5ce7, #0984e3); -fx-background-radius: 12;");
        }
    }

}