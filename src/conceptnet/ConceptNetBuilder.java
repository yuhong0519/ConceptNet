/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conceptnet;

import NodeSimilarity.SimRank;
import NodeSimilarity.SpreadingActivation;
import conceptnet.ds.Concept;
import conceptnet.ds.ConceptNet;
import conceptnet.ds.ConceptNetNode;
import conceptnet.ds.Edge;
import conceptnet.ds.MissingEdge;
import java.io.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author Hong
 */
public class ConceptNetBuilder {
    private static String[] parentRel = new String[]{"/r/IsA"};
    private static String[] siblingRel = new String[]{};
    private static String[] propertyOutRel = new String[]{"/r/CapableOf", "/r/HasProperty", "/r/UsedFor", 
                                            "/r/HasA", "/r/AtLocation"};
    private static String[] propertyInRel = new String[]{"/r/PartOf"};
    static boolean debug = false;
    
    private static int lowerExplor = 1, upperExplore = 1, maxLayer = 2;
    
    public static ConceptNet buildConceptNet(String start){
        ConceptNet cn = findRelatedConcepts(start, upperExplore, lowerExplor);
        trimEdgeforRelConcepts(cn);
        addPropertyEdge(cn);
        return cn;
    }
    
    public static ConceptNet buildConceptNet(String start, int maxL){
        maxLayer = maxL;
       
        return buildConceptNet(start);
    }
    
