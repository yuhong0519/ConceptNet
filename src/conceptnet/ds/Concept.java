/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conceptnet.ds;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Hong
 */
public class Concept implements Serializable{
    private ArrayList<Edge> inEdges = new ArrayList<Edge>();
    private ArrayList<Edge> outEdges = new ArrayList<Edge>();
    
    public String name = null;

    
    public Concept(String name){
        this.name = name;
    }
    public void addInEdge(Edge e){
        inEdges.add(e);
    }
    public void addOutEdge(Edge e){
        outEdges.add(e);
    }
   
    /**
     * Get out edge list that has relation rel
     * @param rel
     * @return 
     */
    public ArrayList<Edge> getOutEdges(String rel){
        ArrayList<Edge> ret = new ArrayList<Edge>();
        for(Edge e : outEdges){
            if(e.rel.equalsIgnoreCase(rel)){
                ret.add(e);
            }
        }
        return ret;
    }
    
    /**
     * Get all out edges
     * @return 
     */
    public ArrayList<Edge> getOutEdges(){
        return outEdges;
    }
    
    /**
     * Get in edge list that has relation rel
     * @param rel
     * @return 
     */
    public ArrayList<Edge> getInEdges(String rel){
        ArrayList<Edge> ret = new ArrayList<Edge>();
        for(Edge e : inEdges){
            if(e.rel.equalsIgnoreCase(rel)){
                ret.add(e);
            }
        }
        return ret;
    }
    
    /**
     * Get all in edges
     * @return 
     */
    public ArrayList<Edge> getInEdges(){
        return inEdges;
    }
    
}
