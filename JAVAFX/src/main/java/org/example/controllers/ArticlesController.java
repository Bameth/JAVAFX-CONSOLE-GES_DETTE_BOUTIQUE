package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import org.example.data.core.interfaces.ArticleRepository;
import org.example.data.core.interfaces.ClientRepository;
import org.example.data.core.interfaces.UserRepository;
import org.example.data.entities.Article;
import org.example.data.repositories.JPA.ArticleRepositoryJPAImpl;
import org.example.data.repositories.JPA.ClientRepositoryJPAImpl;
import org.example.data.repositories.JPA.UserRepositoryJPAImpl;
import org.example.data.services.ArticleServiceImpl;
import org.example.data.services.ClientServiceImpl;
import org.example.data.services.UserServiceImpl;

import java.io.IOException;
import java.util.List;

public class ArticlesController {

    @FXML
    private TableView<Article> articlesTable;
    @FXML
    private TableColumn<Article, String> referenceColumn;
    @FXML
    private TableColumn<Article, String> libelleColumn;
    @FXML
    private TableColumn<Article, Double> prixColumn;
    @FXML
    private TableColumn<Article, Integer> qteStockColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Button createArticleButton;
    @FXML
    private CheckBox availableFilterCheckBox;
    @FXML
    private Button submitFilterButton;

    private ArticleServiceImpl articleService;
    private ObservableList<Article> articlesList;
    private UserServiceImpl userService;
    private ClientServiceImpl clientService;

    public ArticlesController() {
        ClientRepository clientRepository = new ClientRepositoryJPAImpl();
        UserRepository userRepository = new UserRepositoryJPAImpl();
        ArticleRepository articleRepository = new ArticleRepositoryJPAImpl();
        this.articleService = new ArticleServiceImpl(articleRepository);
        this.clientService = new ClientServiceImpl(clientRepository);
        this.userService = new UserServiceImpl(userRepository);
    }

    public void setArticleService(@SuppressWarnings("exports") ArticleServiceImpl articleService) {
        this.articleService = articleService;
        System.out.println("ArticleService has been set!");
        loadArticles();
    }

    public void loadArticles() {
        if (articleService == null) {
            System.err.println("ArticleService is not initialized!");
            return;
        }
        List<Article> articles = articleService.findAll();
        if (articles == null || articles.isEmpty()) {
            System.err.println("No articles found!");
        } else {
            System.out.println("Articles loaded successfully!");
        }
        articlesList = FXCollections.observableArrayList(articles);
        articlesTable.setItems(articlesList);
    }

    @FXML
    public void initialize() {
        referenceColumn.setCellValueFactory(cellData -> cellData.getValue().referenceProperty());
        libelleColumn.setCellValueFactory(cellData -> cellData.getValue().libelleProperty());
        prixColumn.setCellValueFactory(cellData -> cellData.getValue().prixProperty().asObject());
        qteStockColumn.setCellValueFactory(cellData -> cellData.getValue().qteStockProperty().asObject());

        createArticleButton.setOnAction(event -> openCreateArticleDialog());
        submitFilterButton.setOnAction(event -> applyFilters());
        loadArticles();
        filterArticles();
    }

    private void applyFilters() {
        if (articlesList == null) {
            articlesList = FXCollections.observableArrayList();
        }
        FilteredList<Article> filteredData = new FilteredList<>(articlesList, article -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(article -> {
                if (newValue == null || newValue.isEmpty())
                    return true;
                String lowerCaseFilter = newValue.toLowerCase();
                return article.getLibelle().toLowerCase().contains(lowerCaseFilter)
                        || article.getReference().toLowerCase().contains(lowerCaseFilter);
            });
        });

        availableFilterCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(article -> {
                if (availableFilterCheckBox.isSelected() && article.getQteStock() <= 0) {
                    return false;
                }
                return true;
            });
        });

        articlesTable.setItems(filteredData);
    }

    private void filterArticles() {
        if (articlesList == null) {
            articlesList = FXCollections.observableArrayList();
        }
        FilteredList<Article> filteredData = new FilteredList<>(articlesList, article -> true);
        articlesTable.setItems(filteredData);
    }

    private void openCreateArticleDialog() {
        Dialog<Article> dialog = new Dialog<>();
        dialog.setTitle("Créer un nouvel article");

        TextField libelleField = new TextField();
        libelleField.setPromptText("Libellé");

        TextField prixField = new TextField();
        prixField.setPromptText("Prix");

        TextField qteStockField = new TextField();
        qteStockField.setPromptText("Quantité en stock");

       
        VBox dialogPaneContent = new VBox(10);
        dialogPaneContent.getChildren().addAll(libelleField, prixField, qteStockField);

        dialog.getDialogPane().setContent(dialogPaneContent);

        ButtonType createButtonType = new ButtonType("Créer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                try {
                    Article newArticle = new Article();
                    newArticle.setLibelle(libelleField.getText());
                    newArticle.setPrix(Double.parseDouble(prixField.getText()));
                    newArticle.setQteStock(Integer.parseInt(qteStockField.getText()));
                    return newArticle;
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setHeaderText("Veuillez entrer des valeurs valides pour le prix et la quantité.");
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(article -> {
            try {
                articleService.create(article);
                loadArticles(); 
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Creation Error");
                alert.setHeaderText("Could not save the new article.");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });
    }

    @FXML
    private void navigateToClients() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/adminView.fxml"));
            Parent root = loader.load();

            AdminController controller = loader.getController();
            controller.setClientService(clientService);
            controller.setUserService(userService);

            Scene scene = articlesTable.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToClients2() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/BoutiquierView.fxml"));
            Scene scene = articlesTable.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToUsers() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/usersView.fxml"));
            Scene scene = articlesTable.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
