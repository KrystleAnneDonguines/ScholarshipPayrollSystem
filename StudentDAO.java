/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

import config.DBConnection;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // DISPLAY ALL STUDENTS
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        String sql = "SELECT * FROM students";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("student_id"),
                    rs.getString("school_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("course"),
                    rs.getInt("year_level"),
                    rs.getInt("scholarship_id")
                );

                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    // ADD STUDENT
    public boolean addStudent(Student student) {

        String sql = "INSERT INTO students "
                + "(school_id, first_name, last_name, course, "
                + "year_level, scholarship_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, student.getSchoolId());
            pst.setString(2, student.getFirstName());
            pst.setString(3, student.getLastName());
            pst.setString(4, student.getCourse());
            pst.setInt(5, student.getYearLevel());
            pst.setInt(6, student.getScholarshipId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE STUDENT
    public boolean updateStudent(Student student) {

        String sql = "UPDATE students SET school_id=?, "
                + "first_name=?, last_name=?, course=?, "
                + "year_level=?, scholarship_id=? "
                + "WHERE student_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, student.getSchoolId());
            pst.setString(2, student.getFirstName());
            pst.setString(3, student.getLastName());
            pst.setString(4, student.getCourse());
            pst.setInt(5, student.getYearLevel());
            pst.setInt(6, student.getScholarshipId());
            pst.setInt(7, student.getStudentId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE STUDENT
    public boolean deleteStudent(int studentId) {

        String sql = "DELETE FROM students WHERE student_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, studentId);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

