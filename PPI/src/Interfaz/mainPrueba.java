/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaz;

import BD.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Martin
 */
public class mainPrueba {
    public static void main (String args[]) throws SQLException
    {
     
        Conexion con = new Conexion();
        ResultSet rs = con.tabla("Documento");
        
        System.out.println(rs.toString());
        
    }    
}
