package org.example.data.core.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class RepositoryJPAImpl<T> implements Repository<T> {
    protected static EntityManagerFactory emFactory;
    protected EntityManager em;
    protected Class<T> type;

    public RepositoryJPAImpl(Class<T> type) {
        this.type = type;
        // Charger la configuration depuis config.yaml
        Yaml yaml = new Yaml();
        if (emFactory == null) {
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("META-INF/config.yaml")) {
                Map<String, Object> config = yaml.load(inputStream);
                String persistenceUnit = (String) ((Map<String, Object>) config.get("persistence")).get("unit");
                // Créer l'EntityManagerFactory en fonction de l'unité de persistance
                emFactory = Persistence.createEntityManagerFactory(persistenceUnit);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.em = emFactory.createEntityManager();
    }

    @Override
    public boolean insert(T data) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(data);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(T objet) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(objet);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            T entity = em.find(getEntityClass(), id);
            if (entity != null) {
                em.remove(entity);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
            }
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<T> selectAll() {
        try {
            String query = "SELECT e FROM " + getEntityClass().getSimpleName() + " e";
            return em.createQuery(query, getEntityClass()).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T selectById(int id) {
        try {
            return em.find(getEntityClass(), id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int count() {
        try {
            String query = "SELECT COUNT(e) FROM " + getEntityClass().getSimpleName() + " e";
            Long count = em.createQuery(query, Long.class).getSingleResult();
            return count != null ? count.intValue() : 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getEntityClass() {
        return (Class<T>) ((java.lang.reflect.ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    public static void closeFactory() {
        if (emFactory != null && emFactory.isOpen()) {
            emFactory.close();
        }
    }
}
