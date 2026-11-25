/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
        "jdbc:mysql://localhost:3307/scholarship_payroll";

    private static final String USER = "root";

    private static final String PASSWORD = "database@00";

    public static Connection getConnection() {

        try {
            Connection con = DriverManager.getConnection(
                URL, USER, PASSWORD
            );

            return con;

        } catch (SQLException e) {

            System.out.println("Database Connection Failed!");
            System.out.println(e.getMessage());

            return null;
        }
    }
}