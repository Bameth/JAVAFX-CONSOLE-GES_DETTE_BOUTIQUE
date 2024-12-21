package org.example.data.Factory.Impl;

import org.example.data.Factory.FactoryView;
import org.example.data.core.interfaces.View;
import org.example.data.entities.*;
import org.example.data.services.*;
import org.example.data.views.*;

import java.util.Scanner;

public class FactoryViewImpl<T> implements FactoryView<T> {
    private final View<T> view;

    @SuppressWarnings("unchecked")
    public FactoryViewImpl(Class<T> clazz, ClientService clientService, ArticleService articleService,
            DetailService detailService,
            UserService userService, DeptService deptService, UserView userView,
            Scanner scanner) {
        if (Client.class.isAssignableFrom(clazz)) {
            this.view = (View<T>) new ClientView(scanner, (ClientServiceImpl) clientService,
                    (UserServiceImpl) userService, userView);
        } else if (User.class.isAssignableFrom(clazz)) {
            this.view = (View<T>) new UserView(scanner, (UserServiceImpl) userService);
        } else if (Article.class.isAssignableFrom(clazz)) {
            this.view = (View<T>) new ArticleView(scanner, (ArticleServiceImpl) articleService);
        } else if (Dept.class.isAssignableFrom(clazz)) {
            this.view = (View<T>) new DeptView(scanner, (ClientServiceImpl) clientService,
                    (DeptServiceImpl) deptService, (ArticleServiceImpl) articleService,
                    (DetailServiceImpl) detailService);
        } else {
            throw new IllegalArgumentException("Unsupported entity type: " + clazz.getName());
        }
    }

    @Override
    public View<T> createView() {
        return view;
    }
}