    private static void printDrawing(ConceptNet cn){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("out.txt"));
            bw.write("digraph G {");
            bw.newLine();
            HashMap<String, Edge> written = new HashMap<String, Edge>();
            for(ConceptNetNode cnn : cn.related){
                ArrayList<Edge> list = new ArrayList<Edge>(cnn.getInRelEdge());
                list.addAll(cnn.getOutRelEdge());
                for(Edge e : list){
                    if(written.get(e.id) == null){
                        written.put(e.id, e);
                        bw.write("  \"" + e.start + "\" -> \"" + e.end + "\";");
                        bw.newLine();
                    }
                }
                
            }
            for(ConceptNetNode cnn : cn.property){
                ArrayList<Edge> list = new ArrayList<Edge>(cnn.getInPropertyEdge());
                list.addAll(cnn.getOutPropertyEdge());
                for(Edge e : list){
                    if(written.get(e.id) == null){
                        written.put(e.id, e);
                        bw.write("  \"" + e.start + "\" -> \"" + e.end + "\" [label=\"" + e.rel + "\" color=green]" + ";");
                        bw.newLine();
                    }
                }
                bw.write(" \"" + cnn.name + "\" [color = green, fillcolor = green, style=filled];");
                bw.newLine();
            }
            bw.write(" \"" + cn.center.name + "\" [color = red, fillcolor = red, style=filled];");
            bw.newLine();
            bw.write("}");
            bw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * Find all the related concepts
     * @param c center
     * @param upper layers of ancestors to explore
     * @param lower layers of descendants to explore
     * @param maxL max number of layers to explorer
     * @param conceptNet the current concept net
     */
    public static void findRelatedConcepts(ConceptNetNode c, ConceptNet conceptNet, int upper, int lower, int maxL){
        if(c.layer > maxL){
            return;
        }
        c.expanded = true;
        
        
        if(upper > 0){
            ArrayList<Edge> parents = c.getOutRelEdge();
            for(Edge e : parents){
                if(e.end.indexOf(' ') < 0 && e.end.indexOf('_') < 0){
//                    if(e.end.equalsIgnoreCase("citrus")){
//                        System.out.println("");
//                        
//                    }
                    ConceptNetNode tp = conceptNet.getRelatedNode(e.end);
                    if(tp == null ){
                        if(debug){
                            System.out.println(c.name + " isA " + e.end);
                        }                            
                        Concept np = ConceptBuilder.createConcept(e.end);
                        ConceptNetNode ncnn = new ConceptNetNode(np.name);
                        ncnn.layer = c.layer + 1;
                        for(String rel : parentRel){
                            ncnn.addInRelEdge(np.getInEdges(rel));
                            ncnn.addOutRelEdge(np.getOutEdges(rel));
                        }
                        for(String rel : siblingRel){
                            ncnn.addInRelEdge(np.getInEdges(rel));
                            ncnn.addOutRelEdge(np.getOutEdges(rel));
                        }
                        conceptNet.related.add(ncnn);
                        if(edgeRelInArray(e, siblingRel)){
                            findRelatedConcepts(ncnn, conceptNet, upper, lower, maxL);
                        }
                        else
                            findRelatedConcepts(ncnn, conceptNet, upper-1, lower+1, maxL);
                    }
                    else if(tp.layer > c.layer+1){
                        if(debug){
                             System.out.println("Re-expand node: " + tp.name + ", " + c.name + " isA " + tp.name);
                        }
                        tp.layer = c.layer + 1;
                        
                        if(edgeRelInArray(e, siblingRel)){
                            findRelatedConcepts(tp, conceptNet, upper, lower, maxL);
                        }
                        else
                            findRelatedConcepts(tp, conceptNet, upper-1, lower+1, maxL);
                    }
                }
            }
            
        }
        
        if(lower > 0){
            ArrayList<Edge> children = c.getInRelEdge();
            for(Edge e : children){
                if(e.start.indexOf(' ') < 0 && e.start.indexOf('_') < 0){
                    ConceptNetNode tp = conceptNet.getRelatedNode(e.start);
                    if(tp == null ){
                        if(debug){
                            System.out.println(e.start+ " isA " + c.name );
                        }
                        Concept np = ConceptBuilder.createConcept(e.start);
                        ConceptNetNode ncnn = new ConceptNetNode(np.name);
                        ncnn.layer = c.layer + 1;
                        for(String rel : parentRel){
                            ncnn.addInRelEdge(np.getInEdges(rel));
                            ncnn.addOutRelEdge(np.getOutEdges(rel));
                        }
                        for(String rel : siblingRel){
                            ncnn.addInRelEdge(np.getInEdges(rel));
                            ncnn.addOutRelEdge(np.getOutEdges(rel));
                        }
                        conceptNet.related.add(ncnn);
                        if(edgeRelInArray(e, siblingRel)){
                            findRelatedConcepts(ncnn, conceptNet, upper, lower, maxL);
                        }
                        else
                            findRelatedConcepts(ncnn, conceptNet, upper+1, lower-1, maxL);
                    }
                    else if(tp.layer > c.layer+1){
                        if(debug){
                             System.out.println("Re-expand node: " + tp.name  + ", " + tp.name + " isA " + c.name);
                        }
                        tp.layer = c.layer + 1;
                        if(edgeRelInArray(e, siblingRel)){
                            findRelatedConcepts(tp, conceptNet, upper, lower, maxL);
                        }
                        else
                            findRelatedConcepts(tp, conceptNet, upper+1, lower-1, maxL);
                    }
                }
            }            
        }
        
    }
    
    public static ConceptNet findRelatedConcepts(String cs, int upper, int lower){
        
        Concept c = ConceptBuilder.createConcept(cs);
        ConceptNetNode cnn = new ConceptNetNode(c.name);
        ConceptNet cn = new ConceptNet(cnn);
        
        for(String rel : parentRel){
            cnn.addInRelEdge(c.getInEdges(rel));
            cnn.addOutRelEdge(c.getOutEdges(rel));
        }
        for(String rel : siblingRel){
            cnn.addInRelEdge(c.getInEdges(rel));
            cnn.addOutRelEdge(c.getOutEdges(rel));
        }
        
        cnn.layer = 0;
        findRelatedConcepts(cnn, cn, upper, lower, maxLayer);
        return cn;
    }
    
