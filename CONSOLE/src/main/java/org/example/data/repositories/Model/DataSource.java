package org.example.data.repositories.Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataSource {

    Connection connexion();

    void closeConnexion();

    ResultSet executeQuery() throws SQLException;
    
    void initPreparedStatement(String sql) throws SQLException;

    int executeUpdate() throws SQLException;

    // Generate an SQL query (for complex or dynamic queries)
    String generateSql(String tableName, String[] fields, String condition);
    // Set fields for SQL operations, like INSERT or UPDATE
    void setFields(Object[] fields);
}
