package com.mycompany.kasirakademik_noer_azis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/kasir_akademik_1?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";  // sesuaikan dengan user database Anda
    private static final String PASSWORD = "";  // sesuaikan dengan password database Anda

    // Method untuk mendapatkan koneksi ke database
    public static Connection getConnection() throws SQLException {
        try {
            // Pastikan driver JDBC MySQL sudah ada di library proyek Anda (Connector/J)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC MySQL tidak ditemukan!");
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
