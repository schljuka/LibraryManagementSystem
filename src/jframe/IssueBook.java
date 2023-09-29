/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jframe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import javax.swing.JOptionPane;


/**
 *
 * @author nljuk
 */
public class IssueBook extends javax.swing.JFrame {

    /**
     * Creates new form IssueBook
     */
    String userName;
    
    public IssueBook() {
        initComponents();
    }
    
     // metoda za ispis korisnika koji se logovao
    public void setuserName(String name){
        userName=name;
        jLabel4.setText(userName);
    }
    
    // metoda ispisivanja detalja knjige  
    public void getBookDetails() throws ClassNotFoundException {
        int bookId=Integer.parseInt(txt_bookId.getText());
        
         try{
            Connection con=DBConnection.getConnection();
            PreparedStatement pst=con.prepareStatement("SELECT *from book_details where book_id=?");
            pst.setInt(1, bookId);
            ResultSet rs=pst.executeQuery();
            
            if(rs.next()) {
                
                lbl_bookId.setText(rs.getString("book_id"));
                lbl_bookName.setText(rs.getString("book_name"));
                lbl_author.setText(rs.getString("author"));
                lbl_quantity.setText(rs.getString("quantity"));
            } else {
                
                //lbl_bookError.setText("Invalid book id");
                JOptionPane.showMessageDialog(this, "Invalid book id" );
                txt_bookId.setText("");
                lbl_bookId.setText("");
                lbl_bookName.setText("");
                lbl_author.setText("");
                lbl_quantity.setText("");
            }
                     
            
        
        } catch (SQLException ex) {
           // Logger.getLogger(ManageBooks.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getLocalizedMessage());
        }
                 
        
    }
    
    
    
