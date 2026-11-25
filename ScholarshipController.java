/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ScholarshipDAO;
import model.Scholarship;
import java.util.List;
/**
 *
 * @author ASUS
 */
public class ScholarshipController {
    
    private ScholarshipDAO scholarshipDAO = new ScholarshipDAO();
    
    public List<Scholarship> getAllScholarships(){
        return scholarshipDAO.getAllScholarships();
    }
    
    public boolean addScholarship(Scholarship scholarship){
        return scholarshipDAO.addScholarship(scholarship);
    }
    
     public boolean updateScholarship(Scholarship scholarship){
        return scholarshipDAO.updateScholarship(scholarship);
    }
     
      public boolean deleteScholarship(int scholarshipId){
        return scholarshipDAO.deleteScholarship(scholarshipId);
    }
}
