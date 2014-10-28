/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tratamiento;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.mozilla.universalchardet.UniversalDetector;
/**
 *
 * @author Martin
 */
public class Detector {
    
    public static String determinarEncoding(File f) throws FileNotFoundException, IOException
    {    
        byte[] buf = new byte[4096];        
        java.io.FileInputStream fis = new java.io.FileInputStream(f);

        UniversalDetector detector = new UniversalDetector(null);

        int nread;
        while ((nread = fis.read(buf)) > 0 && !detector.isDone()) 
        {
            detector.handleData(buf, 0, nread);
        }
        
        detector.dataEnd();

        String encoding = detector.getDetectedCharset();
        if (encoding != null) 
            return encoding;
        else
            return "ISO-8859-1";
    }
}
