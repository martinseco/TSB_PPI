/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author luciano
 */
public class Conexion {
    
       private Connection c;
       private Statement stmt;
       
       public Conexion() throws SQLException{//creo conexion.
       
           c = DriverManager.getConnection("jdbc:sqlite:BD.s3db");//Conexion con BD abierta.
       }
       /*Obtengo una tabla ResultSet completo de la tabla que mande como parámetro.
        Me sirve para obtener una tabla que luego pasara a ser un Mapa por cada tabla de la BD.
       El Mapa luego será comparado con el Mapa de las palabras de entrada para determinar el INSERT o UPDATE en 
       las tablas.
       
       Si la tabla que quiero recuperar es el Documento, modifico la consulta
       para amoldarla al HashMap de dos valores.
       */
       public ResultSet tabla(String tabla) throws SQLException{
           
           stmt = c.createStatement();
           StringBuilder sb = new StringBuilder();
           
           if ("Documento".equals(tabla)) {
               sb.append("SELECT ").append("id, nombre FROM ").append(tabla);
           }
           else
               sb.append("SELECT ").append("* FROM ").append(tabla);
  
           ResultSet rs = stmt.executeQuery(sb.toString());
           return rs;
       }
       
       public void cerrarConexiones() throws SQLException{
       
           stmt.close();
           c.close();
       }
}
