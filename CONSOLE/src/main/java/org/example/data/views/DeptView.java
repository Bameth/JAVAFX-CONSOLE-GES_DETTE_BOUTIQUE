package org.example.data.views;

import java.util.List;
import java.util.Scanner;

import org.example.data.core.interfaces.ViewImpl;
import org.example.data.entities.Article;
import org.example.data.entities.Client;
import org.example.data.entities.Dept;
import org.example.data.entities.Detail;
import org.example.data.entities.User;
import org.example.data.enums.TypeDette;
import org.example.data.enums.TypeEtat;
import org.example.data.services.ArticleServiceImpl;
import org.example.data.services.ClientServiceImpl;
import org.example.data.services.DeptServiceImpl;
import org.example.data.services.DetailServiceImpl;

public class DeptView extends ViewImpl<Dept> {
    private final Scanner scanner;
    private final ClientServiceImpl clientService;
    private final DeptServiceImpl deptService;
    private final ArticleServiceImpl articleService;
    private final DetailServiceImpl detailService;

    public DeptView(Scanner scanner, ClientServiceImpl clientService, DeptServiceImpl deptService,
            ArticleServiceImpl articleService, DetailServiceImpl detailService) {
        super(scanner);
        this.scanner = scanner;
        this.clientService = clientService;
        this.deptService = deptService;
        this.articleService = articleService;
        this.detailService = detailService;
    }

    @Override
    public Dept saisie() {
        System.out.println("========================");
        Client client = askDeptClient();
        if (client == null) {
            System.out.println("Client introuvable.");
            return null;
        }
        System.out.println("=============================");
        Article article = askDeptArticle();
        if (article == null) {
            System.out.println("L'article avec cette référence n'existe pas.");
            return null;
        }
        System.out.println("=============================");
        System.out.print("Entrez la quantité de l'article: ");
        int qte = scanner.nextInt();
        if (qte <= 0 || qte > article.getQteStock()) {
            System.out.println("La quantité saisie est incorrecte ou dépasse le stock disponible.");
            return null;
        }

        double montant = article.getPrix() * qte;
        System.out.println("===============================");
        System.out.println("Le montant total de la dette est: " + (montant));
        System.out.println("===============================");

        System.out.print("Entrez le montant versé: ");
        double montantVerser = scanner.nextDouble();
        double montantRestant = montant - montantVerser;

        if (montantRestant < 0) {
            System.out.println("Le montant versé est supérieur au montant de la dette.");
            return null;
        }
        Dept dept = new Dept();
        dept.setMontant(montant);
        dept.setMontantVerser(montantVerser);
        dept.setMontantRestant(montantRestant);
        dept.setClient(client);
        deptService.create(dept);

        Detail detail = new Detail();
        detail.setArticle(article);
        detail.setQte(qte);
        detail.setDept(dept);
        dept.getDetail().add(detail);

        article.setQteStock(article.getQteStock() - qte);
        articleService.update(article);
        detailService.create(detail);

        System.out.println("👌👌👌👌👌👌👌👌👌👌👌👌👌👌👌");
        System.out.println("La dette a été enregistrée avec succès.");
        return dept;
    }

    public Article askDeptArticle() {

        System.out.print("Entrez la référence de l'article: ");
        String reference = scanner.next();
        return articleService.getBy(reference);
    }

    public Client askDeptClient() {
        System.out.print("Entrez le numero de telephone du client: ");
        String phone = scanner.next();
        return clientService.search(phone);
    }

    public Dept PayementDept() {
        System.out.print("Entrez le numéro de téléphone du client dont vous voulez payer la dette: ");
        String phone = scanner.next();
        Client client = clientService.search(phone);
        if (client == null) {
            System.out.println("Client non trouvé.");
            return null;
        }
        List<Dept> debts = deptService.findDebtsByClientId(client.getId());
        if (debts.isEmpty()) {
            System.out.println("Ce client n'a pas de dettes.");
            return null;
        }
        System.out.println("Dettes du client :");
        for (Dept dept : debts) {
            System.out.println(dept);
        }
        System.out.print("Entrez l'id de la dette à payer: ");
        int id = scanner.nextInt();
        Dept deptToPay = null;
        for (Dept dept : debts) {
            if (dept.getId() == id) {
                deptToPay = dept;
                break;
            }
        }
        if (deptToPay == null) {
            System.out.println("Dette non trouvée ou ne fait pas partie des dettes du client.");
            return null;
        }
        System.out.println("Détails de la dette : " + deptToPay);
        System.out.print("Entrez le montant à verser: ");
        double montantVerser = scanner.nextDouble();
        double montantRestant = deptToPay.getMontantRestant() - montantVerser;
        if (montantRestant < 0) {
            System.out.println("Le montant versé est supérieur au montant restant de la dette.");
            return null;
        }
        deptToPay.setMontantVerser(deptToPay.getMontantVerser() + montantVerser);
        deptToPay.setMontantRestant(montantRestant);
        deptToPay.updateEtat();
        deptService.update(deptToPay);

        if (montantRestant <= 0) {
            System.out.println("La dette a été entièrement réglée.");
        } else {
            System.out.println("La dette a été partiellement réglée.");
        }
        System.out.println("Mise à jour effectuée avec succès.");
        return deptToPay;
    }

