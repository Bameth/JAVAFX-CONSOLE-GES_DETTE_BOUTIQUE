package org.example.data.services;

import java.util.List;

import org.example.data.core.interfaces.Service;
import org.example.data.entities.Article;

public interface ArticleService extends Service<Article> {
    List<Article> findAvailable();
}
