package org.example.data.views;

import org.example.data.core.interfaces.ViewImpl;
import org.example.data.entities.User;
import org.example.data.enums.Role;
import org.example.data.enums.TypeEtat;
import org.example.data.services.UserServiceImpl;

import java.util.List;
import java.util.Scanner;

public class UserView extends ViewImpl<User> {
    private final UserServiceImpl userService;

    public UserView(Scanner scanner, UserServiceImpl userService) {
    super(scanner);
    this.userService = userService;
}


    @Override
    public User saisie() {
        User user = new User();
        System.out.println("Veuillez saisir les informations de l'utilisateur :");
        System.out.println("===============================================");
        System.out.println("Name :");
        String name = scanner.next();
        user.setName(name);
        System.out.println("login");
        String login = scanner.next();
        user.setLogin(login);
        System.out.println("password");
        String password = scanner.next();
        user.setPassword(password);
        System.out.println("==============================================");
        Role role = saisieRole();
        user.setRole(role);
        user.setTypeEtat(TypeEtat.ACTIVER);
        return user;
    }

    public User Status() {
        System.out.println("Veuillez saisir le name de l'utilisateur à activer/desactiver son compte : ");
        String userName = scanner.next();
        User user = userService.getBy(userName);

        if (user == null) {
            System.out.println("L'utilisateur avec le name spécifié n'existe pas.");
            return null;
        }

        TypeEtat typeEtat = saisieEtat();
        user.setTypeEtat(typeEtat);
        if (userService.update(user)) {
            System.out.println("Status du Compte changer avec succès !");
        } else {
            System.out.println("Erreur lors du changement de status du compte.");
        }

        return user;
    }

    public Role saisieRole() {
        int roleChoice;
        do {
            for (Role role : Role.values()) {
                System.out.println((role.ordinal() + 1) + "-" + role.name());
            }
            System.out.println("Veuillez sélectionner un role : ");
            roleChoice = scanner.nextInt();
        } while (roleChoice <= 0 || roleChoice > Role.values().length);

        return Role.values()[roleChoice - 1];
    }

    public TypeEtat saisieEtat() {
        int etatChoice;
        do {
            for (TypeEtat etat : TypeEtat.values()) {
                System.out.println((etat.ordinal() + 1) + "-" + etat.name());
            }
            System.out.println("Veuillez sélectionner un Etat : ");
            etatChoice = scanner.nextInt();
        } while (etatChoice <= 0 || etatChoice > TypeEtat.values().length);

        return TypeEtat.values()[etatChoice - 1];
    }

    public void displayUsersByEtat() {
        System.out.println("Sélectionner l'état des utilisateurs à afficher:");
        TypeEtat typeEtat = saisieEtat();
        List<User> users = userService.findByEtat(typeEtat);

        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur trouvé avec cet état.");
        } else {
            users.forEach(System.out::println);
        }
    }

    public void displayUsersByRole() {
        System.out.println("Sélectionner le rôle des utilisateurs à afficher:");
        Role role = saisieRole();
        List<User> users = userService.findByRole(role);

        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur trouvé avec ce rôle.");
        } else {
            users.forEach(System.out::println);
        }
    }
    public void displayUsers(List<User> users) {
        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur trouvé.");
        } else {
            for (User user : users) {
                System.out.println(user);
            }
        }
    }
}
