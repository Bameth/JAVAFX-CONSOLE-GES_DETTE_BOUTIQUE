package org.example;

import org.example.data.Factory.FactoryRepository;
import org.example.data.Factory.FactoryService;
import org.example.data.Factory.Impl.FactoryRepositoryImpl;
import org.example.data.Factory.Impl.FactoryServiceImpl;
import org.example.data.entities.*;
import org.example.data.enums.Role;
import org.example.data.services.*;
import org.example.data.views.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
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

        // Connexion utilisateur
        User connectedUser = login(userServiceImpl);

        if (connectedUser != null) {
            // Affichage du menu selon le rôle
            Role userRole = connectedUser.getRole();
            int choice;
            switch (userRole) {
                case ADMIN -> {
                    // Menu ADMIN
                    do {
                        choice = Menu.displayAdminMenu();
                        handleAdminMenu(choice, clientServiceImpl, userServiceImpl, articleServiceImpl, clientView,
                                userView, articleView);
                    } while (choice != 11); // Quitter le menu admin
                }
                case BOUTIQUIER -> {
                    // Menu BOUTIQUIER
                    do {
                        choice = Menu.displayBoutiquierMenu();
                        handleBoutiquierMenu(choice, clientServiceImpl, userServiceImpl, articleServiceImpl, clientView,
                                userView, deptServiceImpl, deptView);
                    } while (choice != 9); // Quitter le menu boutiquier
                }
                case CLIENT -> {
                    // Menu CLIENT
                    do {
                        choice = Menu.displayClientMenu();
                        handleClientMenu(choice, clientServiceImpl, userServiceImpl, articleServiceImpl, clientView,
                                userView, deptServiceImpl, deptView, connectedUser);
                    } while (choice != 5); // Quitter le menu client
                }
                default -> System.out.println("Rôle non reconnu.");
            }
        } else {
            System.out.println("Échec de la connexion.");
        }
    }

    private static User login(UserServiceImpl userServiceImpl) {
        System.out.println("=== Connexion ===");
        System.out.print("Login: ");
        String login = scanner.next();
        System.out.print("Mot de passe: ");
        String password = scanner.next();

        User user = userServiceImpl.findByLogin(login, password);
        if (user != null) {
            System.out.println("Connexion réussie. Bienvenue, " + user.getName() + "!");
            return user;
        } else {
            System.out.println("Login ou mot de passe incorrect.");
            return null;
        }
    }

    // Gestion des actions pour le menu ADMIN
    private static void handleAdminMenu(int choice, ClientServiceImpl clientService, UserServiceImpl userServiceImpl,
            ArticleServiceImpl articleServiceImpl, ClientView clientView,
            UserView userView, ArticleView articleView) {
        switch (choice) {
            case 1 -> clientService.create(clientView.saisie());
            case 2 -> userServiceImpl.create(userView.saisie());
            case 3 -> userServiceImpl.update(userView.Status());
            case 4 -> {
                List<User> listUsers = userServiceImpl.findAll();
                listUsers.forEach(System.out::println);
            }
            case 5 -> {
                System.out.println("1- Afficher les utilisateurs actifs/désactivés");
                System.out.println("2- Afficher les utilisateurs par rôle");
                int filterChoice = scanner.nextInt();
                if (filterChoice == 1) {
                    userView.displayUsersByEtat();
                } else if (filterChoice == 2) {
                    userView.displayUsersByRole();
                }
            }
            case 6 -> articleServiceImpl.create(articleView.saisie());
            case 7 -> {
                List<Article> listArticles = articleServiceImpl.findAll();
                listArticles.forEach(System.out::println);
            }
            case 8 -> {
                List<Article> listArticles = articleServiceImpl.findAvailable();
                listArticles.forEach(System.out::println);
            }
            case 9 -> {
                Article articleModifiee = articleView.updateQteStock();
                if (articleModifiee != null) {
                    if (articleServiceImpl.update(articleModifiee)) {
                        System.out.println("qteStock modifiée avec succès !");
                    } else {
                        System.out.println("Erreur lors de la modification de la qteStock.");
                    }
                }
            }
            case 10 -> System.out.println("Déconnexion...");
            default -> System.out.println("Choix invalide.");
        }
    }

    // Gestion des actions pour le menu BOUTIQUIER
    private static void handleBoutiquierMenu(int choice, ClientServiceImpl clientServiceImpl,
            UserServiceImpl userServiceImpl, ArticleServiceImpl articleServiceImpl,
            ClientView clientView, UserView userView, DeptServiceImpl deptServiceImpl, DeptView deptView) {
        switch (choice) {
            case 1 -> clientServiceImpl.create(clientView.saisie());
            case 2 -> {
                List<Client> listClients = clientServiceImpl.findAll();
                listClients.forEach(System.out::println);
            }
            case 3 -> {
                System.out.print("Entrer le numero de téléphone: ");
                String phone = scanner.next();
                Client client = clientServiceImpl.search(phone);
                if (client != null) {
                    System.out.println(client);
                } else {
                    System.out.println("Client non trouvé.");
                }
            }
            case 4 -> {
                List<Client> listClients = clientServiceImpl.findAllClientWithAccount();
                if (listClients != null && !listClients.isEmpty()) {
                    listClients.forEach(System.out::println);
                } else {
                    System.out.println("Aucun client avec compte trouvé.");
                }
            }
            case 5 -> deptView.saisie();
            case 6 -> {
                Dept dept = deptView.PayementDept();
                if (dept != null) {
                    System.out.println(dept);
                } else {
                    System.out.println("Dette non payée.");
                }
                System.out.println("Paiement effectué avec succès.");
            }
            case 7 -> {
                List<Dept> listDebts = deptServiceImpl.findAllDeptNonSoldees();
                if (listDebts != null && !listDebts.isEmpty()) {
                    listDebts.forEach(System.out::println);
                } else {
                    System.out.println("Aucune dette NONSOLDEES ");
                }
            }
            case 8 -> System.out.println("Lister demandes de dettes en cours non implémentée");
            case 9 -> System.out.println("Déconnexion...");
            default -> System.out.println("Choix invalide.");
        }
    }

    // Gestion des actions pour le menu CLIENT
    private static void handleClientMenu(int choice, ClientServiceImpl clientServiceImpl,
            UserServiceImpl userServiceImpl, ArticleServiceImpl articleServiceImpl,
            ClientView clientView, UserView userView, DeptServiceImpl deptServiceImpl, DeptView deptView,
            User connectedUser) {
        switch (choice) {
            case 1 -> {
                System.out.println("=== Vos dettes NON SOLDEES ===");
                if (connectedUser != null && connectedUser.getRole() == Role.CLIENT) {
                    Client client = clientServiceImpl.findByUserId(connectedUser.getId());
                    if (client != null) {
                        List<Dept> nonSoldees = deptServiceImpl.findAllMyDeptNonSoldees(client.getId());
                        if (!nonSoldees.isEmpty()) {
                            nonSoldees.forEach(System.out::println);
                        } else {
                            System.out.println("Vous n'avez aucune dette non soldée.");
                        }
                    } else {
                        System.out.println("Client non trouvé.");
                    }
                } else {
                    System.out.println("Connexion client requise pour cette action.");
                }
            }
            case 2 -> {
                System.out.println("=== DEMANDE DE DETTE ===");
                if (connectedUser != null && connectedUser.getRole() == Role.CLIENT) {
                    Client client = clientServiceImpl.findByUserId(connectedUser.getId());
                    if (client != null) {
                        deptView.saisie();
                    } else {
                        System.out.println("Client non trouvé.");
                    }
                }
            }
            case 3 -> {
                System.out.println("=== LISTE DE MES DEMANDES DE DETTE OU FILTRER PAR ETAT ===");
                if (connectedUser != null && connectedUser.getRole() == Role.CLIENT) {
                    Client client = clientServiceImpl.findByUserId(connectedUser.getId());
                    System.out.println("1- Afficher toutes mes demandes de dette");
                    System.out.println("2- Afficher les dettes ENCOURS OU ANNULER");
                    int filterChoice = scanner.nextInt();
                    if (filterChoice == 1) {
                        List<Dept> myDebts = deptServiceImpl.findAllMyDebts(client.getId());
                        if (!myDebts.isEmpty()) {
                            myDebts.forEach(System.out::println);
                        } else {
                            System.out.println("Vous n'avez pas encore de demande de dette.");
                        }
                    } else if (filterChoice == 2) {
                        deptView.displayDebtsByEtat();
                    } else {
                        System.out.println("Choix invalide");
                    }
                }
            }
            case 4 -> {
                System.out.println("=== ENVOYER UNE RELANCE POUR UNE DEMANDE DETTE ANNULER ===");
                if (connectedUser != null && connectedUser.getRole() == Role.CLIENT) {
                    Client client = clientServiceImpl.findByUserId(connectedUser.getId());
                    List<Dept> canceledDebts = deptServiceImpl.findCanceledDebtsByClientId(client.getId());
                    if (canceledDebts.isEmpty()) {
                        System.out.println("Aucune dette annulée trouvée pour ce client.");
                        return;
                    }
                    canceledDebts.forEach(System.out::println);
                    deptView.relancerDetteAnnulee();
                }
            }
            case 5 -> System.out.println("Déconnexion...");
            default -> System.out.println("Choix invalide.");
        }
    }

}
