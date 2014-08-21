/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conceptnet;

import conceptnet.ds.Concept;
import conceptnet.ds.Edge;
import java.io.File;
import java.io.*;
import java.util.*;
import org.json.*;

/**
 *
 * @author Hong
 */
public class ConceptBuilder {
    private static HashMap<String, Concept> allConcepts = new HashMap<String, Concept>();
    private static HashMap<String, Edge> allEdges = new HashMap<String, Edge>();
    private static String conceptPath = "concepts/";
    
    private static String urlBase = "http://conceptnet5.media.mit.edu/data/5.2/c/en/";
    private static String[] stopWord = new String[]{"this", "it", "you", "there", "here","often", "usually", "sometimes", "commonly", "generally", "noun", "something"};
    
    
    public boolean compareString(String s1, String s2){
        if(s1.compareToIgnoreCase(s2) == 0){
            return true;
        }
        if(s1.length() > s2.length()){
            String t = s1;
            s1 = s2;
            s2 = t;
        }
        
        return false;
    }
    
    public static Concept readConcept(String name){
        Concept c = null;
        String path = conceptPath+name;
        File f = new File(path);
        if(!f.exists() || f.isDirectory()){
            return null;
        }
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            c = (Concept) (ois.readObject());
            ois.close();
        }
        catch(Exception e){
            e.printStackTrace();                   
        }
        
        return c;
    }
    
    public static void saveConcept(Concept c){
        String path = conceptPath+c.name;
        File f = new File(path);
        if(f.exists() || f.isDirectory()){
            System.out.println("Try to save a concept that is already saved: " + c.name);
            return;
        }
        try{
            ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream(path));
            ois.writeObject(c);
            ois.close();
        }
        catch(Exception e){
            e.printStackTrace();                   
        }
    }
    
    
    public static  Concept createConcept(String name){
        if(name.indexOf(" ")>=0){
            name = name.replace(' ', '_');            
        }
        Concept c = getConcept(name);
        if(c != null){
            return c;
        }
        c = readConcept(name);
        if(c != null){
            allConcepts.put(name, c);
            return c;
        }        
        c = new Concept(name);
        
        String conceptStr = JSONStringObtain.get(urlBase+name);
        if(conceptStr.length() == 0){
            return null;
        }
        try{
            JSONObject jso = new JSONObject(conceptStr);
            JSONArray ae = jso.getJSONArray("edges");
            Flag:
            for(int i = 0; i < ae.length(); i++){
                JSONObject e = ae.getJSONObject(i);
                String id = e.getString("id");
                Edge te = allEdges.get(id);
                if(te == null){
                    te = new Edge(e);
                    allEdges.put(id, te);
                }
                if(te.start.equalsIgnoreCase(te.end)){
                    continue;
                }
                
                for(String s : stopWord){
                    if(s.equalsIgnoreCase(te.start) || s.equalsIgnoreCase(te.end)){
                        continue Flag;
                    }
                }
                if(te.start.equalsIgnoreCase(name)){
                    c.addOutEdge(te);
                }
                else if(te.end.equalsIgnoreCase(name)){
                    c.addInEdge(te);
                }
                              
            }           
            
            allConcepts.put(name, c);
            saveConcept(c);
            return c;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    
    /**
     * Get the concept name. If not exist, create one from web.
     * @param name
     * @return 
     */
    public static Concept getConcept(String name){
        Concept c = allConcepts.get(name);
        return c;
    }
    
    public static Edge getEdge(String id){
        return allEdges.get(id);
    }
    
    /**
     * Add c with name. If name already exists, replace the old one with c
     * @param name
     * @param c 
     */
    public static void addConcept(String name, Concept c){
        allConcepts.put(name, c);
    }
    
    public static void main(String[] args){
        Concept c = createConcept("car");
    }
    
}
