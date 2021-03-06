/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaz;

import BD.Conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author luciano
 */
public class Interfaz extends javax.swing.JFrame {
            Proceso p;
            Connection c;
            Statement stmt;
            PreparedStatement pr;
            DefaultTableModel dtm;
            boolean estaVacia;
    /**
     * Creates new form Interfaz
     */
    public Interfaz() throws ClassNotFoundException, SQLException {
        initComponents();
        Class.forName("org.sqlite.JDBC");
        cargarGrilla(" ");
        estaVacia = grid_listado.getRowCount() > 0;
        txt_buscar.requestFocus();
//        this.setDefaultLookAndFeelDecorated(true);
        this.setLocationRelativeTo(null);
//                try {
//                    UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
//                } catch (InstantiationException ex) {
//                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IllegalAccessException ex) {
//                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (UnsupportedLookAndFeelException ex) {
//                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
//                }
    }
    
    private void conectar() throws SQLException{//creo conexion.
       
           c = DriverManager.getConnection("jdbc:sqlite:DBFinal.db");//Conexion con BD abierta.
           //JOptionPane.showMessageDialog(null, "BD Abierta Exitosamente");
       }
    
    private ResultSet consultaGrilla(String condicion)
       {
            ResultSet rs = null;

                try {
                    String sql = "";
                    
                    sql += "SELECT Palabra.palabra, SUM(PxD.cantidad), COUNT(PxD.id_Palabra) ";
                    sql += "FROM PalabraXDocumento PxD INNER JOIN Palabra on palabra.id= PxD.id_Palabra ";
                    //sql = "select * from consultagrilla";//Es el nombre de la vista
                    if (condicion != null && !" ".equals(condicion) && !"".equals(condicion)) {
                        sql += "where Palabra.palabra LIKE '" + condicion + "%' ";
                    }
                    sql += "GROUP BY Palabra.palabra";
                    
                    pr = c.prepareStatement(sql);
                    
                    
                    rs = pr.executeQuery();
                    
                    
                } catch (SQLException ex) {
                    System.out.println(ex);                }
                
            return rs;

       }
    public void cargarGrilla(String condicion)
    {   
        try
        {
                conectar();
                
                ResultSet rs = consultaGrilla(condicion);//Obtengo RS a mostrar.
                ResultSetMetaData rsm= rs.getMetaData();//Obtengo info de las prop del RS
                ArrayList<Object[]> resultado= new ArrayList<>();//Creo representacion de todas las filas de la tabla
                while(rs.next())
                {
                    Object[] rows= new Object[(rsm.getColumnCount())];//Creo vector de objetos para cada columna del RS, filas del RS
                    for (int i = 0; i < rows.length; i++)
                    {
                        rows[i]= rs.getObject(i+1);//Guardo en el vector el objeto en columna del RS correspondiente.
                    }
                    resultado.add(rows);//Añado toda una fila
                    
                }
                dtm = (DefaultTableModel)this.grid_listado.getModel();
                for (int i = 0; i < resultado.size(); i++)
                {
                    dtm.addRow(resultado.get(i));//Añado las filas a mi JTable
                }
        }
        catch(Exception e)
        {
            JOptionPane.showConfirmDialog(this, "No se encontro nada");
        }
    }
    
    
    public void limpiarGrilla()
    {
        //if (!estaVacia) {
                dtm= (DefaultTableModel)this.grid_listado.getModel();
                for (int i = 0; i < grid_listado.getRowCount(); i++) 
                {
                    dtm.removeRow(i);
                    i-=1;
               // }
           //     System.out.println("T vacia");
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

        Background = new javax.swing.JPanel();
        Busqueda = new javax.swing.JPanel();
        txt_buscar = new javax.swing.JTextField();
        btn_buscar = new javax.swing.JButton();
        Listado = new javax.swing.JPanel();
        scrPane_panel = new javax.swing.JScrollPane();
        grid_listado = new javax.swing.JTable();
        Procesar = new javax.swing.JPanel();
        btn_procesar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Busqueda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscarKeyReleased(evt);
            }
        });

        btn_buscar.setText("Buscar");

        javax.swing.GroupLayout BusquedaLayout = new javax.swing.GroupLayout(Busqueda);
        Busqueda.setLayout(BusquedaLayout);
        BusquedaLayout.setHorizontalGroup(
            BusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BusquedaLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(txt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(btn_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        BusquedaLayout.setVerticalGroup(
            BusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BusquedaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(BusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_buscar))
                .addGap(11, 11, 11))
        );

        Listado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        grid_listado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        grid_listado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Palabra", "Frecuencia", "Archivos"
            }
        ));
        grid_listado.setColumnSelectionAllowed(true);
        grid_listado.setDoubleBuffered(true);
        scrPane_panel.setViewportView(grid_listado);
        grid_listado.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout ListadoLayout = new javax.swing.GroupLayout(Listado);
        Listado.setLayout(ListadoLayout);
        ListadoLayout.setHorizontalGroup(
            ListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ListadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrPane_panel)
                .addContainerGap())
        );
        ListadoLayout.setVerticalGroup(
            ListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ListadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrPane_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addContainerGap())
        );

        Procesar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btn_procesar.setText("Pocesar");
        btn_procesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_procesarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ProcesarLayout = new javax.swing.GroupLayout(Procesar);
        Procesar.setLayout(ProcesarLayout);
        ProcesarLayout.setHorizontalGroup(
            ProcesarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ProcesarLayout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(btn_procesar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        ProcesarLayout.setVerticalGroup(
            ProcesarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ProcesarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_procesar)
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout BackgroundLayout = new javax.swing.GroupLayout(Background);
        Background.setLayout(BackgroundLayout);
        BackgroundLayout.setHorizontalGroup(
            BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Listado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(BackgroundLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(Procesar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        BackgroundLayout.setVerticalGroup(
            BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackgroundLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Busqueda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Procesar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addComponent(Listado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_procesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_procesarActionPerformed
        // TODO add your handling code here:
        p = new Proceso();
        p.setVisible(true);
        
    }//GEN-LAST:event_btn_procesarActionPerformed

    private void txt_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyReleased
        // TODO add your handling code here:
         this.limpiarGrilla();
        this.cargarGrilla(txt_buscar.getText().toLowerCase());
    }//GEN-LAST:event_txt_buscarKeyReleased

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
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                try {
                    new Interfaz().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Background;
    private javax.swing.JPanel Busqueda;
    private javax.swing.JPanel Listado;
    private javax.swing.JPanel Procesar;
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_procesar;
    private javax.swing.JTable grid_listado;
    private javax.swing.JScrollPane scrPane_panel;
    private javax.swing.JTextField txt_buscar;
    // End of variables declaration//GEN-END:variables
}
