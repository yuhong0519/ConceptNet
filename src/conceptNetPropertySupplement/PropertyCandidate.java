/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conceptNetPropertySupplement;

import NodeSimilarity.SimRank;
import NodeSimilarity.SpreadingActivation;
import conceptnet.ds.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Hong
 */
public class PropertyCandidate {
    public enum SimilarityAlg {SimRank, SA};
    
    /**
     * Predict the missing edges for the central node of cn
     * @param cn
     * @param alg
     * @param numNeibors the number of neibors to retrieve missing edges
     * @return 
     */
    public static ArrayList<MissingEdge> predictMissingEdge(ConceptNet cn, double[][] corr, int numNeibors){
//        double[][] adj = cn.getAdjacentMat();
//        double[][] corr = null;
//       if(alg == SimilarityAlg.SimRank){
//           corr = SimRank.computeCorrelation(adj);
//       }
//       else if(alg == SimilarityAlg.SA){
//           corr = SpreadingActivation.computeCorrelation(adj, cn.getCenterIndex());
//       }
//       else{
//           return null;
//       }
        ArrayList<ConceptNetNode> cnnl  = cn.getRankedCorrelatedConcept(corr);
        ArrayList<MissingEdge> missing = cn.getMissingProperty(new ArrayList<ConceptNetNode>(cnnl.subList(0, numNeibors)), corr);
        Collections.sort(missing);
        return missing;
    }
    
}
