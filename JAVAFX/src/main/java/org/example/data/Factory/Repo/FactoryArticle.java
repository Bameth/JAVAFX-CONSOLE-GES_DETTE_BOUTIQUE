package org.example.data.Factory.Repo;

import org.example.data.core.interfaces.ArticleRepository;
import org.example.data.core.interfaces.UserRepository;
import org.example.data.repositories.DB.ArticleRepositoryBd;
import org.example.data.repositories.DB.UserRepositoryBd;
import org.example.data.repositories.JPA.ArticleRepositoryJPAImpl;
import org.example.data.repositories.JPA.UserRepositoryJPAImpl;
import org.example.data.repositories.list.ArticleRepositoryList;
import org.example.data.repositories.list.UserRepositoryList;
import org.example.data.services.ArticleService;
import org.example.data.services.ArticleServiceImpl;
import org.example.data.services.UserService;
import org.example.data.services.UserServiceImpl;

public class FactoryArticle {
    private ArticleRepository articleRepository;

    private ArticleService articleService;

    private ArticleRepositoryList articleRepositoryList;

    private ArticleRepositoryBd ArticleRepositoryBd;

    private ArticleRepositoryJPAImpl articleRepositoryJPAImpl;

    public FactoryArticle() {
        ArticleRepositoryList articleRepositoryList = new ArticleRepositoryList();
        ArticleRepositoryBd articleRepositoryBd = new ArticleRepositoryBd();
        this.articleRepository = new ArticleRepositoryJPAImpl();
        this.articleRepository = articleRepository;
        articleService = new ArticleServiceImpl(articleRepository);
    }
}
