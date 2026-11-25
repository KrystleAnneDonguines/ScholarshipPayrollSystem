/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main;

import view.DashboardFrame;

public class Main{
    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(() ->{
           DashboardFrame dashboard = new DashboardFrame();
               dashboard.setVisible(true);
        });
    }
}
