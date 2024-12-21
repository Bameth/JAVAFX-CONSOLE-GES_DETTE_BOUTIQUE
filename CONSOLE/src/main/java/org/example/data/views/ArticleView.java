package org.example.data.views;

import java.util.Scanner;

import org.example.data.core.interfaces.ViewImpl;
import org.example.data.entities.Article;
import org.example.data.services.ArticleServiceImpl;

public class ArticleView extends ViewImpl<Article> {
    private final ArticleServiceImpl articleService;

    public ArticleView(Scanner scanner,ArticleServiceImpl articleService) {
        super(scanner);
        this.articleService=articleService;
    }

    @Override
    public Article saisie() {
        Article article = new Article();
        System.out.println("Saisie des informations de l'article :");
        System.out.println("Libelle : ");
        article.setLibelle(scanner.next());
        System.out.println("Prix : ");
        article.setPrix(scanner.nextInt());
        System.out.println("Quantité en stock : ");
        article.setQteStock(scanner.nextInt());
        System.out.println("Article Inserer avec Succés!");
        return article;
    }

    public Article updateQteStock() {
        System.out.println("Veuillez saisir la reference de l'article : ");
        System.out.println("======================================================");
        String reference = scanner.next();
        Article article = articleService.getBy(reference);

        if (article == null) {
            System.out.println("Article inexistante");
            return null;
        }
        System.out.println("Update de la qteStock de l'article :");
        System.out.print("Nouveau QteStock: ");
        int qteStock = scanner.nextInt();
        article.setQteStock(qteStock);
        return article;
    }
    
}
