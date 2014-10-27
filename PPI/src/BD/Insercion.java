/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luciano
 */
public class Insercion {
    
    private String nombreDoc;
    private Statement stmt;
    private Connection c;
    private HashMap <String, Integer> palabrasArchivo;
    
    public Insercion(String nombre){
    
        nombreDoc = nombre;
    }
    
    public void conectar() throws SQLException{
        
        c = DriverManager.getConnection("jdbc:sqlite:DBFinal.db");//Conexion con BD abierta.
        c.setAutoCommit(false);
        stmt = c.createStatement();
    }
    
    public void procesar(HashMap contadorPalabras) throws SQLException{
        
        this.conectar();
        palabrasArchivo  = contadorPalabras;//Ver si va en el constructor o no.
        String sql = "";
        int idDoc;
        int idPal;
        
        try
        {
            idDoc = this.getId(nombreDoc, "Documento");
            if(idDoc == -1)
            {   
                sql = this.insertDocumento();
                stmt.executeUpdate(sql);
            }            
            
            for (String key : palabrasArchivo.keySet()) 
            {  
                key = key.toLowerCase();
                idPal = this.getId(key, "Palabra");
                
                if(idPal == -1)
                {
                    sql = this.insertarPalabra(key);
                    stmt.executeUpdate(sql); 
                    
                }
                
            }
           
            
            
            
          c.commit();
          c.setAutoCommit(true);
        }catch(SQLException e) 
        {
            System.out.println("Sali po el SqlExc"+e);
            c.rollback();
        }
        
        stmt.close();
        c.close();        
    }
    
    private int getId(String key, String tabla)
    {
        String sql = "";
        int id = -1;
        
        if (key != null && !" ".equals(key) && tabla.equals("Palabra")) 
        {
            
           ResultSet rs;
            try {
                rs = stmt.executeQuery("SELECT id FROM Palabra WHERE palabra = '" + key +"' LIMIT 1");
                id = rs.getInt(1);
                }
           catch (SQLException ex) {
                id = -1;
           }
           //UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='Palabra'
            
        }
        else
        {
            ResultSet rs;
            try {
                rs = stmt.executeQuery("SELECT id FROM Documento WHERE nombre = '" + key +"' LIMIT 1");
                id = rs.getInt(1);
            }
           catch (SQLException ex) {
                id = -1;
           }            
        }
       return id;
    }
    
//    if (id == -1) {
//                sql = "INSERT INTO Palabra (id,palabra) "; 
//                sql += "VALUES ($next_id, '"+key+"')";
//            }
    private int getLastIdPal()
    {
        int id;
        ResultSet rs;
        String sql = "SELECT MAX(id) FROM Palabra";
        
        try{
            
            rs = stmt.executeQuery(sql);
            id = rs.getInt(1);
        }catch(SQLException e)
        { 
            id = -1;
        }
        
        return id;
    }
    
    
    private String insertDocumento()
    {
        String sql = "";
        ResultSet rs;
        int id;
        
//        try {
//            rs = stmt.executeQuery("SELECT id FROM Documento WHERE nombre = '" + nombreDoc +"' LIMIT 1");
//            id = rs.getInt(1); 
//         } catch (SQLException ex) {
//            id = -1;
 //        }
 //       if (id == -1) {
            sql = "INSERT INTO Documento (id,nombre) ";
            sql += "VALUES ($next_id, '";
            sql += nombreDoc + "')";
//        }
        
        return sql;
    }
    
    private String insertarPalabra(String key)
    {
        String sql ="";
        if (key != null && !" ".equals(key)) {
             sql = "INSERT INTO Palabra (id,palabra) "; 
             sql += "VALUES ($next_id, '"+key+"')";
        }
        
       
        return sql;
    }
   
    
    
    
            
            
}
