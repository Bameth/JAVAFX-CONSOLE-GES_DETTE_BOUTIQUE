package org.example.data.repositories.JPA;

import org.example.data.core.interfaces.ClientRepository;
import org.example.data.core.config.RepositoryJPAImpl;
import org.example.data.entities.Client;

import java.util.List;
import javax.persistence.TypedQuery;

public class ClientRepositoryJPAImpl extends RepositoryJPAImpl<Client> implements ClientRepository {

    public ClientRepositoryJPAImpl() {
        super(Client.class);
    }

    @Override
    public Client selectByPhone(String phone) {
        try {
            String query = "SELECT c FROM Client c WHERE c.phone = :phone";
            TypedQuery<Client> typedQuery = em.createQuery(query, Client.class);
            typedQuery.setParameter("phone", phone);
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Client> findAllClientWithAccount() {
        try {
            String query = "SELECT c FROM Client c WHERE c.user IS NOT NULL";
            TypedQuery<Client> typedQuery = em.createQuery(query, Client.class);
            return typedQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Client selectByUserId(int userId) {
        try {
            String query = "SELECT c FROM Client c WHERE c.user.id = :user_id";
            TypedQuery<Client> typedQuery = em.createQuery(query, Client.class);
            typedQuery.setParameter("user_id", userId);
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
