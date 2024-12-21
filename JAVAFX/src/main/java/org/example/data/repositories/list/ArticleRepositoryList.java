package org.example.data.repositories.list;

import org.example.data.core.interfaces.ArticleRepository;
import org.example.data.core.config.RepositoryListImpl;
import org.example.data.entities.Article;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepositoryList extends RepositoryListImpl<Article> implements ArticleRepository {

    public List<Article> selectByAvailability() {
        List<Article> availableArticles = new ArrayList<>();
        for (Article article : list) {
            if (article.getQteStock() != 0) {
                availableArticles.add(article);
            }
        }
        return availableArticles;
    }
    @Override
    public boolean update(Article article) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getReference().equals(article.getReference())) {
                list.set(i, article);
                return true;
            }
        }
        return false;
    }
    public Article selectByReference(String reference) {
        for (Article article : list) {
            if (article.getReference().equals(reference)) {
                return article;
            }
        }
        return null;
    }
}
