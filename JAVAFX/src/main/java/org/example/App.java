package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

import org.example.controllers.ConnexionController;
import org.example.data.Factory.FactoryRepository;
import org.example.data.Factory.FactoryService;
import org.example.data.Factory.Impl.FactoryRepositoryImpl;
import org.example.data.Factory.Impl.FactoryServiceImpl;
import org.example.data.entities.Article;
import org.example.data.entities.Client;
import org.example.data.entities.Dept;
import org.example.data.entities.Detail;
import org.example.data.entities.User;
import org.example.data.services.ArticleServiceImpl;
import org.example.data.services.ClientServiceImpl;
import org.example.data.services.DeptServiceImpl;
import org.example.data.services.DetailServiceImpl;
import org.example.data.services.UserServiceImpl;
import org.example.data.views.ArticleView;
import org.example.data.views.ClientView;
import org.example.data.views.DeptView;
import org.example.data.views.UserView;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scanner scanner = new Scanner(System.in);
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/connexion.fxml"));
        Parent root = loader.load();

        // Récupérer le contrôleur
        ConnexionController controller = loader.getController();
        // ArticlesController controller = loader.getController();
        // AdminController controller = loader.getController();

        // Initialisation des repositories et services
        FactoryRepository<Client> factoryRepositoryClient = new FactoryRepositoryImpl<>(Client.class);
        FactoryRepository<User> factoryRepositoryUser = new FactoryRepositoryImpl<>(User.class);
        FactoryRepository<Article> factoryRepositoryArticle = new FactoryRepositoryImpl<>(Article.class);
        FactoryRepository<Dept> factoryRepositoryDept = new FactoryRepositoryImpl<>(Dept.class);
        FactoryRepository<Detail> factoryRepositoryDetail = new FactoryRepositoryImpl<>(Detail.class);

        var clientRepository = factoryRepositoryClient.createRepository();
        var userRepository = factoryRepositoryUser.createRepository();
        var articleRepository = factoryRepositoryArticle.createRepository();
        var deptRepository = factoryRepositoryDept.createRepository();
        var detailRepository = factoryRepositoryDetail.createRepository();

        FactoryService<Client> factoryServiceClient = new FactoryServiceImpl<>(Client.class, clientRepository);
        FactoryService<User> factoryServiceUser = new FactoryServiceImpl<>(User.class, userRepository);
        FactoryService<Article> factoryServiceArticle = new FactoryServiceImpl<>(Article.class, articleRepository);
        FactoryService<Dept> factoryServiceDept = new FactoryServiceImpl<>(Dept.class, deptRepository);
        FactoryService<Detail> factoryServiceDetail = new FactoryServiceImpl<>(Detail.class, detailRepository);

        ClientServiceImpl clientServiceImpl = (ClientServiceImpl) factoryServiceClient.createService();
        UserServiceImpl userServiceImpl = (UserServiceImpl) factoryServiceUser.createService();
        ArticleServiceImpl articleServiceImpl = (ArticleServiceImpl) factoryServiceArticle.createService();
        DeptServiceImpl deptServiceImpl = (DeptServiceImpl) factoryServiceDept.createService();
        DetailServiceImpl detailServiceImpl = (DetailServiceImpl) factoryServiceDetail.createService();

        UserView userView = new UserView(scanner, userServiceImpl);
        ClientView clientView = new ClientView(scanner, clientServiceImpl, userServiceImpl, userView);
        ArticleView articleView = new ArticleView(scanner, articleServiceImpl);
        DeptView deptView = new DeptView(scanner, clientServiceImpl, deptServiceImpl, articleServiceImpl,
                detailServiceImpl);

        // Injecter le service dans le contrôleur
        controller.setUserService(userServiceImpl);
        controller.setClientService(clientServiceImpl);
        controller.setArticleService(articleServiceImpl);

        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.setTitle("Application de Connexion");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/org/example/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
