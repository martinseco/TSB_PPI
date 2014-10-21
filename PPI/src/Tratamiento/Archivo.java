/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tratamiento;

import BD.Conexion;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;

/**
 *
 * @author luciano
 */
public class Archivo {
    
    private File f;
    /*Modifico path default ya que solo el doble barra invertida se interpreta como divisor de directorios.
    */
    public Archivo(String path){
      String pathFixed = path.replace("\\", "\\\\");
      
      f =  new File(pathFixed);
    }
    
    //Tengo un HashMap con todas las palabras de todos los archivos que me manden.
    //Con un HashMap deberia ser suficiente para armar un Mapa de todas las palabras 
    //que se encuentren un el conjunto de archivos que seleccione el usuario.
    
    //HASHMAP DE LAS PALABRAS EN EL ARCHIVO.
    private HashMap mapaPalabras() throws FileNotFoundException{//corregir bien despues, verificar file no nula.Corregir delimiter.
                                                                    
        HashMap <String, Integer> contadorPalabras  = new HashMap <>();
        
        Scanner sc = new Scanner(f);
//        sc.useDelimiter(" ").useDelimiter(".").useDelimiter(",");
        Pattern patron = Pattern.compile("[0-9][ \\t\\n\\x0B\\f\\r]$");
        sc.useDelimiter(patron);
        
        while(sc.hasNext()){
            String palabra = sc.next();
            
            Integer i = contadorPalabras.get(palabra);
            if (i == null) {
                contadorPalabras.put(palabra, 1);
            }
            else
                contadorPalabras.put(palabra, i+1);
        }
        return contadorPalabras;
    }
    
    
    
    /*Obtengo un HashMap a partir de una ResultSet que obtengo de una consulta.
      Tendria que obtener una de estas por cada tabla que deseo consultar, para verificar si 
      es INSERT o si debo realizar un UPDATE.
    
    HASHMAP DE LO QUE YA EXISTE EN LA PERSISTENCIA.
    */
    /*Situo el cursor en determinada fila y de alli obtengo los 2 objetos y los inserto en el HashMap 
    en la misma posicion en que la recupero de la ResultSet.
    */
    private HashMap mapaBD(ResultSet rs) throws SQLException{
    
        HashMap resultado = new HashMap();
        
        while(rs.next()){//Movimiento entre las filas de la tabla.
        
            resultado.put(rs.getObject(1),rs.getObject(2));//Obtengo objeto en las columnas 1 y 2 de la fila en la 
                                                            // que estoy parado.
        }
        
        return resultado;
    }
    
    
    
    
    /*Obtengo el ResultSet de la consulta. Tendré que hacer tres consultas, una por cada tabla;es decir, 
    tres invocaciones a este metodo.
    */
    private ResultSet tablaConsulta(String tabla) throws SQLException{
    
        Conexion con = new Conexion();
        ResultSet resultado = con.tabla(tabla);
        con.cerrarConexiones();
        
        return resultado;
    }
    
    
    
    
    /*Comparar los 2 HashMaps  y determinar si la clave ya existe, de no existir, esto determinaría un INSERT,
    de existir sería un UPDATE.
    Voy a crear 2 HashMaps, uno determina el HashMap que respondera a la consulta INSERT y el otro
    el que respondera a la consulta UPDATE.
    */
    public HashMap[] compararMapas() throws SQLException, FileNotFoundException{
    //[0] = UPDATE; [1] = INSERT;
        HashMap[] resultado = new HashMap[2];
        HashMap bd;
        HashMap archivo;
        
        bd = this.mapaBD(this.tablaConsulta("Palabra"));
        archivo = this.mapaPalabras();
        
        Set claves = archivo.keySet();
        Iterator it = claves.iterator();
        Object key;
        Object value;
        int insercion;
        
        while(it.hasNext()){
            key = it.next();//Obtengo las claves de todo el mapa.
            
            if (bd.containsKey(key)) {//Ya esta en la BD
                insercion = 0;
                }
            else
                    insercion = 1; //No esta en la BD
            
        value = bd.get(key);//Obtengo el valor para la clave asociada.
        resultado[insercion].put(key, value); //Inserto en el HashMap correspondiente.       
            
        }
        
        return resultado;
    }
    
    
    
    
}

