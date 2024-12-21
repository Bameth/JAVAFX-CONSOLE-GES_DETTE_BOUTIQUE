package org.example.data.views;

import java.util.Scanner;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);

    public static int displayAdminMenu() {
        System.out.println("-------------------Menu ADMIN-------------------");
        System.out.println("1-Creer un compte pour un client");
        System.out.println("2-Creer un compte user");
        System.out.println("3-Desactiver un compte User");
        System.out.println("4-Lister les users");
        System.out.println("5-Afficher les comptes utilisateurs actifs ou par rôle");
        System.out.println("6-Créer Article");
        System.out.println("7-Afficher Article");
        System.out.println("8-Filtrer Article par disponibilité");
        System.out.println("9-Mettre à jour la quantité en stock");
        System.out.println("10-Archiver les dettes soldées");
        System.out.println("11-QUITTER");
        System.out.print("Entrez votre choix: ");
        return scanner.nextInt();
    }

    public static int displayBoutiquierMenu() {
        System.out.println("-------------------Menu BOUTIQUIER-------------------");
        System.out.println("1-Créer un client avec un utilisateur");
        System.out.println("2-Lister les clients");
        System.out.println("3-Lister les informations d’un client à partir d'une recherche sur le téléphone");
        System.out.println("4-Lister client ayant un compte ou pas");
        System.out.println("5-Enregistrer les dettes");
        System.out.println("6-Payer une dette");
        System.out.println("7-Lister les dettes non soldées");
        System.out.println("8-Lister les demandes de dettes en cours");
        System.out.println("9-QUITTER");
        System.out.print("Entrez votre choix: ");
        return scanner.nextInt();
    }

    public static int displayClientMenu() {
        System.out.println("-------------------Menu CLIENT-------------------");
        System.out.println("1-Lister ses dettes non soldées");
        System.out.println("2-Faire une demande de dette");
        System.out.println("3-Lister ses demandes de dette et filter par ENCOURS/ANNULER");
        System.out.println("4-Envoyer une relance pour une demande de dette annulée");
        System.out.println("5-QUITTER");
        System.out.print("Entrez votre choix: ");
        return scanner.nextInt();
    }
}
