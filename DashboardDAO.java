/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

import config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardDAO {

    public int getTotalStudents() {
        return getCount("SELECT COUNT(*) FROM students");
    }

    public int getTotalScholarships() {
        return getCount("SELECT COUNT(*) FROM scholarships");
    }

    public int getTotalPayroll() {
        return getCount("SELECT COUNT(*) FROM payroll");
    }

    private int getCount(String sql) {
        int count = 0;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()
        ) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
}
