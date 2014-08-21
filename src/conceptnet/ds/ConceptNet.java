/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conceptnet.ds;

import conceptnet.ConceptNetNodeComp;
import conceptnet.ds.Concept;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Hong
 */
public class ConceptNet implements Serializable{
    public ArrayList<ConceptNetNode> related = new ArrayList<ConceptNetNode>();
    public ArrayList<ConceptNetNode> property = new  ArrayList<ConceptNetNode>();
    
    private ArrayList<ConceptNetNode> allNode = null;
    private double[][] adjacentMat = null;
    
    private final double relNodeWeight = 0.5;
    private final double selfCorr = 10;
    
    
    public ConceptNetNode center;
    public ConceptNet(ConceptNetNode c){
        center = c;
        related.add(c);
    }
    
    public double[][] getAdjacentMat(){
        if(adjacentMat == null){
            computeAdjacent();
        }
        return adjacentMat;
    }
    
    public int getCenterIndex(){
        if(allNode == null){
            computeAdjacent();
        }
        return Collections.binarySearch(allNode, center);
    }
    
    public void setCenter(ConceptNetNode cnn){
        center.set(cnn);
        allNode = null;
        adjacentMat = null;
    }
    
    
    public int getNodeIndex(ConceptNetNode cnn){
        if(allNode == null){
            computeAdjacent();
        }
        return Collections.binarySearch(allNode, cnn);
    }
    
    public ConceptNetNode getStartNode(Edge e){
        String name = e.start;
        ConceptNetNode cnn = getPropertyNode(name);
        if(cnn == null){
            cnn = getRelatedNode(name);
        }
        return cnn;
    }
    
    public ConceptNetNode getEndNode(Edge e){
        String name = e.end;
        ConceptNetNode cnn = getPropertyNode(name);
        if(cnn == null){
            cnn = getRelatedNode(name);
        }
        return cnn;
    }
       
    private void computeAdjacent(){
        allNode = new ArrayList<ConceptNetNode>();
        for(ConceptNetNode cnn : related){
            if(Collections.binarySearch(allNode, cnn) < 0){
                allNode.add(cnn);
                Collections.sort(allNode);
            }
        }
        for(ConceptNetNode cnn : property){
            if(Collections.binarySearch(allNode, cnn) < 0){
                allNode.add(cnn);
                Collections.sort(allNode);
            }
        }
        adjacentMat = new double[allNode.size()][allNode.size()];
        for(ConceptNetNode cnn : related){
            int i1 = Collections.binarySearch(allNode, cnn);
            if(i1 < 0){
                System.err.println("Error building adjacent matrix!");
                return;
            }
            ArrayList<Edge> cnne = (cnn.getInRelEdge());
            for(Edge e : cnne){
                int i2 = Collections.binarySearch(allNode, getRelatedNode(e.start));
                if(i2 < 0){
                    System.err.println("Error building adjacent matrix!");
                    return;
                }
                adjacentMat[i1][i2] = e.weight * relNodeWeight;
                adjacentMat[i2][i1] = e.weight * relNodeWeight;
                
            }
            cnne = (cnn.getOutRelEdge());
            for(Edge e : cnne){
                int i2 = Collections.binarySearch(allNode, getRelatedNode(e.end));
                if(i2 < 0){
                    System.err.println("Error building adjacent matrix!");
                return;
                }
                adjacentMat[i1][i2] = e.weight * relNodeWeight;
                adjacentMat[i2][i1] = e.weight * relNodeWeight;
            }
            
            cnne = (cnn.getInPropertyEdge());
            for(Edge e : cnne){
                int i2 = Collections.binarySearch(allNode, getPropertyNode(e.start));
                if(i2 < 0){
                    System.err.println("Error building adjacent matrix!");
                return;
                }
                adjacentMat[i1][i2] = e.weight;
                adjacentMat[i2][i1] = e.weight;
                
            }
            cnne = (cnn.getOutPropertyEdge());
            for(Edge e : cnne){
                ConceptNetNode t = getPropertyNode(e.end);
                if(t == null){
                    System.err.println("Error building adjacent matrix: Cannot find: " + e.end);
                    return;
                }
                int i2 = Collections.binarySearch(allNode, t);
                if(i2 < 0){
                    System.err.println("Error building adjacent matrix!");
                    return;
                }
                adjacentMat[i1][i2] = e.weight;
                adjacentMat[i2][i1] = e.weight;
            }
        }
        for(int i = 0; i < adjacentMat.length; i++){
            adjacentMat[i][i] = selfCorr;
        }
        
    }
    
