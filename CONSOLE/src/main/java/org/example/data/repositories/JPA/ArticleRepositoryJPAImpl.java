package org.example.data.repositories.JPA;

import java.util.List;
import javax.persistence.TypedQuery;
import org.example.data.core.interfaces.ArticleRepository;
import org.example.data.core.config.RepositoryJPAImpl;
import org.example.data.entities.Article;

public class ArticleRepositoryJPAImpl extends RepositoryJPAImpl<Article> implements ArticleRepository {

    public ArticleRepositoryJPAImpl() {
        super(Article.class); 
    }
    @Override
    public List<Article> selectByAvailability() {
        try {
            String query = "SELECT a FROM Article a WHERE a.qteStock > 0";
            TypedQuery<Article> typedQuery = em.createQuery(query, Article.class);
            return typedQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Article selectByReference(String reference) {
        try {
            String query = "SELECT a FROM Article a WHERE a.reference = :reference";
            TypedQuery<Article> typedQuery = em.createQuery(query, Article.class);
            typedQuery.setParameter("reference", reference);
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
