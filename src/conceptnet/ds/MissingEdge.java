/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conceptnet.ds;

/**
 *
 * @author Hong
 */
public class MissingEdge implements Comparable<MissingEdge>{

    @Override
    public int compareTo(MissingEdge o) {
        if(corr > o.corr){
            return -1;
        }
        else if(corr < o.corr){
            return 1;
        }
        else 
            return 0;
    }
    
    public enum Type {in, out};
    public String start;
    public String end;
    public String rel;
    public String newCenter;
    public double corr = -1;    
    public Type type = Type.in;
           
    public MissingEdge(String start, String end, String rel, String newCenter, double corr, Type type){
        this.start = start;
        this.end = end;
        this.rel = rel;
        this.newCenter = newCenter;
        this.corr = corr;
        this.type = type;
    }
    
    @Override
    public String toString(){
        if(type == Type.in){
            return start + " " + rel + " " + newCenter + ": " + corr + "; (" + end + ")";
        }
        else{
            return newCenter + " " + rel + " " + end + ": " + corr + "; (" + start + ")";
        }
    }
}
