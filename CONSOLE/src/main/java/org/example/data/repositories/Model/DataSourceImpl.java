package org.example.data.repositories.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSourceImpl implements DataSource {
    private Connection connection = null;
    protected PreparedStatement pstmt;
    // String url = "jdbc:postgresql://localhost:5432/ges_dette_boutique";
    String url = "jdbc:postgresql://localhost:5432/ges_dette";
    String user = "postgres";
    String password = "Rd5jm7qshp"; 
    protected String table;

    // Establish a connection to the PostgreSQL database
    @Override
    public Connection connexion() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Close the current connection
    @Override
    public void closeConnexion() {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Execute a SELECT query and return the result set
    @Override
    public ResultSet executeQuery() throws SQLException {
        try (PreparedStatement pstmt = this.pstmt;
                ResultSet rs = pstmt.executeQuery()) {
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void initPreparedStatement(String sql) throws SQLException {
        // INSERT
        if (sql.toUpperCase().trim().startsWith("INSERT")) {
            pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        } else {
            pstmt = connection.prepareStatement(sql);
        }
    }

    // Execute an INSERT, UPDATE, or DELETE query
    @Override
    public int executeUpdate() throws SQLException {
        return pstmt.executeUpdate();
    }

    // Generate an SQL query (for complex or dynamic queries)
    @Override
    public String generateSql(String tableName, String[] fields, String condition) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(String.join(", ", fields));
        sql.append(" FROM ").append(tableName);
        if (condition != null && !condition.isEmpty()) {
            sql.append(" WHERE ").append(condition);
        }
        return sql.toString();
    }

    // Set fields for SQL operations like INSERT or UPDATE
    @Override
    public void setFields(Object[] fields) {
        try {
            for (int i = 0; i < fields.length; i++) {
                if (fields[i] instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) fields[i]);
                } else if (fields[i] instanceof String) {
                    pstmt.setString(i + 1, (String) fields[i]);
                } else if (fields[i] instanceof Double) {
                    pstmt.setDouble(i + 1, (Double) fields[i]);
                } else if (fields[i] instanceof Boolean) {
                    pstmt.setBoolean(i + 1, (Boolean) fields[i]);
                } else {
                    pstmt.setObject(i + 1, fields[i]);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