    public void displayDebtsByEtat() {
        System.out.println("Sélectionner l'état de la dette à afficher:");
        TypeDette typeDette = saisieEtat();
        List<Dept> depts = deptService.findByEtat(typeDette);

        if (depts.isEmpty()) {
            System.out.println("Aucune dette trouvé avec cet état.");
        } else {
            depts.forEach(System.out::println);
        }
    }

    public TypeDette saisieEtat() {
        int etatChoice;
        do {
            for (TypeDette etat : TypeDette.values()) {
                System.out.println((etat.ordinal() + 1) + "-" + etat.name());
            }
            System.out.println("Veuillez sélectionner un Etat : ");
            etatChoice = scanner.nextInt();
        } while (etatChoice <= 0 || etatChoice > TypeDette.values().length);

        return TypeDette.values()[etatChoice - 1];
    }

    public void relancerDetteAnnulee() {
        List<Dept> canceledDebts = deptService.findByEtat(TypeDette.ANNULER);
        System.out.print("Entrez l'id de la dette à relancer : ");
        int id = scanner.nextInt();
        Dept deptToRelancer = null;
        for (Dept dept : canceledDebts) {
            if (dept.getId() == id) {
                deptToRelancer = dept;
                break;
            }
        }

        System.out.print("Êtes-vous sûr de vouloir relancer cette dette ? (oui/non) : ");
        String confirmation = scanner.next();
        if (!confirmation.equalsIgnoreCase("oui")) {
            System.out.println("Demande de relance annulée.");
            return;
        }
        deptToRelancer.setTypeDette(TypeDette.ENCOURS);
        deptService.update(deptToRelancer);
        System.out.println("La demande de relance a été effectuée avec succès.");
    }

    public void displayDeptByEncours() {
        System.out.println("Sélectionner l'état de la dette à afficher:");
        TypeDette typeDette = saisieEtat();
        List<Dept> depts = deptService.findByEtat(typeDette);

        if (depts.isEmpty()) {
            System.out.println("Aucun utilisateur trouvé avec cet état.");
        } else {
            depts.forEach(System.out::println);
        }
    }

    public Dept AccepterDept() {
        System.out.print("Entrez l'id de la dette à accepter : ");
        int id = scanner.nextInt();
        Dept deptToAccept = null;
        for (Dept dept : deptService.findAll()) {
            if (dept.getId() == id) {
                deptToAccept = dept;
                break;
            }
        }
        if (deptToAccept == null) {
            System.out.println("Dette non trouvée ou ne fait pas partie des dettes du client.");
            return null; 
        }
        System.out.print("Êtes-vous sûr de vouloir accepter cette dette ? (oui/non) : ");
        String confirmation = scanner.next();
        if (!confirmation.equalsIgnoreCase("oui")) {
            System.out.println("Demande d'acceptation annulée.");
            return null; 
        }
        deptToAccept.setTypeDette(TypeDette.ACCEPTER);
        deptService.update(deptToAccept);
        System.out.println("La demande d'acceptation a été effectuée avec succès.");
        return deptToAccept;
    }

    public Dept AnnulerDept() {
        System.out.print("Entrez l'id de la dette à annuler : ");
        int id = scanner.nextInt();
        Dept deptToCancel = null;
        for (Dept dept : deptService.findAll()) {
            if (dept.getId() == id) {
                deptToCancel = dept;
                break;
            }
        }
        if (deptToCancel == null) {
            System.out.println("Dette non trouvée ou ne fait pas partie des dettes du client.");
            return null;
        }
        System.out.print("Êtes-vous sûr de vouloir annuler cette dette ? (oui/non) : ");
        String confirmation = scanner.next();
        if (!confirmation.equalsIgnoreCase("oui")) {
            System.out.println("Demande d'annulation annulée.");
            return null;
        }
        deptToCancel.setTypeDette(TypeDette.ANNULER);
        deptService.update(deptToCancel);
        System.out.println("La demande d'annulation a été effectuée avec succès.");
        return deptToCancel;
    }
}
