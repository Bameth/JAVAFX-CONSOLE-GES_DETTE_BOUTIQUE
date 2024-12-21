package org.example.data.repositories.JPA;

import org.example.data.core.interfaces.UserRepository;
import org.example.data.core.config.RepositoryJPAImpl;
import org.example.data.entities.User;
import org.example.data.enums.TypeEtat;
import org.example.data.enums.Role;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.ArrayList;

public class UserRepositoryJPAImpl extends RepositoryJPAImpl<User> implements UserRepository {

    public UserRepositoryJPAImpl() {
        super(User.class);
    }

    @Override
    public User selectByName(String name) {
        try {
            String query = "SELECT u FROM User u WHERE u.name = :name";
            TypedQuery<User> typedQuery = em.createQuery(query, User.class);
            typedQuery.setParameter("name", name);
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> selectByEtat(TypeEtat etat) {
        try {
            String query = "SELECT u FROM User u WHERE u.typeEtat = :etat";
            TypedQuery<User> typedQuery = em.createQuery(query, User.class);
            typedQuery.setParameter("etat", etat);
            List<User> resultList = typedQuery.getResultList();
            return resultList != null ? resultList : new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<User> selectByRole(Role role) {
        try {
            String query = "SELECT u FROM User u WHERE u.role = :role";
            TypedQuery<User> typedQuery = em.createQuery(query, User.class);
            typedQuery.setParameter("role", role);
            return typedQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User selectByLogin(String login) {
        try {
            String query = "SELECT u FROM User u WHERE u.login = :login";
            TypedQuery<User> typedQuery = em.createQuery(query, User.class);
            typedQuery.setParameter("login", login);
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
