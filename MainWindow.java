/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package view;

import config.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MainWindow extends JFrame {

    private JTabbedPane tabs;

    private JTable scholarshipTable, studentTable, payrollTable;
    private DefaultTableModel scholarshipModel, studentModel, payrollModel;

    private JTextField scholarshipName, allowance;
    private JTextField schoolId, firstName, lastName, course, yearLevel;

    private JComboBox<ScholarshipItem> scholarshipCombo;
    private JComboBox<StudentItem> studentCombo;

    private JTextField periodStart, periodEnd;

    public MainWindow() {
        setTitle("Scholarship Payroll System");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabs = new JTabbedPane();

        tabs.addTab("Dashboard", dashboardPanel());
        tabs.addTab("Scholarships", scholarshipPanel());
        tabs.addTab("Students", studentPanel());
        tabs.addTab("Payroll", payrollPanel());
        tabs.addTab("Reports", reportsPanel());

        add(tabs);

        refreshAll();
    }

    // DATABASE CONNECTION CHECK
    private Connection connect() throws SQLException {
        Connection con = DBConnection.getConnection();

        if (con == null) {
            throw new SQLException("Cannot connect to database.");
        }

        return con;
    }

    // DASHBOARD
    private JPanel dashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("SCHOLARSHIP PAYROLL SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 27));

        JLabel description = new JLabel(
            "<html><h2>Welcome!</h2>" +
            "Manage scholarships, students, and payroll records here.</html>"
        );

        JButton refresh = new JButton("Refresh Dashboard");
        refresh.addActionListener(e -> refreshAll());

        panel.add(title, BorderLayout.NORTH);
        panel.add(description, BorderLayout.CENTER);
        panel.add(refresh, BorderLayout.SOUTH);

        return panel;
    }

    // SCHOLARSHIP MANAGEMENT
    private JPanel scholarshipPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        scholarshipName = new JTextField(20);
        allowance = new JTextField(20);

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Scholarship Information"));

        form.add(new JLabel("Scholarship Name:"));
        form.add(scholarshipName);
        form.add(new JLabel("Monthly Allowance:"));
        form.add(allowance);

        JButton add = new JButton("Add");
        JButton update = new JButton("Update");
        JButton delete = new JButton("Delete");
        JButton clear = new JButton("Clear");

        JPanel buttons = new JPanel();
        buttons.add(add);
        buttons.add(update);
        buttons.add(delete);
        buttons.add(clear);

        scholarshipModel = new DefaultTableModel(
            new String[]{"ID", "Scholarship Name", "Monthly Allowance"}, 0
        );

        scholarshipTable = new JTable(scholarshipModel);
        scholarshipTable.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        scholarshipTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()
                    && scholarshipTable.getSelectedRow() != -1) {
                int r = scholarshipTable.getSelectedRow();
                scholarshipName.setText(
                    scholarshipModel.getValueAt(r, 1).toString()
                );
                allowance.setText(
                    scholarshipModel.getValueAt(r, 2).toString()
                );
            }
        });

        add.addActionListener(e -> addScholarship());
        update.addActionListener(e -> updateScholarship());
        delete.addActionListener(e -> deleteScholarship());
        clear.addActionListener(e -> clearScholarship());

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(scholarshipTable), BorderLayout.CENTER);

        return panel;
    }

    private void addScholarship() {
        try {
            String name = scholarshipName.getText().trim();
            double amount = Double.parseDouble(allowance.getText().trim());

            if (name.isEmpty() || amount < 0) {
                throw new IllegalArgumentException();
            }

            String sql = "INSERT INTO scholarships "
                    + "(scholarship_name, monthly_allowance) VALUES (?, ?)";

            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, name);
                ps.setDouble(2, amount);
                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Scholarship added!");
            clearScholarship();
            refreshAll();

        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void updateScholarship() {
        int row = scholarshipTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a scholarship first.");
            return;
        }

        try {
            String name = scholarshipName.getText().trim();
            double amount = Double.parseDouble(allowance.getText().trim());
            int id = Integer.parseInt(
                scholarshipModel.getValueAt(row, 0).toString()
            );

            if (name.isEmpty() || amount < 0) {
                throw new IllegalArgumentException();
            }

            String sql = "UPDATE scholarships SET scholarship_name=?, "
                    + "monthly_allowance=? WHERE scholarship_id=?";

            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, name);
                ps.setDouble(2, amount);
                ps.setInt(3, id);
                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Scholarship updated!");
            clearScholarship();
            refreshAll();

        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void deleteScholarship() {
        int row = scholarshipTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a scholarship first.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this, "Delete this scholarship?", "Confirm",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int id = Integer.parseInt(
                scholarshipModel.getValueAt(row, 0).toString()
            );

            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement(
                     "DELETE FROM scholarships WHERE scholarship_id=?"
                 )) {

                ps.setInt(1, id);
                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Scholarship deleted!");
            clearScholarship();
            refreshAll();

        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void clearScholarship() {
        scholarshipName.setText("");
        allowance.setText("");
        scholarshipTable.clearSelection();
    }

    // STUDENT MANAGEMENT
    private JPanel studentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        schoolId = new JTextField();
        firstName = new JTextField();
        lastName = new JTextField();
        course = new JTextField();
        yearLevel = new JTextField();

        scholarshipCombo = new JComboBox<>();

        JPanel form = new JPanel(new GridLayout(6, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Student Information"));

        form.add(new JLabel("School ID:"));
        form.add(schoolId);
        form.add(new JLabel("First Name:"));
        form.add(firstName);
        form.add(new JLabel("Last Name:"));
        form.add(lastName);
        form.add(new JLabel("Course:"));
        form.add(course);
        form.add(new JLabel("Year Level:"));
        form.add(yearLevel);
        form.add(new JLabel("Scholarship:"));
        form.add(scholarshipCombo);

        JButton add = new JButton("Add");
        JButton update = new JButton("Update");
        JButton delete = new JButton("Delete");
        JButton clear = new JButton("Clear");

        JPanel buttons = new JPanel();
        buttons.add(add);
        buttons.add(update);
        buttons.add(delete);
        buttons.add(clear);

        studentModel = new DefaultTableModel(
            new String[]{"ID", "School ID", "First Name", "Last Name",
                "Course", "Year Level", "Scholarship"}, 0
        );

        studentTable = new JTable(studentModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()
                    && studentTable.getSelectedRow() != -1) {

                int r = studentTable.getSelectedRow();

                schoolId.setText(studentModel.getValueAt(r, 1).toString());
                firstName.setText(studentModel.getValueAt(r, 2).toString());
                lastName.setText(studentModel.getValueAt(r, 3).toString());
                course.setText(studentModel.getValueAt(r, 4).toString());
                yearLevel.setText(studentModel.getValueAt(r, 5).toString());

                String selected = studentModel.getValueAt(r, 6).toString();

                for (int i = 0; i < scholarshipCombo.getItemCount(); i++) {
                    if (scholarshipCombo.getItemAt(i).toString()
                            .equals(selected)) {
                        scholarshipCombo.setSelectedIndex(i);
                        break;
                    }
                }
            }
        });

        add.addActionListener(e -> addStudent());
        update.addActionListener(e -> updateStudent());
        delete.addActionListener(e -> deleteStudent());
        clear.addActionListener(e -> clearStudent());

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(studentTable), BorderLayout.CENTER);

        return panel;
    }

    private void addStudent() {
        try {
            String id = schoolId.getText().trim();
            String first = firstName.getText().trim();
            String last = lastName.getText().trim();
            String crs = course.getText().trim();
            int year = Integer.parseInt(yearLevel.getText().trim());

            ScholarshipItem sc =
                (ScholarshipItem) scholarshipCombo.getSelectedItem();

            if (id.isEmpty() || first.isEmpty() || last.isEmpty()
                    || crs.isEmpty() || year < 1 || sc == null) {
                throw new IllegalArgumentException();
            }

            String sql = "INSERT INTO students "
                    + "(school_id, first_name, last_name, course, "
                    + "year_level, scholarship_id) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, id);
                ps.setString(2, first);
                ps.setString(3, last);
                ps.setString(4, crs);
                ps.setInt(5, year);
                ps.setInt(6, sc.id);
                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Student added!");
            clearStudent();
            refreshAll();

        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void updateStudent() {
        int row = studentTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a student first.");
            return;
        }

        try {
            int id = Integer.parseInt(
                studentModel.getValueAt(row, 0).toString()
            );

            ScholarshipItem sc =
                (ScholarshipItem) scholarshipCombo.getSelectedItem();

            if (sc == null) throw new IllegalArgumentException();

            String sql = "UPDATE students SET school_id=?, first_name=?, "
                    + "last_name=?, course=?, year_level=?, scholarship_id=? "
                    + "WHERE student_id=?";

            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, schoolId.getText().trim());
                ps.setString(2, firstName.getText().trim());
                ps.setString(3, lastName.getText().trim());
                ps.setString(4, course.getText().trim());
                ps.setInt(5, Integer.parseInt(yearLevel.getText().trim()));
                ps.setInt(6, sc.id);
                ps.setInt(7, id);
                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Student updated!");
            clearStudent();
            refreshAll();

        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void deleteStudent() {
        int row = studentTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a student first.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this, "Delete this student?", "Confirm",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int id = Integer.parseInt(
                studentModel.getValueAt(row, 0).toString()
            );

            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement(
                     "DELETE FROM students WHERE student_id=?"
                 )) {

                ps.setInt(1, id);
                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Student deleted!");
            clearStudent();
            refreshAll();

        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void clearStudent() {
        schoolId.setText("");
        firstName.setText("");
        lastName.setText("");
        course.setText("");
        yearLevel.setText("");
        studentTable.clearSelection();
        if (scholarshipCombo.getItemCount() > 0) {
            scholarshipCombo.setSelectedIndex(0);
        }
    }

    // PAYROLL MANAGEMENT
    private JPanel payrollPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        studentCombo = new JComboBox<>();
        periodStart = new JTextField("2026-11-01");
        periodEnd = new JTextField("2026-11-30");

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Payroll Information"));

        form.add(new JLabel("Student:"));
        form.add(studentCombo);
        form.add(new JLabel("Period Start (YYYY-MM-DD):"));
        form.add(periodStart);
        form.add(new JLabel("Period End (YYYY-MM-DD):"));
        form.add(periodEnd);

        JButton generate = new JButton("Generate Payroll");
        JButton paid = new JButton("Mark as Paid");
        JButton refresh = new JButton("Refresh");

        JPanel buttons = new JPanel();
        buttons.add(generate);
        buttons.add(paid);
        buttons.add(refresh);

        payrollModel = new DefaultTableModel(
            new String[]{"Payroll ID", "Student", "Start Date", "End Date",
                "Amount Due", "Status", "Payment Date"}, 0
        );

        payrollTable = new JTable(payrollModel);
        payrollTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        generate.addActionListener(e -> generatePayroll());
        paid.addActionListener(e -> markAsPaid());
        refresh.addActionListener(e -> loadPayroll());

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(payrollTable), BorderLayout.CENTER);

        return panel;
    }

    private void generatePayroll() {
        try {
            StudentItem student =
                (StudentItem) studentCombo.getSelectedItem();

            if (student == null) {
                JOptionPane.showMessageDialog(this, "Select a student.");
                return;
            }

            Date start = Date.valueOf(periodStart.getText().trim());
            Date end = Date.valueOf(periodEnd.getText().trim());

            if (end.before(start)) {
                JOptionPane.showMessageDialog(
                    this, "End date must not be before start date."
                );
                return;
            }

            String sql = "INSERT INTO payroll "
                    + "(student_id, period_start, period_end, amount_due) "
                    + "SELECT s.student_id, ?, ?, sc.monthly_allowance "
                    + "FROM students s "
                    + "JOIN scholarships sc "
                    + "ON s.scholarship_id = sc.scholarship_id "
                    + "WHERE s.student_id = ?";

            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setDate(1, start);
                ps.setDate(2, end);
                ps.setInt(3, student.id);
                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Payroll generated!");
            loadPayroll();

        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void markAsPaid() {
        int row = payrollTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a payroll record.");
            return;
        }

        try {
            int id = Integer.parseInt(
                payrollModel.getValueAt(row, 0).toString()
            );

            String sql = "UPDATE payroll SET payment_status='Paid', "
                    + "payment_date=CURRENT_DATE "
                    + "WHERE payroll_id=? AND payment_status='Pending'";

            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                int updated = ps.executeUpdate();

                if (updated == 0) {
                    JOptionPane.showMessageDialog(
                        this, "This payroll is already paid."
                    );
                } else {
                    JOptionPane.showMessageDialog(
                        this, "Payment marked as Paid!"
                    );
                }
            }

            loadPayroll();

        } catch (Exception ex) {
            showError(ex);
        }
    }

    // REPORTS
    private JPanel reportsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JButton display = new JButton("Display Reports");
        JButton print = new JButton("Print Report");

        JPanel buttons = new JPanel();
        buttons.add(display);
        buttons.add(print);

        DefaultTableModel reportModel = new DefaultTableModel(
            new String[]{"School ID", "Student Name", "Scholarship",
                "Allowance", "Payroll Status"}, 0
        );

        JTable reportTable = new JTable(reportModel);

        display.addActionListener(e -> {
            reportModel.setRowCount(0);

            String sql = "SELECT s.school_id, "
                    + "CONCAT(s.first_name, ' ', s.last_name), "
                    + "sc.scholarship_name, sc.monthly_allowance, "
                    + "COALESCE(p.payment_status, 'No Payroll') "
                    + "FROM students s "
                    + "JOIN scholarships sc "
                    + "ON s.scholarship_id=sc.scholarship_id "
                    + "LEFT JOIN payroll p ON s.student_id=p.student_id";

            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    reportModel.addRow(new Object[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getString(5)
                    });
                }

            } catch (Exception ex) {
                showError(ex);
            }
        });

        print.addActionListener(e -> {
            try {
                reportTable.print();
            } catch (Exception ex) {
                showError(ex);
            }
        });

        panel.add(buttons, BorderLayout.NORTH);
        panel.add(new JScrollPane(reportTable), BorderLayout.CENTER);

        return panel;
    }

    // LOAD SCHOLARSHIPS
    private void loadScholarships() {
        scholarshipModel.setRowCount(0);

        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT * FROM scholarships"
             );
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                scholarshipModel.addRow(new Object[]{
                    rs.getInt("scholarship_id"),
                    rs.getString("scholarship_name"),
                    rs.getDouble("monthly_allowance")
                });
            }

        } catch (Exception ex) {
            showError(ex);
        }
    }

    // LOAD STUDENTS
    private void loadStudents() {
        studentModel.setRowCount(0);

        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT s.*, sc.scholarship_name "
                 + "FROM students s JOIN scholarships sc "
                 + "ON s.scholarship_id=sc.scholarship_id"
             );
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                studentModel.addRow(new Object[]{
                    rs.getInt("student_id"),
                    rs.getString("school_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("course"),
                    rs.getInt("year_level"),
                    rs.getString("scholarship_name")
                });
            }

        } catch (Exception ex) {
            showError(ex);
        }
    }

    // LOAD PAYROLL
    private void loadPayroll() {
        payrollModel.setRowCount(0);

        String sql = "SELECT p.payroll_id, "
                + "CONCAT(s.first_name, ' ', s.last_name) AS student, "
                + "p.period_start, p.period_end, p.amount_due, "
                + "p.payment_status, p.payment_date "
                + "FROM payroll p JOIN students s "
                + "ON p.student_id=s.student_id "
                + "ORDER BY p.payroll_id";

        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                payrollModel.addRow(new Object[]{
                    rs.getInt("payroll_id"),
                    rs.getString("student"),
                    rs.getDate("period_start"),
                    rs.getDate("period_end"),
                    rs.getDouble("amount_due"),
                    rs.getString("payment_status"),
                    rs.getDate("payment_date")
                });
            }

        } catch (Exception ex) {
            showError(ex);
        }
    }

    // LOAD COMBO BOXES
    private void loadComboBoxes() {
        scholarshipCombo.removeAllItems();
        studentCombo.removeAllItems();

        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT scholarship_id, scholarship_name FROM scholarships"
             );
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                scholarshipCombo.addItem(new ScholarshipItem(
                    rs.getInt(1), rs.getString(2)
                ));
            }

        } catch (Exception ex) {
            showError(ex);
        }

        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT student_id, school_id, first_name, last_name "
                 + "FROM students ORDER BY last_name"
             );
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                studentCombo.addItem(new StudentItem(
                    rs.getInt("student_id"),
                    rs.getString("school_id"),
                    rs.getString("first_name") + " "
                    + rs.getString("last_name")
                ));
            }

        } catch (Exception ex) {
            showError(ex);
        }
    }

    // REFRESH ALL TABLES
    private void refreshAll() {
        if (scholarshipModel == null) return;

        loadScholarships();
        loadStudents();
        loadPayroll();
        loadComboBoxes();
    }

    // ERROR MESSAGE
    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(
            this,
            "Something went wrong:\n" + ex.getMessage(),
            "System Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    // SCHOLARSHIP COMBO BOX ITEM
    private static class ScholarshipItem {
        int id;
        String name;

        ScholarshipItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // STUDENT COMBO BOX ITEM
    private static class StudentItem {
        int id;
        String schoolId;
        String name;

        StudentItem(int id, String schoolId, String name) {
            this.id = id;
            this.schoolId = schoolId;
            this.name = name;
        }

        @Override
        public String toString() {
            return schoolId + " - " + name;
        }
    }
}