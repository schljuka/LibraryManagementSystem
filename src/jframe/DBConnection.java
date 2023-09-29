/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jframe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author nljuk
 */
public class DBConnection {
    static Connection con=null;
    
    public static Connection getConnection() throws ClassNotFoundException {
        try{
           //Class.forName("com.mysql.jdbc.Driver");
           con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Sky2023");
        
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            
        }    
        return con;
    }
    
}
