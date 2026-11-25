/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

public class Scholarship {

    private int scholarshipId;
    private String scholarshipName;
    private double monthlyAllowance;

    public Scholarship() {
    }

    public Scholarship(int scholarshipId, String scholarshipName,
            double monthlyAllowance) {

        this.scholarshipId = scholarshipId;
        this.scholarshipName = scholarshipName;
        this.monthlyAllowance = monthlyAllowance;
    }

    public int getScholarshipId() {
        return scholarshipId;
    }

    public void setScholarshipId(int scholarshipId) {
        this.scholarshipId = scholarshipId;
    }

    public String getScholarshipName() {
        return scholarshipName;
    }

    public void setScholarshipName(String scholarshipName) {
        this.scholarshipName = scholarshipName;
    }

    public double getMonthlyAllowance() {
        return monthlyAllowance;
    }

    public void setMonthlyAllowance(double monthlyAllowance) {
        this.monthlyAllowance = monthlyAllowance;
    }
}