    /**
     * Trim unused rel edges for concept
     * @param cn 
     */
    public static void trimEdgeforRelConcepts(ConceptNet cn){
        for(ConceptNetNode cnn : cn.related){
            ArrayList<Edge> nin = new ArrayList<Edge>();
            ArrayList<Edge> in = cnn.getInRelEdge();
            for(Edge e : in){
                if(cn.getRelatedNode(e.start) != null){
                    nin.add(e);
                }
            }
            cnn.setInRelEdges(nin);
            
            ArrayList<Edge> nout = new ArrayList<Edge>();
            ArrayList<Edge> out = cnn.getOutRelEdge();
            for(Edge e : out){
//                if(e.start.equalsIgnoreCase("kameradschaft")){
//                    System.out.print("");
//                }
                if(cn.getRelatedNode(e.end) != null){
                    nout.add(e);
                }
            }
            cnn.setOutRelEdges(nout);
        }
    }
    
    /**
     * Add property edge to the conceptnet
     * @param cn 
     */
    public static void addPropertyEdge(ConceptNet cn){
        for(ConceptNetNode cnn : cn.related){
            Concept c = ConceptBuilder.getConcept(cnn.name);
//            if(c.name.compareTo("fruit") == 0){
//                System.out.print("");
//            }
            for(String prop : propertyOutRel){
                ArrayList<Edge> edges = c.getOutEdges(prop);
                cnn.addOutPropertyEdge(edges);
                for(Edge e : edges){
                    ConceptNetNode pp = cn.getPropertyNode(e.end);
                    if(pp == null){
                        Concept cc = ConceptBuilder.getConcept(e.end);
                        if(cc == null){
                            if(debug){
                                System.out.println("Create out property concept: " + e.end);
                            }
                            cc = ConceptBuilder.createConcept(e.end);
                        }
                        else{
                            if(debug){
                                System.out.println("Reuse property concept: " + e.end);
                            }
                        }
                        pp = new ConceptNetNode(cc.name);                        
                        cn.property.add(pp);
                    }   
                    pp.addInPropertyEdge(e);
                }
            }
            for(String prop : propertyInRel){
                ArrayList<Edge> edges = c.getInEdges(prop);
                cnn.addInPropertyEdge(edges);
                for(Edge e : edges){
                    ConceptNetNode pp = cn.getPropertyNode(e.start);
//                    if(e.start.compareToIgnoreCase("story") == 0){
//                        System.out.println("");
//                    }
                    if(pp == null){
                        Concept cc = ConceptBuilder.getConcept(e.start);
                        if(cc == null){
                            if(debug){
                                System.out.println("Create in property concept: " + e.start);
                            }
                            cc = ConceptBuilder.createConcept(e.start);
                        }
                        else{
                            if(debug){
                                System.out.println("Reuse property concept: " + e.start);
                            }
                        }
                        pp = new ConceptNetNode(cc.name);
                        cn.property.add(pp);
                    }  
                    pp.addOutPropertyEdge(e);
                }
            }
        }
    }
    
    
    private static boolean edgeRelInArray(Edge e, String[] array){
        for(String s : array){
            if(e.rel.equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
    }
    
    private static void printMissingProperty(String center, ArrayList<MissingEdge> el){
        for(MissingEdge e : el){
            System.out.println(e.toString());
        }
    }
    
    public static void main(String[] args){
        String center = "shirt";
        ConceptNet cn = buildConceptNet(center);
        double[][] adj = cn.getAdjacentMat();
        printDrawing(cn);
//        double[][] corr = SimRank.computeCorrelation(adj);
        double[][] corr = SpreadingActivation.computeCorrelation(adj, cn.getCenterIndex());
        ArrayList<ConceptNetNode> cnnl  = cn.getRankedCorrelatedConcept(corr);
        ArrayList<MissingEdge> missing = cn.getMissingProperty(new ArrayList<ConceptNetNode>(cnnl.subList(0, 3)), corr);
        Collections.sort(missing);
        for(ConceptNetNode cnn : cnnl){
            System.out.println(cnn.name);
        }
        printMissingProperty(center, missing);
        
    }
    
}
