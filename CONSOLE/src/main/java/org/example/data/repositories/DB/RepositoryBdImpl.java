package org.example.data.repositories.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.data.core.config.Repository;
import org.example.data.repositories.Model.DataSourceImpl;

public abstract class RepositoryBdImpl<T> extends DataSourceImpl implements Repository<T> {
    public abstract T convertToObject(ResultSet rs) throws SQLException ;
    
}
