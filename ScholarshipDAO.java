/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

import config.DBConnection;
import model.Scholarship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScholarshipDAO {

    // DISPLAY ALL SCHOLARSHIPS
    public List<Scholarship> getAllScholarships() {

        List<Scholarship> scholarships = new ArrayList<>();

        String sql = "SELECT * FROM scholarships";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Scholarship scholarship = new Scholarship(
                    rs.getInt("scholarship_id"),
                    rs.getString("scholarship_name"),
                    rs.getDouble("monthly_allowance")
                );

                scholarships.add(scholarship);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scholarships;
    }

    // ADD SCHOLARSHIP
    public boolean addScholarship(Scholarship scholarship) {

        String sql = "INSERT INTO scholarships "
                + "(scholarship_name, monthly_allowance) "
                + "VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, scholarship.getScholarshipName());
            pst.setDouble(2, scholarship.getMonthlyAllowance());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE SCHOLARSHIP
    public boolean updateScholarship(Scholarship scholarship) {

        String sql = "UPDATE scholarships SET "
                + "scholarship_name=?, monthly_allowance=? "
                + "WHERE scholarship_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, scholarship.getScholarshipName());
            pst.setDouble(2, scholarship.getMonthlyAllowance());
            pst.setInt(3, scholarship.getScholarshipId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE SCHOLARSHIP
    public boolean deleteScholarship(int scholarshipId) {

        String sql = "DELETE FROM scholarships "
                + "WHERE scholarship_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, scholarshipId);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}