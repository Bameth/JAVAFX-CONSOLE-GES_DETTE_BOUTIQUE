package org.example.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

import org.example.data.core.interfaces.ArticleRepository;
import org.example.data.core.interfaces.ClientRepository;
import org.example.data.core.interfaces.UserRepository;
import org.example.data.entities.Article;
import org.example.data.entities.Client;
import org.example.data.entities.User;
import org.example.data.enums.Role;
import org.example.data.enums.TypeEtat;
import org.example.data.repositories.JPA.ArticleRepositoryJPAImpl;
import org.example.data.repositories.JPA.ClientRepositoryJPAImpl;
import org.example.data.repositories.JPA.UserRepositoryJPAImpl;
import org.example.data.services.ArticleServiceImpl;
import org.example.data.services.ClientServiceImpl;
import org.example.data.services.UserServiceImpl;
import java.util.List;

public class AdminController {

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
    private TextField searchField;
    @FXML
    private Button createClientButton;
    @FXML
    private MenuBar menuBar;

    private UserServiceImpl userService;
    private ClientServiceImpl clientService;
    private ArticleServiceImpl articleService;
    private ObservableList<Client> clientsList;

    public AdminController() {
        ClientRepository clientRepository = new ClientRepositoryJPAImpl();
        UserRepository userRepository = new UserRepositoryJPAImpl();
        ArticleRepository articleRepository = new ArticleRepositoryJPAImpl();
        this.articleService = new ArticleServiceImpl(articleRepository);
        this.clientService = new ClientServiceImpl(clientRepository);
        this.userService = new UserServiceImpl(userRepository);
    }

    public AdminController(ClientServiceImpl clientService, UserServiceImpl userService,
            ArticleServiceImpl articleService) {
        this.clientService = clientService;
        this.userService = userService;
        this.articleService = articleService;
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    public void setArticleService(ArticleServiceImpl articleService) {
        this.articleService = articleService;
    }

    public void setClientService(ClientServiceImpl clientService) {
        this.clientService = clientService;
        loadClients();
    }

    public void loadClients() {
        List<Client> clients = clientService.findAll();
        clientsList = FXCollections.observableArrayList(clients);
        clientsTable.setItems(clientsList);
    }

    @FXML
    public void initialize() {
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        userColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserName()));

        createClientButton.setOnAction(event -> openCreateClientDialog());
        loadClients();
        filterClients();
    }

    @FXML
    private void filterClients() {
        FilteredList<Client> filteredData = new FilteredList<>(clientsList, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(client -> {
                if (newValue == null || newValue.isEmpty())
                    return true;
                String lowerCaseFilter = newValue.toLowerCase();
                return client.getSurname().toLowerCase().contains(lowerCaseFilter)
                        || client.getPhone().toLowerCase().contains(lowerCaseFilter);
            });
        });
        clientsTable.setItems(filteredData);
    }

    private void openCreateClientDialog() {
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

        TextField nameField = new TextField();
        nameField.setPromptText("Nom d'utilisateur");

        TextField loginField = new TextField();
        loginField.setPromptText("Login");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");

        ComboBox<Role> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().setAll(Role.values());
        roleComboBox.setPromptText("Choisir un rôle");

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
                if (surnameField.getText().isEmpty() || phoneField.getText().isEmpty()
                        || addressField.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Champs manquants");
                    alert.setHeaderText("Veuillez remplir tous les champs obligatoires");
                    alert.showAndWait();
                    return null;
                }

                Client newClient = new Client(surnameField.getText(), phoneField.getText(), addressField.getText());

                if (createUserCheckBox.isSelected()) {
                    if (nameField.getText().isEmpty() || loginField.getText().isEmpty()
                            || passwordField.getText().isEmpty() || roleComboBox.getValue() == null) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Champs utilisateur manquants");
                        alert.setHeaderText("Veuillez remplir tous les champs utilisateur");
                        alert.showAndWait();
                        return null;
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

                return newClient;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(client -> {
            clientService.create(client);
            loadClients();
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

    @FXML
    private void navigateToArticles() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/articlesView.fxml"));
            Parent root = loader.load();

            ArticlesController controller = loader.getController();
            controller.setArticleService(articleService);

            Scene scene = clientsTable.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // @FXML
    // private void navigateToDept() {
    // try {
    // FXMLLoader loader = new
    // FXMLLoader(getClass().getResource("/org/example/deptView.fxml"));
    // Parent root = loader.load();
    // DeptController controller = loader.getController();
    // controller.setDeptService(deptService);
    // Stage stage = (Stage) menuBar.getScene().getWindow();
    // stage.setScene(new Scene(root));
    // stage.setTitle("Gestion des Depts");
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    @FXML
    public void navigateToClients() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/clientsView.fxml"));
            Parent root = loader.load();
            ClientController controller = loader.getController();
            controller.setClientService(clientService);
            controller.setUserService(userService);
            Stage stage = (Stage) menuBar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestion des Clients");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void navigateToUsers(@SuppressWarnings("exports") ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/usersView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
