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
public class ConceptNetNode implements Comparable<ConceptNetNode>, Serializable {
    private ArrayList<Edge> inRelEdges = new ArrayList<Edge>();
    private ArrayList<Edge> outRelEdges = new ArrayList<Edge>();
    private ArrayList<Edge> inPropertyEdges = new ArrayList<Edge>();
    private ArrayList<Edge> outPropertyEdges = new ArrayList<Edge>();
    
    public String name = null;
    public double corrScore = 0;
    
//    Has the concept been expaned? used in building concept net
    public boolean expanded = false;
    
//    the closeness to the center node
    public int layer = -1;
    
    public ConceptNetNode(String name){
        this.name = name;
    }
    
    public ConceptNetNode(ConceptNetNode source){
        set(source);
    }
    
    public void set(ConceptNetNode source){
        this.name = source.name;
        this.corrScore = source.corrScore;
        this.expanded = source.expanded;
        this.layer = source.layer;
        this.inRelEdges.clear();
        this.outRelEdges.clear();
        this.inPropertyEdges.clear();
        this.outPropertyEdges.clear();
        this.inRelEdges.addAll(source.inRelEdges);
        this.outRelEdges.addAll(source.outRelEdges);
        this.inPropertyEdges.addAll(source.inPropertyEdges);
        this.outPropertyEdges.addAll(source.outPropertyEdges);        
    }
    
    public void setInRelEdges(ArrayList<Edge> inRel){
        inRelEdges = inRel;
    }
    
    public void setOutRelEdges(ArrayList<Edge> outRel){
        outRelEdges = outRel;
    }
    
    public void setInPropertyEdges(ArrayList<Edge> inP){
        inPropertyEdges = inP;
    }
    
    public void setOutPropertyEdges(ArrayList<Edge> outP){
        outPropertyEdges = outP;
    }
    
    
    public void addInRelEdge(Edge e){
        inRelEdges.add(e);            
    }
    public void addOutRelEdge(Edge e){
        outRelEdges.add(e);            
    }
    
    public void addInRelEdge(ArrayList<Edge> e){
        inRelEdges.addAll(e);            
    }
        
    public void addInPropertyEdge(Edge e){
        inPropertyEdges.add(e);            
    }
    
    public void addInPropertyEdge(ArrayList<Edge> e){
        inPropertyEdges.addAll(e);            
    }
    
        public void addOutRelEdge(ArrayList<Edge> e){
        outRelEdges.addAll(e);            
    }
        
    public void addOutPropertyEdge(Edge e){
        outPropertyEdges.add(e);            
    }
    
    public void addOutPropertyEdge(ArrayList<Edge> e){
        outPropertyEdges.addAll(e);            
    }
    
    public ArrayList<Edge> getInRelEdge(){
        return inRelEdges;
    }
    public ArrayList<Edge> getOutRelEdge(){
        return outRelEdges;
    }
    
    public ArrayList<Edge> getInPropertyEdge(){
        return inPropertyEdges;
    }
    public ArrayList<Edge> getOutPropertyEdge(){
        return outPropertyEdges;
    }
    
    public ArrayList<Edge> getInRelEdge(String rel){
        ArrayList<Edge> ret = new ArrayList<Edge>();
        for(Edge e : inRelEdges){
            if(e.rel.equalsIgnoreCase(rel)){
                ret.add(e);
            }
        }
        return ret;
    }
    
    public ArrayList<Edge> getOutRelEdge(String rel){
        ArrayList<Edge> ret = new ArrayList<Edge>();
        for(Edge e : outRelEdges){
            if(e.rel.equalsIgnoreCase(rel)){
                ret.add(e);
            }
        }
        return ret;
    }

    @Override
    public int compareTo(ConceptNetNode o) {
        return this.name.compareToIgnoreCase(o.name);
    }
}
