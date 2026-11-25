/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.StudentDAO;
import model.Student;
import java.util.List;
/**
 *
 * @author ASUS
 */
public class StudentController {
    
    private StudentDAO studentDAO = new StudentDAO();
    
    public List<Student> getAllStudents(){
        return studentDAO.getAllStudents();
    }
    
    public boolean addStudent(Student student){
        return studentDAO.addStudent(student);
    }
    
     public boolean updateStudent(Student student){
        return studentDAO.updateStudent(student);
    }
     
      public boolean deleteStudent(int studentId){
        return studentDAO.deleteStudent(studentId);
    }
}