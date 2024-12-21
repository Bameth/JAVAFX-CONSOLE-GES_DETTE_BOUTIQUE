package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

import org.example.data.core.interfaces.ArticleRepository;
import org.example.data.core.interfaces.ClientRepository;
import org.example.data.core.interfaces.DeptRepository;
import org.example.data.core.interfaces.DetailRepository;
import org.example.data.core.interfaces.UserRepository;
import org.example.data.entities.Article;
import org.example.data.entities.Client;
import org.example.data.entities.Dept;
import org.example.data.entities.Detail;
import org.example.data.repositories.JPA.ArticleRepositoryJPAImpl;
import org.example.data.repositories.JPA.ClientRepositoryJPAImpl;
import org.example.data.repositories.JPA.DeptRepositoryJPAImpl;
import org.example.data.repositories.JPA.DetailRepositoryJPAImpl;
import org.example.data.repositories.JPA.UserRepositoryJPAImpl;
import org.example.data.services.ArticleServiceImpl;
import org.example.data.services.ClientServiceImpl;
import org.example.data.services.DeptServiceImpl;
import org.example.data.services.DetailServiceImpl;
import org.example.data.services.UserServiceImpl;

public class DetteController {

    @FXML
    private TextField clientNameField, phoneField, addressField, priceField, quantityField, totalField;
    @FXML
    private ComboBox<Article> articleComboBox;
    @FXML
    private TableView<ArticleOrder> orderTable;
    @FXML
    private TableColumn<ArticleOrder, String> articleColumn;
    @FXML
    private TableColumn<ArticleOrder, Double> priceColumn;
    @FXML
    private TableColumn<ArticleOrder, Integer> quantityColumn;
    @FXML
    private TableColumn<ArticleOrder, Double> amountColumn;
    @FXML
    private TableColumn<ArticleOrder, Button> actionColumn;

    private ObservableList<ArticleOrder> orderList = FXCollections.observableArrayList();
    private ObservableList<Article> articlesList;

    private ArticleServiceImpl articleService;
    private ClientServiceImpl clientService;
    private DeptServiceImpl deptService;
    private DetailServiceImpl detailService;
    private UserServiceImpl userService;

    public DetteController() {
        ClientRepository clientRepository = new ClientRepositoryJPAImpl();
        UserRepository userRepository = new UserRepositoryJPAImpl();
        DeptRepository deptRepository = new DeptRepositoryJPAImpl();
        ArticleRepository articleRepository = new ArticleRepositoryJPAImpl();
        DetailRepository detailRepository = new DetailRepositoryJPAImpl();
        this.detailService = new DetailServiceImpl(detailRepository);
        this.articleService = new ArticleServiceImpl(articleRepository);
        this.clientService = new ClientServiceImpl(clientRepository);
        this.userService = new UserServiceImpl(userRepository);
        this.deptService = new DeptServiceImpl(deptRepository);
    }

    public void setArticleService(ArticleServiceImpl articleService) {
        this.articleService = articleService;
        loadArticles();
    }

    public void setDetailService(DetailServiceImpl detailService) {
        this.detailService = detailService;
    }

    public void setClientService(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    public void setDeptService(DeptServiceImpl deptService) {
        this.deptService = deptService;
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @FXML
    public void initialize() {
        articleColumn.setCellValueFactory(new PropertyValueFactory<>("articleName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("actions"));

        orderTable.setItems(orderList);
    }

    private void loadArticles() {
        List<Article> articles = articleService.findAll();
        articlesList = FXCollections.observableArrayList(articles);
        articleComboBox.setItems(articlesList);
    }

    @FXML
    private void searchClientByPhone() {
        if (clientService == null) {
            showAlert("Service client non initialisé.");
            return;
        }

        String phone = phoneField.getText();
        Client client = clientService.search(phone);
        if (client != null) {
            clientNameField.setText(client.getSurname());
            addressField.setText(client.getAddress());
        } else {
            showAlert("Client introuvable.");
            clientNameField.clear();
            addressField.clear();
        }
    }

    @FXML
    private void populateArticlePrice() {
        Article selectedArticle = articleComboBox.getValue();
        if (selectedArticle != null) {
            priceField.setText(String.valueOf(selectedArticle.getPrix()));
        }
    }

    @FXML
    private void addArticleToOrder() {
        Article selectedArticle = articleComboBox.getValue();
        if (selectedArticle == null) {
            showAlert("Veuillez sélectionner un article.");
            return;
        }
        try {
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            double amount = price * quantity;

            ArticleOrder order = new ArticleOrder(selectedArticle.getLibelle(), price, quantity, amount);
            order.setActions(createDeleteButton(order));
            orderList.add(order);

            calculateTotal();
        } catch (NumberFormatException e) {
            showAlert("Veuillez entrer une quantité valide.");
        }
    }

    private Button createDeleteButton(ArticleOrder order) {
        Button deleteButton = new Button("Supprimer");
        deleteButton.setOnAction(event -> {
            orderList.remove(order);
            calculateTotal();
        });
        return deleteButton;
    }

    private void calculateTotal() {
        double total = orderList.stream().mapToDouble(ArticleOrder::getAmount).sum();
        totalField.setText(String.valueOf(total));
    }

    @FXML
    private void validateDebt() {
        try {
            if (deptService == null) {
                showAlert("Service dette non initialisé.");
                return;
            }
            String clientPhone = phoneField.getText();
            Client client = clientService.search(clientPhone);
            if (client == null) {
                showAlert("Veuillez rechercher un client valide avant de valider la dette.");
                return;
            }

            double total = Double.parseDouble(totalField.getText());
            Dept debt = new Dept();
            debt.setClient(client);
            debt.setMontant(total);
            debt.setMontantRestant(total);
            deptService.create(debt);
            for (ArticleOrder order : orderList) {
                Detail detail = new Detail();
                detail.setArticle(order.getArticle());
                detail.setDept(debt);
                detail.setQte(order.getQuantity());

                detailService.create(detail);
            }
            showAlert("Dette validée avec succès !");
            clearForm();
        } catch (Exception e) {
            showAlert("Erreur lors de la validation de la dette : " + e.getMessage());
        }
    }

    private void clearForm() {
        phoneField.clear();
        clientNameField.clear();
        addressField.clear();
        articleComboBox.setValue(null);
        priceField.clear();
        quantityField.clear();
        totalField.clear();
        orderList.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.showAndWait();
    }

    public static class ArticleOrder {
        private final String articleName;
        private final double price;
        private final int quantity;
        private final double amount;
        private Button actions;
        private Article article;

        // Getter for article
        public Article getArticle() {
            return article;
        }

        // Setter for article
        public void setArticle(Article article) {
            this.article = article;
        }

        public ArticleOrder(String articleName, double price, int quantity, double amount) {
            this.articleName = articleName;
            this.price = price;
            this.quantity = quantity;
            this.amount = amount;
        }

        public String getArticleName() {
            return articleName;
        }

        public double getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getAmount() {
            return amount;
        }

        public Button getActions() {
            return actions;
        }

        public void setActions(Button actions) {
            this.actions = actions;
        }
    }
}
