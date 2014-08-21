/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conceptnet;

import conceptnet.ds.ConceptNetNode;
import java.util.Comparator;

/**
 *
 * @author Hong
 */
public class ConceptNetNodeComp implements Comparator<ConceptNetNode>{

    @Override
    public int compare(ConceptNetNode o1, ConceptNetNode o2) {
        if(o1.corrScore > o2.corrScore){
            return -1;
        }
        else if(o1.corrScore < o2.corrScore){
            return 1;
        }
        else
            return 0;
    }


    
}
