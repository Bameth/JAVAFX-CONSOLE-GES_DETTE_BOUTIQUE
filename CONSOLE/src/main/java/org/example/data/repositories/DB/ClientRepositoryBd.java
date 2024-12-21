package org.example.data.repositories.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.example.data.core.interfaces.ClientRepository;
import org.example.data.entities.Client;

public class ClientRepositoryBd extends RepositoryBdImpl<Client> implements ClientRepository {

    @Override
    public Client convertToObject(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getInt("id"));
        client.setSurname(rs.getString("surname"));
        client.setPhone(rs.getString("phone"));
        client.setAddress(rs.getString("adresse"));
        client.setUser(new UserRepositoryBd().selectById(rs.getInt("user_id")));
        return client;
    }

    @Override
    public boolean insert(Client client) {
        String query = "INSERT INTO \"client\" (\"surname\", \"phone\", \"adresse\", \"user_id\") VALUES (?, ?, ?, ?)";
        try {
            this.connexion();
            this.initPreparedStatement(query);

            this.pstmt.setString(1, client.getSurname());
            this.pstmt.setString(2, client.getPhone());
            this.pstmt.setString(3, client.getAddress());
            this.pstmt.setObject(4, (client.getUser() != null) ? client.getUser().getId() : null);

            int affectedRows = this.executeUpdate();
            ResultSet rs = this.pstmt.getGeneratedKeys();
            if (rs.next()) {
                client.setId(rs.getInt(1));
            }
            rs.close();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting client: " + e.getMessage());
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
    public boolean update(Client client) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Client> selectAll() {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM \"client\"";
        try {
            this.connexion();
            this.initPreparedStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                clients.add(this.convertToObject(rs));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error listing clients: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return clients;
    }

    @Override
    public Client selectById(int id) {
        String query = "SELECT * FROM \"client\" WHERE \"id\" = ?";
        Client client = null;
        try {
            this.connexion();
            this.initPreparedStatement(query);
            this.pstmt.setInt(1, id);
            ResultSet rs = this.pstmt.executeQuery();
            if (rs.next()) {
                client = this.convertToObject(rs);
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error selecting client by id: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return client;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public Client selectByPhone(String phone) {
        String query = "SELECT * FROM \"client\" WHERE \"phone\" = ?";
        Client client = null;
        try {
            this.connexion();
            this.initPreparedStatement(query);
            this.pstmt.setString(1, phone);
            ResultSet rs = this.pstmt.executeQuery();
            if (rs.next()) {
                client = this.convertToObject(rs);
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error selecting client by phone: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return client;
    }

    @Override
    public List<Client> findAllClientWithAccount() {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT c.* FROM \"client\" c JOIN \"user\" u ON c.\"user_id\" = u.\"id\"";
        try {
            this.connexion();
            this.initPreparedStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                clients.add(this.convertToObject(rs));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error listing clients with accounts: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return clients;
    }

    @Override
    public Client selectByUserId(int userId) {
        String query = "SELECT * FROM \"client\" WHERE \"user_id\" = ?";
        Client client = null;
        try {
            this.connexion();
            this.initPreparedStatement(query);
            this.pstmt.setInt(1, userId);
            ResultSet rs = this.pstmt.executeQuery();
            if (rs.next()) {
                client = this.convertToObject(rs);
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error selecting client by user id: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return client;
    }

}
