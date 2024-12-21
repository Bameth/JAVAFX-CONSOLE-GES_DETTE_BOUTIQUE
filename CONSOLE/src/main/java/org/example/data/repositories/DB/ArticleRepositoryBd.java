package org.example.data.repositories.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.data.core.interfaces.ArticleRepository;
import org.example.data.entities.Article;

public class ArticleRepositoryBd extends RepositoryBdImpl<Article> implements ArticleRepository {

    @Override
    public boolean insert(Article article) {
        try {
            String query = "INSERT INTO \"article\" (\"libelle\", \"prix\", \"qtestock\") VALUES (?, ?, ?)";
            this.connexion();
            this.initPreparedStatement(query);

            pstmt.setString(1, article.getLibelle());
            pstmt.setDouble(2, article.getPrix());
            pstmt.setInt(3, article.getQteStock());
            this.executeUpdate();

            ResultSet rs = this.pstmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                article.setId(generatedId);
                String reference = generateReference(generatedId);
                article.setReference(reference);
                updateReference(generatedId, reference);
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error inserting article: " + e.getMessage());
            return false;
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }

    public String generateReference(int id) {
        return "ART" + "0".repeat(4 - String.valueOf(id).length()) + id;
    }

    private void updateReference(int id, String reference) throws SQLException {
        String updateQuery = "UPDATE \"article\" SET \"reference\" = ? WHERE \"id\" = ?";
        this.initPreparedStatement(updateQuery);
        pstmt.setString(1, reference);
        pstmt.setInt(2, id);
        pstmt.executeUpdate();
    }

    @Override
    public boolean update(Article article) {
        try {
            String query = "UPDATE \"article\" SET \"reference\" = ?, \"libelle\" = ?, \"prix\" = ?, \"qtestock\" = ? WHERE \"id\" = ?";
            this.connexion();
            this.initPreparedStatement(query);
            pstmt.setString(1, article.getReference());
            pstmt.setString(2, article.getLibelle());
            pstmt.setDouble(3, article.getPrix());
            pstmt.setInt(4, article.getQteStock());
            pstmt.setInt(5, article.getId());
            return this.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating article: " + e.getMessage());
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
        try {
            String query = "DELETE FROM \"article\" WHERE \"id\" = ?";
            this.connexion();
            this.initPreparedStatement(query);

            pstmt.setInt(1, id);
            return this.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting article: " + e.getMessage());
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
    public List<Article> selectAll() {
        List<Article> articles = new ArrayList<>();
        try {
            String query = "SELECT * FROM \"article\"";

            this.connexion();
            this.initPreparedStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                articles.add(this.convertToObject(rs));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error listing articles: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return articles;
    }

    @Override
    public Article selectById(int id) {
        Article article = null;
        try {
            String query = "SELECT * FROM \"article\" WHERE \"id\" = ?";
            this.connexion();
            this.initPreparedStatement(query);
            this.pstmt.setInt(1, id);
            ResultSet rs = this.executeQuery();
            if (rs.next()) {
                article = this.convertToObject(rs);
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error selecting article by ID: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public int count() {
        try {
            String query = "SELECT COUNT(*) FROM \"article\"";
            this.connexion();
            this.initPreparedStatement(query);
            ResultSet rs = this.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error counting users: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return 0;
    }

    @Override
    public List<Article> selectByAvailability() {
        List<Article> articles = new ArrayList<>();
        try {
            String query = "SELECT * FROM \"article\" WHERE \"qtestock\" > 0";
            this.connexion();
            this.initPreparedStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                articles.add(this.convertToObject(rs));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error fetching available articles: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return articles;
    }

    @Override
    public Article selectByReference(String reference) {
        Article article = null;
        try {
            String query = "SELECT * FROM \"article\" WHERE \"reference\" = ?";
            this.connexion();
            this.initPreparedStatement(query);
            this.pstmt.setString(1, reference);
            ResultSet rs = this.pstmt.executeQuery();
            if (rs.next()) {
                article = this.convertToObject(rs);
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error fetching article by reference: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return article;
    }

    @Override
    public Article convertToObject(ResultSet rs) throws SQLException {
        Article article = new Article();
        article.setId(rs.getInt("id"));
        article.setReference(rs.getString("reference"));
        article.setLibelle(rs.getString("libelle"));
        article.setPrix(rs.getDouble("prix"));
        article.setQteStock(rs.getInt("qtestock"));
        return article;

    }
}
