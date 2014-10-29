/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaz;

import BD.Conexion;
import BD.Insercion;
import Tratamiento.Archivo;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Martin
 */
public class mainPrueba {
    public static void main (String args[]) throws SQLException, FileNotFoundException
    {
     
      Archivo a = new Archivo("16082-8.txt");
//      Insercion i = new Insercion("16082-8.txt");
//      i.procesar(a.mapaPalabras());
    }    
}
