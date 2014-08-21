/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conceptNetPropertySupplement;

import NodeSimilarity.CommonUtil;
import NodeSimilarity.SimRank;
import NodeSimilarity.SpreadingActivation;
import conceptnet.ConceptNetBuilder;
import conceptnet.ds.ConceptNet;
import conceptnet.ds.ConceptNetNode;
import conceptnet.ds.Edge;
import conceptnet.ds.MissingEdge;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

/**
 *
 * @author Hong
 */
public class PSMainControl {
    
    
    public static void loadAllConcepts(ArrayList<String> todo){
//        File folder = new File("concepts/");
//        for (File fileEntry : folder.listFiles()) {
//            todo.add(fileEntry.getName());
//            if(fileEntry.getName().indexOf("_") < 0)
//                System.out.println(fileEntry.getName());
//        }
        try{
            Scanner s = new Scanner(new FileReader("testConcepts.txt"));
            while(s.hasNext()){
                String str = s.next();                
//                System.out.println(str);
                todo.add(str);
                
            }
            s.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    
    /**
     * check if the center of cn has more than num properties that can be removed
     *  i.e. the properties are also connected by other nodes
     * @param cn 
     */
    private static ArrayList<Edge> getValidFeatures(ConceptNet cn){
        
        ArrayList<Edge> ret = new ArrayList<Edge>();
        ConceptNetNode center = cn.center;
        ArrayList<Edge> features = center.getInPropertyEdge();
        for(Edge e : features){
            ConceptNetNode cnn = cn.getStartNode(e);
            if(cnn == null){
                System.err.println("Error: Cannot find node: " + e.start + " in Concept Net: " + center.name);
                return null;
            }
            if(cnn.getInPropertyEdge().size() > 1 || cnn.getOutPropertyEdge().size() > 1 || cnn.getInRelEdge().size() > 1 || cnn.getOutRelEdge().size() > 1){
                ret.add(e);
            }
        }
        features = center.getOutPropertyEdge();
        for(Edge e : features){
            ConceptNetNode cnn = cn.getEndNode(e);
            if(cnn == null){
                System.err.println("Error: Cannot find node: " + e.end + " in Concept Net: " + center.name);
                return null;
            }
            if(cnn.getInPropertyEdge().size() > 1 || cnn.getOutPropertyEdge().size() > 1 || cnn.getInRelEdge().size() > 1 || cnn.getOutRelEdge().size() > 1){
                ret.add(e);
            }
        }
        return ret;
    }
    
    private static boolean checkMissingEdgeInSet(ArrayList<Edge> edges, MissingEdge me){
        for(Edge e : edges){
            if(me.type==MissingEdge.Type.in){
                if(e.start.compareTo(me.start)==0 && e.rel.compareTo(me.rel)==0){
                    return true;
                }
            }
            else{
                if(e.end.compareTo(me.end)==0 && e.rel.compareTo(me.rel)==0){
                    return true;
                }
            }
        }
        
        return false;
        
    }
    
    private static Results computeResults(ConceptNetNode oldcnn, ConceptNetNode newcnn, ArrayList<MissingEdge> missing){
        Results r = new Results();
        if(missing.size() == 0){
            return r;
        }
        ArrayList<Edge> in = oldcnn.getInPropertyEdge();
        ArrayList<Edge> out = oldcnn.getOutPropertyEdge();
        double correctNum = 0;
        double missedNum = oldcnn.getInPropertyEdge().size() + oldcnn.getOutPropertyEdge().size() - newcnn.getInPropertyEdge().size() - newcnn.getOutPropertyEdge().size();
        HashSet<String> checkedString = new HashSet<String>();
        
//        System.out.println("old:");
//        for(Edge e : out){
//            System.out.println(e.end);
//        }
//        System.out.println("new:");
//        for(Edge e : newcnn.getOutPropertyEdge()){
//            System.out.println(e.end);
//        }
        
        for(MissingEdge me : missing){
            ArrayList<Edge> check;
//            if(me.end.equals("polka_band")){
//                System.out.println("");
//            }
            if(me.type == MissingEdge.Type.in){
                check = in;
                if(checkedString.contains(me.start+me.rel+"in")){
                    continue;
                }
                checkedString.add(me.start+me.rel+"in");
            }
            else{
                check = out;
                if(checkedString.contains(me.end+me.rel+"out")){
                    continue;
                }
                checkedString.add(me.end+me.rel+"out");
            }
            if(checkMissingEdgeInSet(check, me)){
                correctNum += 1;
//                System.out.println(me.end);
            }
        }
//        if(correctNum > missedNum){
//            correctNum = missedNum;
//        }
                
        r.addPrecision(correctNum / missing.size());

        r.addRecall(correctNum / missedNum);
        return r;
        
    }
    
    public static void startTest(){
        ArrayList<String> todo = new ArrayList<String>();
        loadAllConcepts(todo);
//        int numNeibors = 5;        
        int numLeastProperty = 4;
        
        PropertyCandidate.SimilarityAlg alg = PropertyCandidate.SimilarityAlg.SA;
        
        int maxNeibor = 10;
        Results[] all = new Results[maxNeibor];
        for(int i = 0; i < maxNeibor; i++){
            all[i] = new Results();
        }
        
        for(String name : todo){
//            name = "cat";
//            System.out.println("Test: " + name);
            ConceptNet cn = ConceptNetBuilder.buildConceptNet(name);
            if(cn.related.size() + cn.property.size() > 1000){
                cn = ConceptNetBuilder.buildConceptNet(name, 1);
            }
            ArrayList<Edge> f = getValidFeatures(cn);
            System.out.println(name + ": " + f.size());
            if(f != null && f.size() >= numLeastProperty){
                int numPropertyRemoved =  f.size() / 2;
                System.out.println("Valid: " + cn.center.name);
                ConceptNetNode newCenter = cn.center;
                ConceptNetNode oldCenter = new ConceptNetNode(newCenter);
                
//                ArrayList<Edge> prop = new ArrayList(newCenter.getInPropertyEdge());
//                prop.addAll(newCenter.getOutPropertyEdge());
                int numR = 0;
                ArrayList<Edge> prop = (newCenter.getInPropertyEdge());                
                if(prop.size() > 0){
                    ArrayList<Edge> newprop = new ArrayList();
                    for(int i = 0; i < prop.size(); i++){
                        Edge e = prop.get(i);
                        if(f.contains(e) && numR < numPropertyRemoved){
                            numR++;
                        }
                        else{
                            newprop.add(e);
                        }
                    }
                    newCenter.setInPropertyEdges(newprop);
                }
                   
                if(numR < numPropertyRemoved){
                    prop = (newCenter.getOutPropertyEdge());
                    ArrayList<Edge> newprop = new ArrayList();
                    for(int i = 0; i < prop.size(); i++){
                        Edge e = prop.get(i);
                        if(f.contains(e) && numR < numPropertyRemoved){
                            numR++;
                        }
                        else{
                            newprop.add(e);
                        }
                    }
                    newCenter.setOutPropertyEdges(newprop);
                }
                if(numR < numPropertyRemoved){
                    System.err.println("Error simulate new center!");
                }
//                cn.setCenter(newCenter);
                double[][] corr = null;
                double[][] adj = cn.getAdjacentMat();
                if(alg == PropertyCandidate.SimilarityAlg.SimRank){
                    corr = SimRank.computeCorrelation(adj);
                }
                else if(alg == PropertyCandidate.SimilarityAlg.SA){
                    corr = SpreadingActivation.computeCorrelation(adj, cn.getCenterIndex());
                }

                ArrayList<ConceptNetNode> cnnl  = cn.getRankedCorrelatedConcept(corr);
                
                for(int i = 1; i <= maxNeibor; i++){
                    if(i > cnnl.size()){
                        break;
                    }
                    ArrayList<MissingEdge> missing = cn.getMissingProperty(new ArrayList(cnnl.subList(0, i)), corr);
//                Collections.sort(missing);
                    Results r = computeResults(oldCenter, newCenter, missing);
                    System.out.println(r);
                    all[i-1].addResults(r);
                }
                
//                break;
            }
            
        }
        double[] precision = new double[maxNeibor];
        double[] recall = new double[maxNeibor];
        for(int i = 0; i < maxNeibor; i++){
            System.out.println(all[i]);
            precision[i] = all[i].getPrecision();
            recall[i] = all[i].getRecall();
        }
        String fileName = "" + alg;
        if(alg == PropertyCandidate.SimilarityAlg.SA){
            fileName = fileName + "_" + SpreadingActivation.alpha + "_" + SpreadingActivation.kmax + ".txt";
        }
        else if(alg == PropertyCandidate.SimilarityAlg.SimRank){
            fileName = fileName + "_" + SimRank.cf + ".txt";
        }
        CommonUtil.printObject(precision, "precision_"+fileName);
        CommonUtil.printObject(recall, "recall.txt"+fileName);
        
        
    }
    
    public static void main(String[] args){
        startTest();
    }
    
    
}