    /**
     * Get related node with name : name
     * @param name
     * @return null if not found
     */
    public ConceptNetNode getRelatedNode(String name){
        for(ConceptNetNode c : related){
            if(c.name.equalsIgnoreCase(name)){
                return c;
            }
        }
        return null;
    }
    
    /**
     * Get property node with name : name
     * @param name
     * @return 
     */
    public ConceptNetNode getPropertyNode(String name){
        for(ConceptNetNode c : property){
            if(c.name.equalsIgnoreCase(name)){
                return c;
            }
        }
        return null;
    }
    
    public ArrayList<ConceptNetNode> getRankedCorrelatedAll(double[][] corr){
        ArrayList<ConceptNetNode> ret = new ArrayList<ConceptNetNode>();
        if(allNode == null){
//            computeAdjacent();
            System.err.println("Should compute the ajacent matrix before geting correlated concepts!");
            return null;
        }
        int ind = allNode.indexOf(center);
        double[] ccorr = corr[ind];
        for(int i = 0; i < ccorr.length; i++){
            allNode.get(i).corrScore = ccorr[i];
        }
        ret.addAll(allNode);
        Collections.sort(ret, new ConceptNetNodeComp());
        return ret;
    }
    
    /**
     * Get ranked correlated concepts that are in the related list
     * @param corr
     * @return 
     */
    public ArrayList<ConceptNetNode> getRankedCorrelatedConcept(double[][] corr){
        ArrayList<ConceptNetNode> all = getRankedCorrelatedAll(corr);
        ArrayList<ConceptNetNode> ret = new ArrayList<ConceptNetNode>();
        for(ConceptNetNode cnn : all){
            if(cnn != center && related.contains(cnn)){
                ret.add(cnn);
            }
        } 
        return ret;
    }
    
    /**
     * Check if e is "in" list. "in" means the start or end are the same, the rel are the same 
     * @param list
     * @param e
     * @param type 0: check start; 1: check end
     * @return 
     */
    private boolean checkPropertyMatch(ArrayList<Edge> list, Edge e, int type){
        for(Edge ee : list){
            if(ee.rel.compareToIgnoreCase(e.rel) == 0){
                if(type == 0 && ee.start.compareToIgnoreCase(e.start) == 0){
                    return true;
                }
                if(type == 1 && ee.end.compareToIgnoreCase(e.end) == 0){
                    return true;
                }
                
            }
        }
        return false;
    }
    
    /**
     * Given a list of nodes, compute the missing properties from nodes for center
     * @param nodes
     * @param corr
     * @return 
     */
    public ArrayList<MissingEdge> getMissingProperty(ArrayList<ConceptNetNode> nodes, double[][] corr){
        ArrayList<MissingEdge> ret = new ArrayList<>();
        ArrayList<Edge> cin = center.getInPropertyEdge();
        ArrayList<Edge> cout = center.getOutPropertyEdge();
        
        int centerInd = Collections.binarySearch(allNode, center);
        
        for(ConceptNetNode cnn : nodes){
            ArrayList<Edge> tp = cnn.getInPropertyEdge();            
            for(Edge e : tp){

                if(!checkPropertyMatch(cin, e, 0)){
                    int ind = Collections.binarySearch(allNode, getPropertyNode(e.start));
                    if(ind < 0){
                        System.err.println("Error when getting missing property");
                        return null;
                    }
                    MissingEdge me = new MissingEdge(e.start, e.end, e.rel, center.name, corr[centerInd][ind], MissingEdge.Type.in);                    
                    ret.add(me);
                }
            }            
            tp = cnn.getOutPropertyEdge();            
            for(Edge e : tp){
//                if(e.end.compareTo("nutritious") == 0){
//                    System.out.println("");
//                }
                if(!checkPropertyMatch(cout, e, 1)){
                    int ind = Collections.binarySearch(allNode, getPropertyNode(e.end));
                    if(ind < 0){
                        System.err.println("Error when getting missing property");
                        return null;
                    }
                    MissingEdge me = new MissingEdge(e.start, e.end, e.rel, center.name, corr[centerInd][ind], MissingEdge.Type.out);                    
                    ret.add(me);
                }
            }
        }        
        Collections.sort(ret);
        return ret;
    }
    

    
    
}
