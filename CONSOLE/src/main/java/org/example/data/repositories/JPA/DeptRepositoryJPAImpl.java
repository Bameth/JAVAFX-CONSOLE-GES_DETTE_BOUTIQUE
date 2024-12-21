package org.example.data.repositories.JPA;

import java.util.List;
import javax.persistence.TypedQuery;
import org.example.data.core.config.RepositoryJPAImpl;
import org.example.data.core.interfaces.DeptRepository;
import org.example.data.entities.Dept;
import org.example.data.enums.EtatDette;
import org.example.data.enums.TypeDette;

public class DeptRepositoryJPAImpl extends RepositoryJPAImpl<Dept> implements DeptRepository {
    private static final String CLIENT_ID_PARAM = "client_id";

    public DeptRepositoryJPAImpl() {
        super(Dept.class);
    }

    @Override
    public List<Dept> selectByClientId(int clientId) {
        try {
            String query = "SELECT d FROM Dept d WHERE d.client.id = :" + CLIENT_ID_PARAM;
            TypedQuery<Dept> typedQuery = em.createQuery(query, Dept.class);
            typedQuery.setParameter(CLIENT_ID_PARAM, clientId);
            return typedQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Dept> selectAllMyDeptNonSoldees(int clientId) {
        try {
            String query = "SELECT d FROM Dept d WHERE d.client.id = :client_id AND d.etat = :NONSOLDEES";
            TypedQuery<Dept> typedQuery = em.createQuery(query, Dept.class);
            typedQuery.setParameter(CLIENT_ID_PARAM, clientId);
            typedQuery.setParameter("NONSOLDEES", EtatDette.NONSOLDEES);
            return typedQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Dept> selectAllDeptNonSoldees() {
        try {
            String query = "SELECT d FROM Dept d WHERE d.etat = :NONSOLDEES";
            TypedQuery<Dept> typedQuery = em.createQuery(query, Dept.class);
            typedQuery.setParameter("NONSOLDEES", EtatDette.NONSOLDEES);
            return typedQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Dept> selectAllMyDept(int clientId) {
        try {
            String query = "SELECT d FROM Dept d WHERE d.client.id = :client_id";
            TypedQuery<Dept> typedQuery = em.createQuery(query, Dept.class);
            typedQuery.setParameter(CLIENT_ID_PARAM, clientId);            
            return typedQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Dept> selectByEtat(TypeDette etat) {
        try {
            String query = "SELECT d FROM Dept d WHERE d.typeDette = :typedette";
            TypedQuery<Dept> typedQuery = em.createQuery(query, Dept.class);
            typedQuery.setParameter("typedette", etat);
            return typedQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Dept> selectAllMyDeptAnnuler(int clientId) {
        try {
            String query = "SELECT d FROM Dept d WHERE d.client.id = :client_id AND d.typeDette = :ANNULER";
            TypedQuery<Dept> typedQuery = em.createQuery(query, Dept.class);
            typedQuery.setParameter(CLIENT_ID_PARAM, clientId);            
            typedQuery.setParameter("ANNULER", TypeDette.ANNULER);
            return typedQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
