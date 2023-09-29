/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jframe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/**
 *
 * @author nljuk
 */
public class ViewAllRecord extends javax.swing.JFrame {

    /**
     * Creates new form ViewAllRecord
     */
    String userName;
    
    DefaultTableModel model;
    public ViewAllRecord() {
        initComponents();
        setIssueBookDetailsToTable();
    }
    
    // metoda za ispis korisnika koji se logovao
    public void setuserName(String name){
        userName=name;
        jLabel5.setText(userName);
    }
    
     // metoda za ispisivanje o knjigama u tabeli
    public void setIssueBookDetailsToTable() {
                 
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Sky2023");
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT *FROM issue_book_details");
            
            while (rs.next()) {
                String id=rs.getString("id");
                String bookName=rs.getString("book_name");
                String studentName=rs.getString("student_name");
                String issueDate=rs.getString("issue_date");
                String dueDate=rs.getString("due_date");
                String status=rs.getString("status");
                String returnDate=rs.getString("return_date");
                
                Object[] obj={id, bookName, studentName, issueDate, dueDate, status, returnDate};
                model=(DefaultTableModel)tbl_issueBookDetails.getModel();
                model.addRow(obj);
            }
            
        } catch (SQLException ex) {
           // Logger.getLogger(ManageBooks.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getLocalizedMessage());
        }
                 
    }       
        
         // methoda za tabelu prilikom izmejne(ADD, DELTE...), tj automatski se mijenjaju na tabeli vrijednosti
        public void clearTable(){
        DefaultTableModel model=(DefaultTableModel) tbl_issueBookDetails.getModel();
        model.setRowCount(0);
    }
        
        
        // metoda za 
        public void search() throws ClassNotFoundException{
            
            Date uFromDate=date_fromDate.getDate();
            Date uToDate=date_toDate.getDate();
            
            long l1=uFromDate.getTime();
            long l2=uToDate.getTime();
            
            java.sql.Date fromDate= new java.sql.Date(l1);
            java.sql.Date toDate= new java.sql.Date(l2);
            
           
           
           try{
               Connection con=DBConnection.getConnection();
               String sql="SELECT *from issue_book_details where issue_date BETWEEN ? and ?";
               PreparedStatement pst=con.prepareStatement(sql);
               pst.setDate(1, fromDate);
               pst.setDate(2, toDate);
               
               ResultSet rs=pst.executeQuery();
               
               if(rs.next()==false){
                   JOptionPane.showMessageDialog(this, "No Record Found");
               } else{
                                  
               while(rs.next()) {
                    String id=rs.getString("id");
                    String bookName=rs.getString("book_name");
                    String StudentName=rs.getString("student_name");
                    String issueDate=rs.getString("issue_date");
                    String dueDate=rs.getString("due_date");
                    String status=rs.getString("status");

                    Object[] obj={id, bookName, StudentName, issueDate, dueDate, status};
                    model=(DefaultTableModel)tbl_issueBookDetails.getModel();
                    model.addRow(obj);
                   
               }
             }
               
               
           } catch (SQLException ex) {
           // Logger.getLogger(ManageBooks.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getLocalizedMessage());
        }
                 
            
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        date_fromDate = new com.toedter.calendar.JDateChooser();
        jLabel36 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        date_toDate = new com.toedter.calendar.JDateChooser();
        rSMaterialButtonCircle1 = new rojerusan.RSMaterialButtonCircle();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        rSMaterialButtonCircle2 = new rojerusan.RSMaterialButtonCircle();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        panel_table = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_issueBookDetails = new rojeru_san.complementos.RSTableMetro();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 25)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\nljuk\\OneDrive\\Documents\\NetBeansProjects\\Library_NEW\\icons\\View All Record Icon.png")); // NOI18N
        jLabel2.setText("View All Record");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 40, 290, 60));

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

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 520, 10));

        date_fromDate.setBackground(new java.awt.Color(255, 51, 51));
        date_fromDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        date_fromDate.setForeground(new java.awt.Color(255, 51, 51));
        date_fromDate.setDateFormatString("dd/MM/yyyy");
        jPanel1.add(date_fromDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 320, 30));

        jLabel36.setFont(new java.awt.Font("Times New Roman", 0, 17)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Issue Date :");
        jPanel1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 150, -1));

        jLabel34.setFont(new java.awt.Font("Times New Roman", 0, 17)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Issue Date :");
        jPanel1.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 190, 150, -1));

        date_toDate.setBackground(new java.awt.Color(255, 51, 51));
        date_toDate.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 51, 51)));
        date_toDate.setForeground(new java.awt.Color(255, 51, 51));
        date_toDate.setDateFormatString("dd/MM/yyyy");
        jPanel1.add(date_toDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 180, 320, 30));

        rSMaterialButtonCircle1.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle1.setText("SEARCH");
        rSMaterialButtonCircle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle1ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 170, 160, 60));

        jPanel2.setBackground(new java.awt.Color(251, 51, 51));

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
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 40));

        rSMaterialButtonCircle2.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle2.setText("ALL");
        rSMaterialButtonCircle2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonCircle2MouseClicked(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 170, 160, 60));

        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1440, 0, -1, -1));

        jLabel3.setBackground(new java.awt.Color(102, 102, 255));
        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\nljuk\\OneDrive\\Documents\\NetBeansProjects\\Library_NEW\\icons\\Close white.png")); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1440, 0, 40, 50));

        jLabel5.setForeground(new java.awt.Color(102, 102, 255));
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 150, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1480, 280));

        panel_table.setBackground(new java.awt.Color(255, 255, 255));
        panel_table.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));
        panel_table.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_issueBookDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Book Name", "Student Name", "Issue Date", "Due Date", "Status", "Return Date"
            }
        ));
        tbl_issueBookDetails.setColorBackgoundHead(new java.awt.Color(102, 102, 255));
        tbl_issueBookDetails.setColorBordeFilas(new java.awt.Color(102, 102, 255));
        tbl_issueBookDetails.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        tbl_issueBookDetails.setColorSelBackgound(new java.awt.Color(255, 55, 51));
        tbl_issueBookDetails.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        tbl_issueBookDetails.setFuenteFilas(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        tbl_issueBookDetails.setFuenteFilasSelect(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tbl_issueBookDetails.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tbl_issueBookDetails.setRowHeight(40);
        tbl_issueBookDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_issueBookDetailsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_issueBookDetails);

        panel_table.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 1350, 410));

        getContentPane().add(panel_table, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 1480, 580));

        setSize(new java.awt.Dimension(1481, 857));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_issueBookDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_issueBookDetailsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_issueBookDetailsMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        try {
            // TODO add your handling code here:
            HomePage home=new HomePage();
            home.setuserName(jLabel5.getText());
            home.setVisible(true);
            dispose();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewAllRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel1MouseClicked
    
    // pozivanje metode SEARCH
    private void rSMaterialButtonCircle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle1ActionPerformed
        try {
            // TODO add your handling code here:
            
            if(date_fromDate.getDate() !=null && date_toDate.getDate() !=null ) {
                    
                    clearTable();
                    search();
            }else {
                JOptionPane.showMessageDialog(this, "Please select a date");
            }
    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewAllRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_rSMaterialButtonCircle1ActionPerformed
   
    // metoda kkilom na dugme ALL  - za ispisivanje istorije za iznajmljene knjige
    private void rSMaterialButtonCircle2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle2MouseClicked
        // TODO add your handling code here:
        clearTable();
        setIssueBookDetailsToTable();
    }//GEN-LAST:event_rSMaterialButtonCircle2MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel3MouseClicked

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
            java.util.logging.Logger.getLogger(ViewAllRecord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewAllRecord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewAllRecord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewAllRecord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewAllRecord().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser date_fromDate;
    private com.toedter.calendar.JDateChooser date_toDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panel_table;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle1;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle2;
    private rojeru_san.complementos.RSTableMetro tbl_issueBookDetails;
    // End of variables declaration//GEN-END:variables
}
