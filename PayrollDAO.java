/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

import config.DBConnection;
import model.Payroll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PayrollDAO {

    // DISPLAY ALL PAYROLL RECORDS
    public List<Payroll> getAllPayroll() {

        List<Payroll> payrollList = new ArrayList<>();

        String sql = "SELECT * FROM payroll";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Payroll payroll = new Payroll(
                    rs.getInt("payroll_id"),
                    rs.getInt("student_id"),
                    rs.getDate("period_start"),
                    rs.getDate("period_end"),
                    rs.getDouble("amount_due"),
                    rs.getString("payment_status"),
                    rs.getDate("payment_date")
                );

                payrollList.add(payroll);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payrollList;
    }

    // ADD PAYROLL
    public boolean addPayroll(Payroll payroll) {

        String sql = "INSERT INTO payroll "
                + "(student_id, period_start, period_end, "
                + "amount_due, payment_status, payment_date) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, payroll.getStudentId());
            pst.setDate(2, payroll.getPeriodStart());
            pst.setDate(3, payroll.getPeriodEnd());
            pst.setDouble(4, payroll.getAmountDue());
            pst.setString(5, payroll.getPaymentStatus());
            pst.setDate(6, payroll.getPaymentDate());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE PAYMENT STATUS
    public boolean updatePaymentStatus(int payrollId, String status) {

        String sql = "UPDATE payroll SET payment_status=?, "
                + "payment_date=? WHERE payroll_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, status);

            if ("Paid".equalsIgnoreCase(status)) {
                pst.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            } else {
                pst.setNull(2, java.sql.Types.DATE);
            }

            pst.setInt(3, payrollId);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE PAYROLL
    public boolean deletePayroll(int payrollId) {

        String sql = "DELETE FROM payroll WHERE payroll_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, payrollId);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

