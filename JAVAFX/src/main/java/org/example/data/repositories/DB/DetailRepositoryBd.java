package org.example.data.repositories.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.data.core.interfaces.DetailRepository;
import org.example.data.entities.Detail;
import org.example.data.entities.User;
import org.example.data.enums.Role;
import org.example.data.enums.TypeEtat;

public class DetailRepositoryBd extends RepositoryBdImpl<Detail> implements DetailRepository {

    @Override
    public boolean insert(Detail objet) {
        String query = "INSERT INTO \"detail\" (\"qte\", \"article_id\", \"dept_id\") VALUES (?, ?, ?)";
        try {
            this.connexion();
            this.initPreparedStatement(query);

            this.pstmt.setInt(1, objet.getQte());
            this.pstmt.setObject(2, (objet.getArticle() != null) ? objet.getArticle().getId() : null);

            if (objet.getDept() != null) {
                this.pstmt.setInt(3, objet.getDept().getId());
            } else {
                System.err.println("dept_id est null, insertion échouée.");
                return false; 
            }

            int affectedRows = this.executeUpdate();
            ResultSet rs = this.pstmt.getGeneratedKeys();
            if (rs.next()) {
                objet.setId(rs.getInt(1));
            }
            rs.close();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting detail: " + e.getMessage());
            return false;
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }

    @Override
    public boolean update(Detail objet) {
        try {
            String query = "UPDATE \"detail\" SET \"qte\" = ?, \"article_id\" = ?, \"dept_id\" = ? WHERE \"id\" = ?";
            this.connexion();
            this.initPreparedStatement(query);

            this.pstmt.setInt(1, objet.getQte());
            this.pstmt.setObject(2, (objet.getArticle() != null) ? objet.getArticle().getId() : null);
            this.pstmt.setObject(3, (objet.getDept() != null) ? objet.getDept().getId() : null);
            pstmt.setInt(4, objet.getId());
            return this.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating detail: " + e.getMessage());
            return false;
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }

    @Override
    public boolean delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Detail> selectAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectAll'");
    }

    @Override
    public Detail selectById(int id) {
        Detail detail = null;
        String query = "SELECT * FROM \"detail\" WHERE \"id\" = ?";
        ResultSet rs = null;
        try {
            this.connexion();
            this.initPreparedStatement(query);
            this.pstmt.setInt(1, id);
            rs = this.pstmt.executeQuery();
            if (rs.next()) {
                detail = this.convertToObject(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error selecting detail by ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null)
                    rs.close();
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return detail;
    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public Detail convertToObject(ResultSet rs) throws SQLException {
        Detail detail = new Detail();
        detail.setId(rs.getInt("id"));
        detail.setQte(rs.getInt("qte"));
        detail.setArticle(new ArticleRepositoryBd().selectById(rs.getInt("article_id")));
        detail.setDept(new DeptRepositoryBd().selectById(rs.getInt("dept_id")));
        return detail;
    }

    public List<Detail> selectByDeptId(int deptId) {
        List<Detail> details = new ArrayList<>();
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM \"detail\" WHERE \"dept_id\" = ?";
            this.connexion();
            this.initPreparedStatement(query);
            this.pstmt.setInt(1, deptId);
            rs = this.pstmt.executeQuery();

            while (rs.next()) {
                details.add(this.convertToObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error selecting details by dept ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return details;
    }
}