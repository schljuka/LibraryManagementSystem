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
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;



/**
 *
 * @author nljuk
 */
public class ManageStudents extends javax.swing.JFrame {

    /**
     * Creates new form ManageBooks
     */
    
    
    String studentName, department, programme;
    int studentId;
    
    DefaultTableModel model;
    
    String userName;
    
 
    
    public ManageStudents(){
        initComponents();
        setStudentDetailsToTable();
    }
    
    
     // metoda za ispis korisnika koji se logovao
    public void setuserName(String name){
        userName=name;
        jLabel5.setText(userName);
    }

    
        
    
    
    // metoda za ispisivanje o studentima u tabeli
    public void setStudentDetailsToTable() {
                 
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Sky2023");
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT *FROM student_details");
            
            while (rs.next()) {
                String studentId=rs.getString("student_id");
                String studentName=rs.getString("name");
                String department=rs.getString("department");
                String programme=rs.getString("programme");
                
                Object[] obj={studentId, studentName, department, programme};
                model=(DefaultTableModel)tbl_studentDetails.getModel();
                model.addRow(obj);
            }
            
        } catch (SQLException ex) {
           // Logger.getLogger(ManageBooks.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getLocalizedMessage());
        }
                 
            
    }
    
    
    
    
    // metoda za dodavanje studenta 
    public boolean addStudent() throws ClassNotFoundException {
        boolean isAdded=false;
        studentId=Integer.parseInt(txt_studentId.getText());
        studentName=txt_studentName.getText();
        department=combo_DepartmentName.getSelectedItem().toString();
        programme=combo_programme.getSelectedItem().toString();
        
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Connection con = DBConnection.getConnection();
            String sql="INSERT into student_details values(?, ?, ?, ?)";
            PreparedStatement pst=con.prepareStatement(sql);
            
            pst.setInt(1, studentId);
            pst.setString(2, studentName);
            pst.setString(3, department);
            pst.setString(4, programme);
            
            
            
            int rowCount=pst.executeUpdate();
            if (rowCount>0) {
                isAdded=true;
            } else {
                isAdded=false;
            }
                                  
        } catch (SQLException ex) {
           // Logger.getLogger(ManageBooks.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getLocalizedMessage());
        }
        
        return isAdded;
    
}
    
    
    
    
    
    //metoda za UPDATE 
    public boolean updateStudent() throws ClassNotFoundException{
        boolean isUpdated=false;
        studentId=Integer.parseInt(txt_studentId.getText());
        studentName=txt_studentName.getText();
        department=combo_DepartmentName.getSelectedItem().toString();
        programme=combo_programme.getSelectedItem().toString();
        
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Connection con = DBConnection.getConnection();
            String sql="UPDATE student_details set name= ?, department=?, programme=? where student_id=?";
            PreparedStatement pst=con.prepareStatement(sql);
            
            pst.setString(1, studentName);
            pst.setString(2, department);
            pst.setString(3, programme);
            pst.setInt(4, studentId);
            
            int rowCount=pst.executeUpdate();
            if (rowCount>0) {
                isUpdated=true;
            } else {
                isUpdated=false;
            }
                                  
        } catch (SQLException ex) {
           // Logger.getLogger(ManageBooks.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getLocalizedMessage());
        }
        
        return isUpdated;
    }
    
    
    
    // metoda za DELETE 
    public boolean deleteStudent() throws ClassNotFoundException{
        boolean isDeleted=false;
        studentId=Integer.parseInt(txt_studentId.getText());
        
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Connection con = DBConnection.getConnection();
            String sql="DELETE from student_details where student_id=?";
            PreparedStatement pst=con.prepareStatement(sql);
            
            pst.setInt(1, studentId);
            
            int rowCount=pst.executeUpdate();
            if (rowCount>0) {
                isDeleted=true;
            } else {
                isDeleted=false;
            }
                                  
        } catch (SQLException ex) {
           // Logger.getLogger(ManageBooks.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getLocalizedMessage());
        }
        
        return isDeleted;
        
    }
    
    
    // methoda za tabelu prilikom izmejne(ADD, DELTE...), tj automatski se mijenjaju na tabeli vrijednosti
        public void clearTable(){
        DefaultTableModel model=(DefaultTableModel) tbl_studentDetails.getModel();
        model.setRowCount(0);
    }
        
        
        
         //metoda za provjeru praznih polja
    public boolean verify(String add_or_edit)
    {
        String id=txt_studentId.getText();
        String name=txt_studentName.getText();
        
        
        boolean val=false;
        
        if(!id.equals("") && !name.equals(""))
        {
            val= true;
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Enter Valid Student Data");
            val= false;
        }
        return val;
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_studentId = new app.bolivia.swing.JCTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_studentName = new app.bolivia.swing.JCTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        rSMaterialButtonCircle2 = new necesario.RSMaterialButtonCircle();
        rSMaterialButtonCircle3 = new necesario.RSMaterialButtonCircle();
        rSMaterialButtonCircle4 = new necesario.RSMaterialButtonCircle();
        combo_programme = new javax.swing.JComboBox<>();
        combo_DepartmentName = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_studentDetails = new rojeru_san.complementos.RSTableMetro();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 40));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 17)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon("C:\\Users\\nljuk\\OneDrive\\Documents\\NetBeansProjects\\Library_NEW\\icons\\Manage Student  ID.png")); // NOI18N
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 40, 50));

        txt_studentId.setBackground(new java.awt.Color(102, 102, 255));
        txt_studentId.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
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
        jPanel1.add(txt_studentId, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, 320, -1));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 17)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Enter Student Id");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 150, -1));

        txt_studentName.setBackground(new java.awt.Color(102, 102, 255));
        txt_studentName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_studentName.setFont(new java.awt.Font("Times New Roman", 0, 17)); // NOI18N
        txt_studentName.setPlaceholder("Enter Student Name ...");
        txt_studentName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_studentNameFocusLost(evt);
            }
        });
        txt_studentName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_studentNameActionPerformed(evt);
            }
        });
        jPanel1.add(txt_studentName, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 270, 320, -1));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 17)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Enter Student Name :");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, 150, -1));

        jLabel17.setFont(new java.awt.Font("Times New Roman", 0, 17)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 30, 50));

        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 17)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Select Department");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, 150, -1));

        jLabel19.setFont(new java.awt.Font("Times New Roman", 0, 17)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setIcon(new javax.swing.ImageIcon("C:\\Users\\nljuk\\OneDrive\\Documents\\NetBeansProjects\\Library_NEW\\icons\\Manage Students  List.png")); // NOI18N
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 380, 30, 50));

        jLabel20.setFont(new java.awt.Font("Times New Roman", 0, 17)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setIcon(new javax.swing.ImageIcon("C:\\Users\\nljuk\\OneDrive\\Documents\\NetBeansProjects\\Library_NEW\\icons\\Manage Student Branch.png")); // NOI18N
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 520, 30, 60));

        jLabel21.setFont(new java.awt.Font("Times New Roman", 0, 17)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Select Programme");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 500, 150, -1));

        rSMaterialButtonCircle2.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle2.setText("ADD");
        rSMaterialButtonCircle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle2ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 640, 140, 70));

        rSMaterialButtonCircle3.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle3.setText("DELETE");
        rSMaterialButtonCircle3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle3ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 640, 140, 70));

        rSMaterialButtonCircle4.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle4.setText("Update");
        rSMaterialButtonCircle4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle4ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 640, 140, 70));

        jPanel1.add(combo_programme, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 530, 320, 40));

        combo_DepartmentName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Department", "BIOLOGIJA", "FIZIKA", "GEOGRAFIJA", "HEMIJA", "MATEMATICKE I KOMPJUTERSKE NAUKE" }));
        combo_DepartmentName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_DepartmentNameActionPerformed(evt);
            }
        });
        jPanel1.add(combo_DepartmentName, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 390, 320, 40));

        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\nljuk\\OneDrive\\Documents\\NetBeansProjects\\Library_NEW\\icons\\Manage Student Name.png")); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 580, 830));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_studentDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student Id", "Name", "Department", "Programme"
            }
        ));
        tbl_studentDetails.setColorBackgoundHead(new java.awt.Color(102, 102, 255));
        tbl_studentDetails.setColorBordeFilas(new java.awt.Color(102, 102, 255));
        tbl_studentDetails.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        tbl_studentDetails.setColorSelBackgound(new java.awt.Color(255, 55, 51));
        tbl_studentDetails.setFont(new java.awt.Font("Times New Roman", 0, 25)); // NOI18N
        tbl_studentDetails.setFuenteFilas(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        tbl_studentDetails.setFuenteFilasSelect(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        tbl_studentDetails.setFuenteHead(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        tbl_studentDetails.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tbl_studentDetails.setRowHeight(40);
        tbl_studentDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_studentDetailsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_studentDetails);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 260, 990, 410));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\nljuk\\OneDrive\\Documents\\NetBeansProjects\\Library_NEW\\icons\\Manage Students Icon.png")); // NOI18N
        jLabel3.setText("Manage Students");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 110, 290, -1));

        jPanel5.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 630, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 180, 630, 5));

        jLabel2.setBackground(new java.awt.Color(102, 102, 255));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\nljuk\\OneDrive\\Documents\\NetBeansProjects\\Library_NEW\\icons\\Close red.png")); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1300, 0, 40, 50));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, 170, 40));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 1340, 830));

        setSize(new java.awt.Dimension(1724, 824));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // metoda kojom se klikom misa vracamo korak na Home stranicu
    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        try {
            // TODO add your handling code here:
            HomePage home=new HomePage();
            home.setuserName(jLabel5.getText());
            home.setVisible(true);
            dispose();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ManageStudents.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel1MouseClicked

    private void txt_studentIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_studentIdFocusLost

    }//GEN-LAST:event_txt_studentIdFocusLost

    private void txt_studentNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_studentNameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_studentNameFocusLost

    private void txt_studentIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_studentIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_studentIdActionPerformed

    private void txt_studentNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_studentNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_studentNameActionPerformed

    // metoda klikom na ADD dugme, dodajemo studenta s time da smo ubacili i metodu za ispis u tabeli 
    private void rSMaterialButtonCircle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle2ActionPerformed
        
        if(verify("add")==true)
            {
                try {
                    if (addStudent()==true)
                    {
                        JOptionPane.showMessageDialog(this, "Student Added");
                        clearTable();
                        setStudentDetailsToTable();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, "Student Addition Failed");
                    }   } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ManageStudents.class.getName()).log(Level.SEVERE, null, ex);
                }
        }            
    }//GEN-LAST:event_rSMaterialButtonCircle2ActionPerformed

    // metoda klikom za dugme DELETE 
    private void rSMaterialButtonCircle3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle3ActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            
            if (deleteStudent()==true)
            {
                JOptionPane.showMessageDialog(this, "Student Deleted");
                clearTable();
                setStudentDetailsToTable();
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Student Deletion Failed");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ManageStudents.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_rSMaterialButtonCircle3ActionPerformed

    // metoda klikom za UPDATE 
    private void rSMaterialButtonCircle4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle4ActionPerformed
       
       if(verify("edit"))                
            try {
                if (updateStudent()==true)
                {
                    JOptionPane.showMessageDialog(this, "Student Updated");
                    clearTable();
                    setStudentDetailsToTable();
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Student Updated Failed");
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ManageStudents.class.getName()).log(Level.SEVERE, null, ex);
            } 
             
        
    }//GEN-LAST:event_rSMaterialButtonCircle4ActionPerformed

    // metoda da klikom misa iz tabele na zeljenu vrijednost ispise tu vrijednost na lijevom panelu
    private void tbl_studentDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_studentDetailsMouseClicked
        // TODO add your handling code here:
        
        int rowNo=tbl_studentDetails.getSelectedRow();
        TableModel model=tbl_studentDetails.getModel();
        
        txt_studentId.setText(model.getValueAt(rowNo, 0).toString());
        txt_studentName.setText(model.getValueAt(rowNo, 1).toString());
        combo_DepartmentName.setSelectedItem(model.getValueAt(rowNo, 2).toString());
        combo_programme.setSelectedItem(model.getValueAt(rowNo, 3).toString());
                
        
    }//GEN-LAST:event_tbl_studentDetailsMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void combo_DepartmentNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_DepartmentNameActionPerformed
        // TODO add your handling code here:
        if (combo_DepartmentName.getSelectedItem().equals("BIOLOGIJA"))
        {
            combo_programme.removeAllItems();
            combo_programme.setSelectedItem(null);
            combo_programme.addItem("Biohemija i fiziologija");
            combo_programme.addItem("Ekologija");
            combo_programme.addItem("Genetika");
            combo_programme.addItem("Mikrobiologija");
            combo_programme.addItem("Nastavni");
            
        }
        else
            if (combo_DepartmentName.getSelectedItem().equals("FIZIKA"))
            {
            combo_programme.removeAllItems();
            combo_programme.setSelectedItem(null);
            combo_programme.addItem("Teorijksa fizika");
            combo_programme.addItem("Eksperimentalna fizika");
            combo_programme.addItem("Medicinska radijaciona fizika");
            }
        else
            if (combo_DepartmentName.getSelectedItem().equals("GEOGRAFIJA"))
            {
            combo_programme.removeAllItems();
            combo_programme.setSelectedItem(null);
            combo_programme.addItem("Nastavni");
            combo_programme.addItem("Regionalno i prostorno planiranje");
            combo_programme.addItem("Turizam i zastita zivotne sredine");
            }
        else
            if (combo_DepartmentName.getSelectedItem().equals("HEMIJA"))
            {
            combo_programme.removeAllItems();
            combo_programme.setSelectedItem(null);
            combo_programme.addItem("Opsti smjer");
            combo_programme.addItem("Nastavni smjer");
            combo_programme.addItem("Kontrola kvaliteta i zastita sredine");
            }
        else
            if (combo_DepartmentName.getSelectedItem().equals("MATEMATICKE I KOMPJUTERSKE NAUKE"))
            {
            combo_programme.removeAllItems();
            combo_programme.setSelectedItem(null);
            combo_programme.addItem("Opsti smjer");
            combo_programme.addItem("Primjenjena matematika");
            combo_programme.addItem("Nastavni smjer");
            combo_programme.addItem("Kompjuterske nauke");
            }
    }//GEN-LAST:event_combo_DepartmentNameActionPerformed

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
            java.util.logging.Logger.getLogger(ManageStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageStudents().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> combo_DepartmentName;
    private javax.swing.JComboBox<String> combo_programme;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private necesario.RSMaterialButtonCircle rSMaterialButtonCircle2;
    private necesario.RSMaterialButtonCircle rSMaterialButtonCircle3;
    private necesario.RSMaterialButtonCircle rSMaterialButtonCircle4;
    private rojeru_san.complementos.RSTableMetro tbl_studentDetails;
    private app.bolivia.swing.JCTextField txt_studentId;
    private app.bolivia.swing.JCTextField txt_studentName;
    // End of variables declaration//GEN-END:variables
}
