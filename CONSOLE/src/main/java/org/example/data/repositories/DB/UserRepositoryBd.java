package org.example.data.repositories.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.data.core.interfaces.UserRepository;
import org.example.data.entities.User;
import org.example.data.enums.Role;
import org.example.data.enums.TypeEtat;

public class UserRepositoryBd extends RepositoryBdImpl<User> implements UserRepository {

    @Override
    public boolean insert(User user) {
        try {
            String query = "INSERT INTO \"user\" (\"name\", \"login\", \"password\", \"role\", \"typeetat\") VALUES (?, ?, ?, ?, ?)";
            this.connexion();
            this.initPreparedStatement(query);

            this.pstmt.setString(1, user.getName());
            this.pstmt.setString(2, user.getLogin());
            this.pstmt.setString(3, user.getPassword());
            this.pstmt.setString(4, user.getRole().name());
            this.pstmt.setString(5, user.getTypeEtat().name());

            this.executeUpdate();
            ResultSet rs = this.pstmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error inserting user: " + e.getMessage());
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
    public boolean update(User user) {
        try {
            String query = "UPDATE \"user\" SET \"name\" = ?, \"login\" = ?, \"password\" = ?, \"role\" = ?, \"typeetat\" = ? WHERE \"id\" = ?";
            this.connexion();
            this.initPreparedStatement(query);

            this.pstmt.setString(1, user.getName());
            this.pstmt.setString(2, user.getLogin());
            this.pstmt.setString(3, user.getPassword());
            this.pstmt.setString(4, user.getRole().name());
            this.pstmt.setString(5, user.getTypeEtat().name());
            this.pstmt.setInt(6, user.getId());

            return this.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
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
            String query = "DELETE FROM \"user\" WHERE \"id\" = ?";
            this.connexion();
            this.initPreparedStatement(query);

            pstmt.setInt(1, id);
            return this.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
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
    public List<User> selectAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM \"user\"";
        try {
            this.connexion();
            this.initPreparedStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(this.convertToObject(rs));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error listing users: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return users;
    }

    @Override
    public User selectById(int id) {
        User user = null;
        String query = "SELECT * FROM \"user\" WHERE \"id\" = ?";
        ResultSet rs = null; 
        try {
            this.connexion();
            this.initPreparedStatement(query);
            this.pstmt.setInt(1, id);
            rs = this.pstmt.executeQuery();
            if (rs.next()) {
                user = this.convertToObject(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error selecting user by ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null)
                    rs.close(); // Fermez le ResultSet ici
                this.closeConnexion(); // Fermez la connexion ici
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return user;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public User selectByName(String name) {
        User user = null;
        try {
            String query = "SELECT * FROM \"user\" WHERE \"name\" = ?";
            this.connexion();
            this.initPreparedStatement(query);
            this.pstmt.setString(1, name);
            ResultSet rs = this.executeQuery();
            if (rs.next()) {
                user = this.convertToObject(rs);
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error selecting user by name: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return user;
    }

    @Override
    public List<User> selectByEtat(TypeEtat etat) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM \"user\" WHERE \"typeetat\" = ?";
        try (var conn = this.connexion();
                var pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, etat.name());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    users.add(this.convertToObject(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error selecting users by state: " + e.getMessage());
        }
        return users;
    }

    @Override
    public List<User> selectByRole(Role role) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM \"user\" WHERE \"role\" = ?";
        try (var conn = this.connexion();
                var pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, role.name());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    users.add(this.convertToObject(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error selecting users by role: " + e.getMessage());
        }
        return users;
    }

    @Override
    public User convertToObject(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setRole(Role.valueOf(rs.getString("role")));
        user.setTypeEtat(TypeEtat.valueOf(rs.getString("typeetat")));
        return user;
    }

    @Override
    public User selectByLogin(String login) {
        User user = null;
        String query = "SELECT * FROM \"user\" WHERE \"login\" = ?";
        try {
            this.connexion();
            this.initPreparedStatement(query);
            this.pstmt.setString(1, login);
            ResultSet rs = this.pstmt.executeQuery();
            if (rs.next()) {
                user = this.convertToObject(rs);
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error selecting user by login: " + e.getMessage());
        } finally {
            this.closeConnexion();
        }
        return user;
    }
}
