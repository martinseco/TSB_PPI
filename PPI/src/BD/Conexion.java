/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

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
       
       
       public DefaultTableModel buildTableModel()
       {
           try
           {
                ResultSet rs = this.obtenerTablaAMostrar();
                ResultSetMetaData metaData = rs.getMetaData();

                 // names of columns
                 Vector<String> columnNames = new Vector<String>();
                 int columnCount = metaData.getColumnCount();
                 for (int column = 1; column <= columnCount; column++) 
                 {
                     columnNames.add(metaData.getColumnName(column));
                 }

                 // data of the table
                 Vector<Vector<Object>> data = new Vector<Vector<Object>>();
                 while (rs.next()) {
                     Vector<Object> vector = new Vector<Object>();
                     for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) 
                     {
                         vector.add(rs.getObject(columnIndex));
                     }
                     data.add(vector);
                 }
           return new DefaultTableModel(data, columnNames);
           }
           catch(Exception e){return null;}
           
        

       
       }
       
       private ResultSet obtenerTablaAMostrar() throws SQLException
       {
           String sql ="";
           
           sql+= "SELECT palabra,COUNT(PxD.palabra_id), SUM(PxD.cantidad) FROM PalabraXDocumento PxD ";
           sql += "INNER JOIN Palabra p ON p.id = PxD.palabra_id ";
           sql += "GROUP BY palabra";
           
           c.createStatement();
           return stmt.executeQuery(sql);
       
       }
}
