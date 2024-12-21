package org.example.data.repositories.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.List;

import org.example.data.core.config.Repository;
import org.example.data.repositories.Model.DataSourceImpl;

public abstract class RepositoryBdImpl<T> extends DataSourceImpl implements Repository<T> {
    public abstract T convertToObject(ResultSet rs) throws SQLException ;
    
}
