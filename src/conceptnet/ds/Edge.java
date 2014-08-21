/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conceptnet.ds;

import java.io.Serializable;
import org.json.*;

/**
 *
 * @author Hong
 */
public class Edge implements Serializable{
    public String start = null;
    public String end = null;
    public String id = null;
    public double weight = 0;
    public String surfaceText = null;
    public String rel = null;
    
    public Edge(JSONObject jo){
        try{
            start = jo.getString("startLemmas");
            if(start.indexOf(" ") >= 0){
                start = start.replace(' ', '_');
            }
            if(start.indexOf("\\") >= 0){
                start = start.replace('\\', ' ').trim();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try{
            end = jo.getString("endLemmas");
            if(end.indexOf(" ")>=0){
                end = end.replace(' ', '_');            
            }
            if(end.indexOf("\\") >= 0){
                end = end.replace('\\', ' ').trim();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try{
            id = jo.getString("id");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try{
            weight = jo.getDouble("weight");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try{
            rel = jo.getString("rel");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try{
            surfaceText = jo.getString("surfaceText");
        }
        catch(Exception e){
//            System.err.println("Cannot find surfaceText for edge start: " + start + ", end: " + end);
        }
    }
    
}
