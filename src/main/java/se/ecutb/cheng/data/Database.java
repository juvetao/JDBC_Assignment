package se.ecutb.cheng.data;


import java.io.FileInputStream;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    //If you really want to expose your password use this;
    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("" +
                        "jdbc:mysql://localhost:3306/world?&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Berlin",
                "root",
                "AjaCZs8f"
        );
        return connection;
    }

}