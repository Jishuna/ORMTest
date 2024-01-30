package me.jishuna.ormtest;

import com.zaxxer.hikari.HikariDataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private final HikariDataSource dataSource;

    private ConnectionPool(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = this.dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void close() {
        if (this.dataSource != null && !this.dataSource.isClosed()) {
            this.dataSource.close();
        }
    }

    public static ConnectionPool createSQLite(String name, File folder) {
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String url = "jdbc:sqlite:" + folder.getAbsolutePath() + "/" + name + ".db";

        HikariDataSource source = new HikariDataSource();
        source.setJdbcUrl(url);
        source.setPoolName(name + "-Connection-Pool");

        return new ConnectionPool(source);
    }
}
