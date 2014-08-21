/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conceptnet;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.net.*;

/**
 *
 * @author Hong
 */
public class JSONStringObtain {
    
    public static String get(String urls){
        StringBuffer ret = new StringBuffer();
        try{
            URL url = new URL(urls);
            URLConnection urlConn = url.openConnection(); 
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream())); 
            String s; 

            while ((s = br.readLine()) != null)
            { 
              ret.append(s);
            } 
            br.close(); 
        }
        
        catch (Exception e) {
            e.printStackTrace();
        } 
        return ret.toString();
    }
}
