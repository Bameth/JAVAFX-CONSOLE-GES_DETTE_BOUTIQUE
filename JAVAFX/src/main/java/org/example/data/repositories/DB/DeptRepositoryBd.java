package org.example.data.repositories.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.data.core.interfaces.DeptRepository;
import org.example.data.entities.Dept;
import org.example.data.enums.EtatDette;
import org.example.data.enums.TypeDette;

public class DeptRepositoryBd extends RepositoryBdImpl<Dept> implements DeptRepository {

    @Override
    public boolean insert(Dept objet) {
        try {
            String query = "INSERT INTO \"dept\" (\"montant\", \"montantverser\", \"montantrestant\", \"etat\", \"typedette\", \"date\", \"client_id\") VALUES (?, ?, ?, ?, ?, ?, ?)";
            this.connexion();
            this.initPreparedStatement(query);

            this.pstmt.setDouble(1, objet.getMontant());
            this.pstmt.setDouble(2, objet.getMontantVerser());
            this.pstmt.setDouble(3, objet.getMontantRestant());
            this.pstmt.setString(4, objet.getEtat().toString());
            this.pstmt.setString(5, objet.getTypeDette().toString());
            this.pstmt.setDate(6, java.sql.Date.valueOf(objet.getDate()));
            this.pstmt.setObject(7, (objet.getClient() != null) ? objet.getClient().getId() : null);
            this.executeUpdate();
            ResultSet rs = this.pstmt.getGeneratedKeys();
            if (rs.next()) {
                objet.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error inserting dept: " + e.getMessage());
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
    public boolean update(Dept objet) {
        try {
            String query = "UPDATE \"dept\" SET \"montant\" = ?, \"montantverser\" = ?, \"montantrestant\" = ?, \"etat\" = ?,\"typedette\"= ?, \"date\" = ?, \"client_id\" = ? WHERE \"id\" = ?";
            this.connexion();
            this.initPreparedStatement(query);

            this.pstmt.setDouble(1, objet.getMontant());
            this.pstmt.setDouble(2, objet.getMontantVerser());
            this.pstmt.setDouble(3, objet.getMontantRestant());
            this.pstmt.setString(4, objet.getEtat().toString());
            this.pstmt.setString(5, objet.getTypeDette().toString());
            this.pstmt.setDate(6, java.sql.Date.valueOf(objet.getDate()));
            this.pstmt.setObject(7, (objet.getClient() != null) ? objet.getClient().getId() : null);
            this.pstmt.setInt(8, objet.getId());
            return this.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating dept: " + e.getMessage());
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
            String query = "DELETE FROM \"dept\" WHERE \"id\" = ?";
            this.connexion();
            this.initPreparedStatement(query);

            pstmt.setInt(1, id);
            return this.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting dept: " + e.getMessage());
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
    public List<Dept> selectAll() {
        List<Dept> debts = new ArrayList<>();
        String query = "SELECT * FROM \"dept\"";
        try {
            this.connexion();
            this.initPreparedStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                debts.add(this.convertToObject(rs));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error listing dept: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return debts;
    }

    @Override
    public Dept selectById(int id) {
        Dept dept = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM \"dept\" WHERE \"id\" = ?";
            this.connexion();
            this.initPreparedStatement(query);
            this.pstmt.setInt(1, id);
            rs = this.pstmt.executeQuery();

            if (rs.next()) {
                dept = this.convertToObject(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error selecting dept by ID: " + e.getMessage());
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
        return dept;
    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public Dept convertToObject(ResultSet rs) throws SQLException {
        Dept dept = new Dept();
        dept.setId(rs.getInt("id"));
        dept.setMontant(rs.getDouble("montant"));
        dept.setMontantVerser(rs.getDouble("montantverser"));
        dept.setMontantRestant(rs.getDouble("montantrestant"));
        dept.setDate(rs.getDate("date").toLocalDate());
        dept.setEtat(EtatDette.valueOf(rs.getString("etat")));
        dept.setTypeDette(TypeDette.valueOf(rs.getString("typedette")));
        dept.setClient(new ClientRepositoryBd().selectById(rs.getInt("client_id")));
        return dept;
    }

    @Override
    public List<Dept> selectByClientId(int clientId) {
        List<Dept> debts = new ArrayList<>();
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM \"dept\" WHERE \"client_id\" = ?";
            this.connexion();
            this.initPreparedStatement(query);
            this.pstmt.setInt(1, clientId);
            rs = this.pstmt.executeQuery();

            while (rs.next()) {
                debts.add(this.convertToObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error selecting debts by client ID: " + e.getMessage());
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
        return debts;
    }

    @Override
    public List<Dept> selectAllDeptNonSoldees() {
        List<Dept> debts = new ArrayList<>();
        String query = "SELECT * FROM \"dept\"  WHERE \"etat\" = 'NONSOLDEES'";
        try {
            this.connexion();
            this.initPreparedStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                debts.add(this.convertToObject(rs));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error listing dept NONSOLDEES: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return debts;
    }

    @Override
    public List<Dept> selectAllMyDeptNonSoldees(int clientId) {
        List<Dept> debts = new ArrayList<>();
        String query = "SELECT * FROM \"dept\" WHERE \"etat\" = 'NONSOLDEES' AND \"client_id\" = ?";
        try {
            this.connexion();
            this.initPreparedStatement(query);
            pstmt.setInt(1, clientId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                debts.add(this.convertToObject(rs));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error listing dept NONSOLDEES for client: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return debts;
    }
    @Override
    public List<Dept> selectAllMyDeptAnnuler(int clientId) {
        List<Dept> debts = new ArrayList<>();
        String query = "SELECT * FROM \"dept\" WHERE \"typedette\" = 'ANNULER' AND \"client_id\" = ?";
        try {
            this.connexion();
            this.initPreparedStatement(query);
            pstmt.setInt(1, clientId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                debts.add(this.convertToObject(rs));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error listing dept ANNULER for client: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return debts;
    }

    @Override
    public List<Dept> selectAllMyDept(int clientId) {
        List<Dept> debts = new ArrayList<>();
        String query = "SELECT * FROM \"dept\" WHERE \"client_id\" = ?";
        try {
            this.connexion();
            this.initPreparedStatement(query);
            pstmt.setInt(1, clientId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                debts.add(this.convertToObject(rs));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error listing dept for client: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
        return debts;
    }

    @Override
    public List<Dept> selectByEtat(TypeDette etat) {
        List<Dept> depts = new ArrayList<>();
        String query = "SELECT * FROM \"dept\" WHERE \"typedette\" = ?";
        try (var conn = this.connexion();
                var pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, etat.name());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    depts.add(this.convertToObject(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error selecting depts by state: " + e.getMessage());
        }
        return depts;
    }
}