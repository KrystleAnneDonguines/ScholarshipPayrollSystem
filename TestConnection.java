/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


    package main;

    import config.DBConnection;
    import java.sql.Connection;

    public class TestConnection {

        public static void main(String[] args) {

            Connection con = DBConnection.getConnection();

            if (con != null) {
                System.out.println("SUCCESSFULLY CONNECTED TO MYSQL!");
            } else {
                System.out.println("DATABASE CONNECTION FAILED!");
            }
        }
    }
