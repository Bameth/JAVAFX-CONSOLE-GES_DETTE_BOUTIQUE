package org.example.data.core.interfaces;

import java.util.List;

import org.example.data.core.config.Repository;
import org.example.data.entities.Article;

public interface ArticleRepository extends Repository<Article> {
    List<Article> selectByAvailability();
    Article selectByReference(String reference);
}
