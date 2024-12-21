package org.example.data.services;

import org.example.data.core.interfaces.ArticleRepository;
import org.example.data.entities.Article;

import java.util.List;

public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void create(Article article) {
        articleRepository.insert(article);
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.selectAll();
    }

    @Override
    public List<Article> findAvailable() {
        return articleRepository.selectByAvailability();
    }

    @Override
    public boolean update(Article article) {
        return articleRepository.update(article);
    }

    @Override
    public Article getBy(String value) {
        return articleRepository.selectByReference(value);
    }

    @Override
    public int count() {
        return articleRepository.count();
    }
}
