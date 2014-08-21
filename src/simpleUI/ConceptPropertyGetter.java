/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simpleUI;

import NodeSimilarity.SimRank;
import conceptNetPropertySupplement.PSMainControl;
import conceptNetPropertySupplement.PropertyCandidate;
import conceptnet.ConceptBuilder;
import conceptnet.ConceptNetBuilder;
import conceptnet.ds.Concept;
import conceptnet.ds.ConceptNet;
import conceptnet.ds.ConceptNetNode;
import conceptnet.ds.Edge;
import conceptnet.ds.MissingEdge;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author Hong
 */
public class ConceptPropertyGetter {
    private static ArrayList<String> allConcepts = null;
    private static Random rd = new Random();
    private static String rel = "/r/PartOf";
    private static String direction = "in";
    
    private static final int numMissingEdge = 2, numConcepts = 2, numPropertyPerConcept = 2;
    
    private static boolean alreadySelected(ArrayList<ConceptWProperty> list, String name){
        for(ConceptWProperty cwp : list){
            if(cwp.concept.equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }
    
    private static void sortEdge(ArrayList<Edge> edgs, final boolean ascending, final String di){
        Collections.sort(edgs, new Comparator<Edge>(){
            @Override
            public int compare(Edge o1, Edge o2){
                int num1 = 0, num2 = 0;
                if(di.compareTo("in") == 0){
                    Concept c1 = ConceptBuilder.createConcept(o1.start);
                    Concept c2 = ConceptBuilder.createConcept(o2.start);
                    num1 = c1.getInEdges().size() + c1.getOutEdges().size();
                    num2 = c2.getInEdges().size() + c2.getOutEdges().size();
                }
                else{
                    Concept c1 = ConceptBuilder.createConcept(o1.end);
                    Concept c2 = ConceptBuilder.createConcept(o2.end);
                    num1 = c1.getInEdges().size() + c1.getOutEdges().size();
                    num2 = c2.getInEdges().size() + c2.getOutEdges().size();                    
                }
                return ascending ? num1-num2 : num2-num1;
            }
        });
    }
    
    public static ArrayList<ConceptWProperty> get(){
        if(allConcepts == null){
            allConcepts = new ArrayList<String>();
            PSMainControl.loadAllConcepts(allConcepts);
        }
        ArrayList<ConceptWProperty> ret = new ArrayList();
        ArrayList<ConceptNet> cnl = new ArrayList();
        
        for(int i = 0; i < numConcepts; i++){
            ConceptWProperty cwp = new ConceptWProperty();
            Concept c = null;
            ArrayList<Edge> edges = null;
            flag:
            while(true){
                String name = allConcepts.get(rd.nextInt(allConcepts.size()));  
//                Check if the concept is similar with preivous concept
                for(ConceptNet cn : cnl){
                    if(cn.getRelatedNode(name) != null || cn.getPropertyNode(name) != null){
                        continue flag;
                    }
                }
                c = ConceptBuilder.createConcept(name);
                
                if(direction.equals("in")){
                    edges = new ArrayList(c.getInEdges(rel));
                }
                else{
                    edges = new ArrayList(c.getOutEdges(rel));
                }
//                find a concept that has the required number of edges and is not selected yet
                if(edges.size() >= numPropertyPerConcept && !alreadySelected(ret, name)){
                    break;
                }
            }
            ConceptNet cn = ConceptNetBuilder.buildConceptNet(c.name, 3);
            cnl.add(cn);
            cwp.concept = c.name;
//            ArrayList<Edge> out = c.getOutEdges(rel);
//            Collections.shuffle(edges);
            sortEdge(edges, true, direction);
            for(int k = 0; k < numPropertyPerConcept; k++){
                if(direction.equals("in")){
                    cwp.properties.add(edges.get(k).start);
                }
                else{
                    cwp.properties.add(edges.get(k).end);
                }
               
            }
            ret.add(cwp);
        }
        
        return ret;
    }
    
    
    public static ArrayList<String> getCandidateProperty(String concept){
        ConceptNet cn = ConceptNetBuilder.buildConceptNet(concept);
        if(cn.related.size() + cn.property.size() > 1000){
            cn = ConceptNetBuilder.buildConceptNet(concept, 1);
        }
        double[][] adj = cn.getAdjacentMat();
        double[][] corr = SimRank.computeCorrelation(adj);
        ArrayList<ConceptNetNode> cnnl  = cn.getRankedCorrelatedConcept(corr);
        ArrayList<MissingEdge> missing = cn.getMissingProperty(new ArrayList(cnnl), corr);
        Collections.sort(missing);
        ArrayList<String> ret = new ArrayList();
        for(MissingEdge me : missing){
            if(me.rel.compareTo(rel) == 0 && ((me.type == MissingEdge.Type.out && direction.equalsIgnoreCase("out")) || (me.type == MissingEdge.Type.in && direction.equalsIgnoreCase("in")))){
                if(direction.equalsIgnoreCase("in")){
                    if(!ret.contains(me.start))
                        ret.add(me.start);
                }
                else{
                    if(!ret.contains(me.end))
                        ret.add(me.end);
                }
                System.out.println(me);
            }
            if(ret.size() >= numMissingEdge){
                break;
            }
        }
        
        return ret;
    }
}
