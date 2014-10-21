/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author luciano
 */
public class Insercion {
    
    private String nombreDoc;
    private Statement stmt;
    private Connection c;
    
    
    public Insercion(String nombre){
    
        nombreDoc = nombre;
    }
    
    public void conectar() throws SQLException{
        
        c = DriverManager.getConnection("jdbc:sqlite:BD.s3db");//Conexion con BD abierta.
        stmt = c.createStatement();
    }
    
    public void procesar(HashMap contadorPalabras) throws SQLException{
        
        this.conectar();
        HashMap <String, Integer> palabrasArchivo  = contadorPalabras;
        String sql = "";
        
        try
        {
            c.setAutoCommit(false);
            for (String key : palabrasArchivo.keySet()) 
            {            
                sql = "INSERT OR REPLACE INTO Palabra (palabra, cantidad) "; 
                sql += "VALUES ('";
                sql += key + "',(COALESCE((SELECT cantidad FROM palabra WHERE palabra LIKE '" + key + "') + '" ;
                sql += palabrasArchivo.get(key) + "', '" + palabrasArchivo.get(key) +"')))";

                stmt.executeUpdate(sql);  
            }
            c.commit();
            c.setAutoCommit(true);
        }catch(SQLException e) 
        {
            c.rollback();
        }
        
        stmt.close();
        c.close();
        System.out.println(palabrasArchivo.toString());
    }
    
    
            
            
}
