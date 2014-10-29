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
    
    public Insercion(){}
    
    private void conectar() throws SQLException{
        
        c = DriverManager.getConnection("jdbc:sqlite:DBFinal.db");//Conexion con BD abierta.
        c.setAutoCommit(false);
        stmt = c.createStatement();
    }
    
    public void procesar(String nombreArch, HashMap contadorPalabras) throws SQLException{
        
        nombreDoc = nombreArch;        
        this.conectar();
        palabrasArchivo  = contadorPalabras;
        String sql = "";
        int idDoc;
        int idPal;
        boolean existeArch = true;
        
        try
        {
            idDoc = this.getId(nombreDoc, "Documento");
            
            if(idDoc == -1)//Si no existe el documento, lo inserto
            {   
                sql = this.insertDocumento();
                stmt.executeUpdate(sql);
                idDoc = this.getLastId("Documento");
                existeArch = false;
            }            
            
            for (String key : palabrasArchivo.keySet()) 
            {  
                idPal = this.getId(key, "Palabra");
                
                if(idPal == -1)//Si no existe la palabra, la inserto
                {
                    sql = this.insertarPalabra(key);
                    stmt.executeUpdate(sql);
                    idPal = this.getLastId("Palabra");
                }
                
                if(existeArch)
                    sql = this.updateCant(idDoc, idPal, key);//Armo querry para actualizar la cantidad de veces que aparece key en el documento, puede venir vacia                                        
                
                else
                    sql = this.insertPalabraDocumento(idDoc, idPal, key);//Armo querry para insertar por primera vez en PalabrasXDocumento
                
                if(sql.length() > 0)
                    stmt.executeUpdate(sql);                
            }
            
            c.commit();
            c.setAutoCommit(true);
            
        }catch(SQLException e) 
        {
            System.out.println("Error de SQL: "+e);
            c.rollback();
        }
        
        stmt.close();
        c.close();        
    }
    
    private int getId(String key, String tabla)
    {
        String sql = "";
        int id;
        
        if (key != null && !"".equals(key) && tabla.equals("Palabra")) 
        {            
            ResultSet rs;
            key = key.toLowerCase();
            try 
            {
                rs = stmt.executeQuery("SELECT id FROM Palabra WHERE palabra = '" + key +"' LIMIT 1");
                id = rs.getInt(1);                
            }
            catch (SQLException ex) 
            {
                id = -1;
            }            
        }
        else
        {
            ResultSet rs;
            
            try 
            {
                rs = stmt.executeQuery("SELECT id FROM Documento WHERE nombre = '" + key +"' LIMIT 1");
                id = rs.getInt(1);
            }
            catch (SQLException ex) 
            {
                 id = -1;
            }            
        }
       return id;
    }    

    private int getLastId(String tabla)
    {
        int id;
        ResultSet rs;
        String sql = "SELECT MAX(id) FROM '" + tabla + "'";
        
        try
        {
            rs = stmt.executeQuery(sql);
            id = rs.getInt(1);
        }
        catch(SQLException e)
        { 
            id = -1;
        }
        
        return id;
    }
    
    private int getCantidad(int idDoc, int idPal)
    {
        int cant = 0;
        ResultSet rs;
        try 
        {
            rs = stmt.executeQuery("SELECT cantidad FROM PalabraXDocumento WHERE id_Palabra = "+ idPal + " AND id_Documento = " + idDoc + "");
            cant = rs.getInt(1);
        }
        catch(SQLException e)
        {
            //Ver que mensaje entregar
        }        
        
        return cant;       
    }
    
    private String insertDocumento()
    {
        String sql = "";
        ResultSet rs;
        int id;

        sql = "INSERT INTO Documento (id,nombre) ";
        sql += "VALUES ($next_id, '";
        sql += nombreDoc + "')";
        
        return sql;
    }
    
    private String insertarPalabra(String key)
    {
        String sql ="";
        if (key != null && !" ".equals(key)) 
        {
             sql = "INSERT INTO Palabra (id,palabra) "; 
             sql += "VALUES ($next_id, '"+key+"')";
        }       
       
        return sql;
    }
    
    private String insertPalabraDocumento(int idDoc, int idPal, String key)
    {
        String sql;
        
        sql = "INSERT INTO PalabraXDocumento (id_Documento, id_Palabra, cantidad) ";
        sql += "VALUES (" + idDoc + ", " + idPal + ", " + palabrasArchivo.get(key) + ")";
        
        
        return sql;
    }        
    
   private String updateCant(int idDoc, int idPal, String key)
   {
        String sql = "";
       
        int cant = this.getCantidad(idDoc, idPal);
        int cantNueva = palabrasArchivo.get(key);
        if(cant > cantNueva || cant < cantNueva)//Si la cantidad de veces que la palabra aparece en el doc cambio desde el ultimo procesamiento, armo una querry para actualizar
        {
            sql = "UPDATE PalabraXDocumento SET cantidad=" + cantNueva + " WHERE id_Palabra=" + idPal +" AND id_Documento=" + idDoc +"";            
        }    
            
        return sql;
   }        
}
