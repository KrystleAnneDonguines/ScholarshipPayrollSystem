/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

import java.sql.Date;

public class Payroll {

    private int payrollId;
    private int studentId;
    private Date periodStart;
    private Date periodEnd;
    private double amountDue;
    private String paymentStatus;
    private Date paymentDate;

    public Payroll() {
    }

    public Payroll(int payrollId, int studentId,
            Date periodStart, Date periodEnd,
            double amountDue, String paymentStatus,
            Date paymentDate) {

        this.payrollId = payrollId;
        this.studentId = studentId;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.amountDue = amountDue;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }

    public int getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Date getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(Date periodStart) {
        this.periodStart = periodStart;
    }

    public Date getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(Date periodEnd) {
        this.periodEnd = periodEnd;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