    // metoda ispisivanja detalja studenta  
    public void getStudentDetails() throws ClassNotFoundException {
        int studentId=Integer.parseInt(txt_studentId.getText());
        
         try{
            Connection con=DBConnection.getConnection();
            PreparedStatement pst=con.prepareStatement("SELECT *from student_details where student_id=?");
            pst.setInt(1, studentId);
            ResultSet rs=pst.executeQuery();
            
            if(rs.next()) {
                
                lbl_studentId.setText(rs.getString("student_id"));
                lbl_studentName.setText(rs.getString("name"));
                lbl_department.setText(rs.getString("department"));
                lbl_programme.setText(rs.getString("programme"));
            }else {
                //lbl_studentError.setText("Invalid student id");
                JOptionPane.showMessageDialog(this, "Invalid student id" );
                lbl_studentId.setText("");
                lbl_studentName.setText("");
                lbl_department.setText("");
                lbl_programme.setText("");
            }
                     
            
        
        } catch (SQLException ex) {
           // Logger.getLogger(ManageBooks.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getLocalizedMessage());
        }
                 
        
    }
    
    
    // metod za ubacivanje iznajmljene knjige u tabelu issuedBook
    public boolean issueBook() throws ClassNotFoundException{
        boolean isIssued=false;
        
        int bookId=Integer.parseInt(txt_bookId.getText());
        int studentId=Integer.parseInt(txt_studentId.getText());
        String bookName=lbl_bookName.getText();
        String studentName=lbl_studentName.getText();
        
        Date uIssueDate=date_issueDate.getDate();
        Date uDueDate=date_dueDate.getDate();
        
        Long l1=uIssueDate.getTime();
        Long l2=uDueDate.getTime();
        
        java.sql.Date sIssueDate=new java.sql.Date(l1);
        java.sql.Date sDueDate=new java.sql.Date(l2);
        
        try {
                // Class.forName("com.mysql.jdbc.Driver");
                 Connection con=DBConnection.getConnection();
                 String sql="INSERT into issue_book_details (book_id, book_name, student_id, student_name, issue_date, due_date, status) values(?,?,?,?,?,?,?)";
                 PreparedStatement pst=con.prepareStatement(sql);
                 pst.setInt(1, bookId);
                 pst.setString(2, bookName);
                 pst.setInt(3, studentId);
                 pst.setString(4, studentName);
                 pst.setDate(5, sIssueDate);
                 pst.setDate(6, sDueDate);
                 pst.setString(7, "pending");
                 
                 int rowCount=pst.executeUpdate();
                 if (rowCount>0) {
                     isIssued=true;
                }else {
                     isIssued=false;
                }                 
             
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return isIssued;
    
        
    }
    
    
    // metoda kojom mijenjamo stanje kolicine knjiga
    public void updateBookCount() throws ClassNotFoundException{
        int bookId=Integer.parseInt(txt_bookId.getText());
        
        try{
            Connection con=DBConnection.getConnection();
            String sql="UPDATE book_details set quantity=quantity -1 where book_id=?";
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1, bookId);
            
            int rowCount=pst.executeUpdate();
            if (rowCount>0){
                JOptionPane.showMessageDialog(this, "Book count updated");
                int initialCount=Integer.parseInt(lbl_quantity.getText());
                lbl_quantity.setText(Integer.toString(initialCount -1));
            } else {
                JOptionPane.showMessageDialog(this, "Cant´t updated count book");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
                
    }
    
    
    // metoda za provjeru da li je knjiga vec izdata istom studentu
    public boolean isAlreadyIssued() throws ClassNotFoundException{
        
        boolean isAlreadyIssued=false;
        int bookId=Integer.parseInt(txt_bookId.getText());
        int studentId=Integer.parseInt(txt_studentId.getText());
        
        try{
            Connection con=DBConnection.getConnection();
            String sql="SELECT *FROM issue_book_details  WHERE book_id=? AND student_id =? AND status =?";
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1, bookId);
            pst.setInt(2, studentId);
            pst.setString(3, "pending");
            
            ResultSet rs=pst.executeQuery();
            
            if (rs.next()){
                isAlreadyIssued=true;
            } else{
                isAlreadyIssued=false;
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return isAlreadyIssued;
        
    }
    
    // metoda koja privjera da li su sva polja popunjena prije izvrsenja ISSUE
    public boolean validateFields(){
        
        
        String bookId=txt_bookId.getText();
        String studentId=txt_studentId.getText();
        
        Date uIssueDate=date_issueDate.getDate();
        Date uDueDate=date_dueDate.getDate();
        
        if (bookId.equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter bookId");
            return false;
        } 
        if (studentId.equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter valid studentId");
            return false;
        }
        if (uIssueDate==null) {
            JOptionPane.showMessageDialog(this, "Please enter valid date");
            return false;
        }        
        if (uDueDate==null) {
            JOptionPane.showMessageDialog(this, "Please enter valid date");
            return false;
        }
        return true;
            
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        panel_main = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lbl_quantity = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbl_bookName = new javax.swing.JLabel();
        lbl_author = new javax.swing.JLabel();
        lbl_bookId = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbl_bookError = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        lbl_programme = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lbl_studentName = new javax.swing.JLabel();
        lbl_department = new javax.swing.JLabel();
        lbl_studentId = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lbl_studentError = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txt_bookId = new app.bolivia.swing.JCTextField();
        jLabel34 = new javax.swing.JLabel();
        txt_studentId = new app.bolivia.swing.JCTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        rSMaterialButtonCircle2 = new necesario.RSMaterialButtonCircle();
        date_issueDate = new com.toedter.calendar.JDateChooser();
        date_dueDate = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jPanel4.setBackground(new java.awt.Color(255, 51, 51));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(102, 102, 255));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Back");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 60));

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 25)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Book Details");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, 150, 40));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 520, 10));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Quantity : ");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 500, -1, -1));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 500, 290, 30));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Book Name : ");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, -1, -1));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Author : ");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, -1, -1));

        jLabel17.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Book Id : ");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 290, -1, -1));

        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 280, 290, 30));

        jLabel19.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 350, 290, 30));

        jLabel20.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 420, 290, 30));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 204, 0));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_main.setBackground(new java.awt.Color(255, 255, 255));
        panel_main.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));
        panel_main.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 51, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\nljuk\\OneDrive\\Documents\\NetBeansProjects\\Library_NEW\\icons\\Back white.png")); // NOI18N
        jLabel1.setText("Back");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 40));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 25)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\nljuk\\OneDrive\\Documents\\NetBeansProjects\\Library_NEW\\icons\\Issue Book book.png")); // NOI18N
        jLabel2.setText("Book Details");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 220, 60));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 520, 10));

        lbl_quantity.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lbl_quantity.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lbl_quantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 500, 290, 30));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Book name : ");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, -1, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Author : ");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, -1, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Book Id : ");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 290, -1, -1));

        lbl_bookName.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lbl_bookName.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lbl_bookName, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 350, 290, 30));

        lbl_author.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lbl_author.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lbl_author, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 420, 290, 30));

        lbl_bookId.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lbl_bookId.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lbl_bookId, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 280, 290, 30));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Quantity : ");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 500, -1, -1));

        lbl_bookError.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        lbl_bookError.setForeground(new java.awt.Color(255, 255, 0));
        jPanel1.add(lbl_bookError, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 570, 450, 40));

        panel_main.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 1080));

        jPanel7.setBackground(new java.awt.Color(102, 102, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel7.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 520, 10));

        jLabel23.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Programme : ");
        jPanel7.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 500, -1, -1));

        lbl_programme.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lbl_programme.setForeground(new java.awt.Color(255, 255, 255));
        jPanel7.add(lbl_programme, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 500, 290, 30));

        jLabel25.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Student name : ");
        jPanel7.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, -1, -1));

        jLabel26.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Department : ");
        jPanel7.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, -1, -1));

        jLabel27.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Student Id : ");
        jPanel7.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 290, -1, -1));

        jLabel28.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jPanel7.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 500, 290, 30));

        lbl_studentName.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lbl_studentName.setForeground(new java.awt.Color(255, 255, 255));
        jPanel7.add(lbl_studentName, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 350, 290, 30));

        lbl_department.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lbl_department.setForeground(new java.awt.Color(255, 255, 255));
        jPanel7.add(lbl_department, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 420, 290, 30));

        lbl_studentId.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lbl_studentId.setForeground(new java.awt.Color(255, 255, 255));
        jPanel7.add(lbl_studentId, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 290, 290, 30));

        jLabel31.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jPanel7.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 280, 290, 30));

        jLabel33.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jPanel7.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 430, 290, 30));

        jLabel24.setFont(new java.awt.Font("Times New Roman", 0, 25)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setIcon(new javax.swing.ImageIcon("C:\\Users\\nljuk\\OneDrive\\Documents\\NetBeansProjects\\Library_NEW\\icons\\Issue Book Student.png")); // NOI18N
        jLabel24.setText("Student Details");
        jPanel7.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, 240, 70));

        lbl_studentError.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        lbl_studentError.setForeground(new java.awt.Color(255, 255, 0));
        jPanel7.add(lbl_studentError, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 570, 470, 40));

        panel_main.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 0, 640, 1080));

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("Times New Roman", 0, 25)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 51, 51));
        jLabel22.setIcon(new javax.swing.ImageIcon("C:\\Users\\nljuk\\OneDrive\\Documents\\NetBeansProjects\\Library_NEW\\icons\\Manage Books Red.png")); // NOI18N
        jLabel22.setText("Issue Book");
        panel_main.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(1480, 126, 220, -1));

        jPanel10.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        panel_main.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 200, -1, -1));

        jLabel21.setFont(new java.awt.Font("Times New Roman", 0, 17)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Enter Student Id");
        panel_main.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 150, -1));

        jLabel29.setFont(new java.awt.Font("Times New Roman", 0, 17)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        panel_main.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, 30, 60));

        jLabel30.setFont(new java.awt.Font("Times New Roman", 0, 17)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 51, 51));
        jLabel30.setText("Book Id :");
        panel_main.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(1310, 320, 150, -1));

        txt_bookId.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 51, 51)));
        txt_bookId.setFont(new java.awt.Font("Times New Roman", 0, 17)); // NOI18N
        txt_bookId.setPlaceholder("Enter Book Id ...");
        txt_bookId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_bookIdFocusLost(evt);
            }
        });
        txt_bookId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bookIdActionPerformed(evt);
            }
        });
        txt_bookId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bookIdKeyReleased(evt);
            }
        });
        panel_main.add(txt_bookId, new org.netbeans.lib.awtextra.AbsoluteConstraints(1500, 310, 320, -1));

        jLabel34.setFont(new java.awt.Font("Times New Roman", 0, 17)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 51, 51));
        jLabel34.setText("Due Date :");
        panel_main.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 590, 150, -1));

        txt_studentId.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 51, 51)));
        txt_studentId.setFont(new java.awt.Font("Times New Roman", 0, 17)); // NOI18N
        txt_studentId.setPlaceholder("Enter Student Id ...");
        txt_studentId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_studentIdFocusLost(evt);
            }
        });
        txt_studentId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_studentIdActionPerformed(evt);
            }
        });
        txt_studentId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_studentIdKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_studentIdKeyTyped(evt);
            }
        });
        panel_main.add(txt_studentId, new org.netbeans.lib.awtextra.AbsoluteConstraints(1500, 380, 320, -1));

        jLabel35.setFont(new java.awt.Font("Times New Roman", 0, 17)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 51, 51));
        jLabel35.setText("Student Id :");
        panel_main.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(1310, 390, 150, -1));

        jLabel36.setFont(new java.awt.Font("Times New Roman", 0, 17)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 51, 51));
        jLabel36.setText("Issue Date :");
        panel_main.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 510, 150, -1));

        rSMaterialButtonCircle2.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle2.setText("ISSUE BOOK");
        rSMaterialButtonCircle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle2ActionPerformed(evt);
            }
        });
        panel_main.add(rSMaterialButtonCircle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1330, 730, 490, 70));

        date_issueDate.setBackground(new java.awt.Color(255, 51, 51));
        date_issueDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        date_issueDate.setForeground(new java.awt.Color(255, 51, 51));
        date_issueDate.setDateFormatString("dd/MM/yyyy");
        panel_main.add(date_issueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 500, 320, 30));

        date_dueDate.setBackground(new java.awt.Color(255, 51, 51));
        date_dueDate.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 51, 51)));
        date_dueDate.setForeground(new java.awt.Color(255, 51, 51));
        date_dueDate.setDateFormatString("dd/MM/yyyy");
        panel_main.add(date_dueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 582, 320, 30));

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\nljuk\\OneDrive\\Documents\\NetBeansProjects\\Library_NEW\\icons\\Close red.png")); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        panel_main.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1800, 0, -1, -1));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        panel_main.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 60, 130, 40));

        getContentPane().add(panel_main, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 1020));

        setSize(new java.awt.Dimension(1840, 1016));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        try {
            // TODO add your handling code here:
            HomePage home=new HomePage();
            home.setuserName(jLabel4.getText());
            home.setVisible(true);
            dispose();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IssueBook.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel11MouseClicked

    private void txt_bookIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bookIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bookIdActionPerformed
    // pozivanje metoda za izpis iz tabele knjige
    private void txt_bookIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_bookIdFocusLost
        if(!txt_bookId.getText().equals("")){
            try {   
                getBookDetails();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(IssueBook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }//GEN-LAST:event_txt_bookIdFocusLost

    // pozivanje metode za ispis studenta iz tabele student
    private void txt_studentIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_studentIdFocusLost
        // TODO add your handling code here:
        if(!txt_studentId.getText().equals("")){
            try {   
                getStudentDetails();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(IssueBook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txt_studentIdFocusLost

    private void txt_studentIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_studentIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_studentIdActionPerformed

    // metoda kojom klikom na dugme izvrsavamo ISSUED book i koja automatski provjerava da li je knjiga izdata istom studentu i count izvrsenje
    private void rSMaterialButtonCircle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle2ActionPerformed
        
        if(validateFields()==true){
        try {
            
            if (lbl_quantity.getText().equals("0")) {
                JOptionPane.showMessageDialog(this, "Book is not available");
            }else{
            
                if(isAlreadyIssued()==false){
                   if(issueBook()==true) {
                    JOptionPane.showMessageDialog(this, "Book issued successfully");
                    updateBookCount();
                }else{ 
                    JOptionPane.showMessageDialog(this, "Can´t issued the book");
                } 
                }else{
                    JOptionPane.showMessageDialog(this, "This student already has this book"); 
                }
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IssueBook.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }//GEN-LAST:event_rSMaterialButtonCircle2ActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void txt_bookIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bookIdKeyReleased
        try {
            // TODO add your handling code here:
            getBookDetails();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IssueBook.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txt_bookIdKeyReleased

    private void txt_studentIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_studentIdKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_studentIdKeyTyped

    private void txt_studentIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_studentIdKeyReleased
        try {
            // TODO add your handling code here:
            getStudentDetails();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IssueBook.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txt_studentIdKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IssueBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IssueBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IssueBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IssueBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IssueBook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser date_dueDate;
    private com.toedter.calendar.JDateChooser date_issueDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lbl_author;
    private javax.swing.JLabel lbl_bookError;
    private javax.swing.JLabel lbl_bookId;
    private javax.swing.JLabel lbl_bookName;
    private javax.swing.JLabel lbl_department;
    private javax.swing.JLabel lbl_programme;
    private javax.swing.JLabel lbl_quantity;
    private javax.swing.JLabel lbl_studentError;
    private javax.swing.JLabel lbl_studentId;
    private javax.swing.JLabel lbl_studentName;
    private javax.swing.JPanel panel_main;
    private necesario.RSMaterialButtonCircle rSMaterialButtonCircle2;
    private app.bolivia.swing.JCTextField txt_bookId;
    private app.bolivia.swing.JCTextField txt_studentId;
    // End of variables declaration//GEN-END:variables
}
