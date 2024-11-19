package org.example.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static String jdbcURL = "jdbc:mysql://localhost:3306/computer_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static String username = "root";
    private static String password = "";

    public static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, username, password);
//            System.out.println("Kết nối thành công");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
//            System.out.println("Kết nối thất bại");
        }
        return connection;
    }
}
