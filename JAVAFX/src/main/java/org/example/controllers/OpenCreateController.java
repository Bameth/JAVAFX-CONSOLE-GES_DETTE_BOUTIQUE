package org.example.controllers;

import java.io.IOException;

import org.example.data.entities.Client;
import org.example.data.entities.User;
import org.example.data.enums.Role;
import org.example.data.enums.TypeEtat;
import org.example.data.services.ArticleServiceImpl;
import org.example.data.services.ClientServiceImpl;
import org.example.data.services.DeptServiceImpl;
import org.example.data.services.DetailServiceImpl;
import org.example.data.services.UserServiceImpl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OpenCreateController {
    @FXML
    private TextField searchField;
    @FXML
    private Button createClientButton;
    @FXML
    private MenuBar menuBar;

    private UserServiceImpl userService;
    private ClientServiceImpl clientService;
    private ArticleServiceImpl articleService;
    private DeptServiceImpl deptService;
    private DetailServiceImpl detailService;

    public void setClientService(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    public void setArticleService(ArticleServiceImpl articleService) {
        this.articleService = articleService;
    }

    public void setDeptService(DeptServiceImpl deptService) {
        this.deptService = deptService;
    }

    public void setDetailService(DetailServiceImpl detailService) {
        this.detailService = detailService;
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    public void openCreateDeptDialog() {
        Dialog<Client> dialog = new Dialog<>();
        dialog.setTitle("Créer un nouveau client");

        TextField surnameField = new TextField();
        surnameField.setPromptText("Nom");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Téléphone");

        TextField addressField = new TextField();
        addressField.setPromptText("Adresse");

        CheckBox createUserCheckBox = new CheckBox("Créer un utilisateur");
        createUserCheckBox.setSelected(false);

        // Champs pour les détails de l'utilisateur
        TextField nameField = new TextField();
        nameField.setPromptText("Nom d'utilisateur");

        TextField loginField = new TextField();
        loginField.setPromptText("Login");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");

        ComboBox<Role> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().setAll(Role.values());
        roleComboBox.setPromptText("Choisir un rôle");

        // Masquer les champs utilisateur par défaut
        nameField.setVisible(false);
        loginField.setVisible(false);
        passwordField.setVisible(false);
        roleComboBox.setVisible(false);

        createUserCheckBox.setOnAction(event -> {
            boolean isSelected = createUserCheckBox.isSelected();
            nameField.setVisible(isSelected);
            loginField.setVisible(isSelected);
            passwordField.setVisible(isSelected);
            roleComboBox.setVisible(isSelected);
        });

        ButtonType createButtonType = new ButtonType("Créer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        VBox vbox = new VBox(10, surnameField, phoneField, addressField, createUserCheckBox,
                nameField, loginField, passwordField, roleComboBox);
        dialog.getDialogPane().setContent(vbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                Client newClient = new Client(surnameField.getText(), phoneField.getText(), addressField.getText());
                if (createUserCheckBox.isSelected()) {
                    // Créer l'utilisateur avec les informations fournies
                    User newUser = new User();
                    newUser.setName(nameField.getText());
                    newUser.setLogin(loginField.getText());
                    newUser.setPassword(passwordField.getText());
                    newUser.setRole(roleComboBox.getValue());
                    newUser.setTypeEtat(TypeEtat.ACTIVER); // État actif par défaut

                    // Associer l'utilisateur au client
                    newClient.setUser(newUser);
                    userService.create(newUser); // Enregistrer l'utilisateur
                }
                return newClient;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(client -> {
            clientService.create(client);
        });
    }

    public void openCreateClientDialog() {
        Dialog<Client> dialog = new Dialog<>();
        dialog.setTitle("Créer un nouveau client");

        TextField surnameField = new TextField();
        surnameField.setPromptText("Nom");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Téléphone");

        TextField addressField = new TextField();
        addressField.setPromptText("Adresse");

        CheckBox createUserCheckBox = new CheckBox("Créer un utilisateur");
        createUserCheckBox.setSelected(false);

        // Champs pour les détails de l'utilisateur
        TextField nameField = new TextField();
        nameField.setPromptText("Nom d'utilisateur");

        TextField loginField = new TextField();
        loginField.setPromptText("Login");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");

        ComboBox<Role> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().setAll(Role.values());
        roleComboBox.setPromptText("Choisir un rôle");

        // Masquer les champs utilisateur par défaut
        nameField.setVisible(false);
        loginField.setVisible(false);
        passwordField.setVisible(false);
        roleComboBox.setVisible(false);

        createUserCheckBox.setOnAction(event -> {
            boolean isSelected = createUserCheckBox.isSelected();
            nameField.setVisible(isSelected);
            loginField.setVisible(isSelected);
            passwordField.setVisible(isSelected);
            roleComboBox.setVisible(isSelected);
        });

        ButtonType createButtonType = new ButtonType("Créer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        VBox vbox = new VBox(10, surnameField, phoneField, addressField, createUserCheckBox,
                nameField, loginField, passwordField, roleComboBox);
        dialog.getDialogPane().setContent(vbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                Client newClient = new Client(surnameField.getText(), phoneField.getText(), addressField.getText());
                if (createUserCheckBox.isSelected()) {
                    // Créer l'utilisateur avec les informations fournies
                    User newUser = new User();
                    newUser.setName(nameField.getText());
                    newUser.setLogin(loginField.getText());
                    newUser.setPassword(passwordField.getText());
                    newUser.setRole(roleComboBox.getValue());
                    newUser.setTypeEtat(TypeEtat.ACTIVER); // État actif par défaut

                    // Associer l'utilisateur au client
                    newClient.setUser(newUser);
                    userService.create(newUser); // Enregistrer l'utilisateur
                }
                return newClient;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(client -> {
            clientService.create(client);
            ClientController clientController = new ClientController();
            clientController.loadClients();
        });
    }

    @FXML
    private void openCreateUserDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/createUserView.fxml"));
            Parent root = loader.load();
            CreateUserController controller = loader.getController();
            controller.setUserService(userService);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Créer un Compte Utilisateur");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
