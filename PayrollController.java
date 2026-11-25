/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.PayrollDAO;
import model.Payroll;
import java.util.List;
/**
 *
 * @author ASUS
 */
public class PayrollController {
    
     private PayrollDAO payrollDAO = new PayrollDAO();
    
    public List<Payroll> getAllPayroll(){
        return payrollDAO.getAllPayroll();
    }
    
    public boolean addPayroll(Payroll payroll){
        return payrollDAO.addPayroll(payroll);
    }
    
     public boolean updatePaymentStatus(int payrollId, String status){
        return payrollDAO.updatePaymentStatus(payrollId, status);
    }
     
      public boolean deletePayroll(int payrollId){
        return payrollDAO.deletePayroll(payrollId);
    }
}
