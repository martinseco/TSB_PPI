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
        
        sc.useDelimiter("[^a-zA-ZñÑá-úÁ-Ú]");
        
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
    
}

