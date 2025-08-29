package org.example.adapters;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataBaseAdapter {

        Connection getConnection() throws SQLException;
        String getDatabaseType();
        void closeConnection();


}
