/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simpleUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Hong
 */
public class RecaptchaControl {
    private ArrayList<ConceptWProperty> cwplist = null;
    private ConceptWProperty candidate;
    private Random r = new Random();
    public void getConceptProperty(ArrayList<String> concepts, ArrayList<String> properties){
        cwplist = ConceptPropertyGetter.get();
        for(ConceptWProperty cwp : cwplist){
            concepts.add(cwp.concept);
            properties.addAll(cwp.properties);
            System.out.println(cwp.concept +" has parts: " + cwp.properties);
        }
        
        String cn = concepts.get(r.nextInt(concepts.size()));
        ArrayList<String> cnm = ConceptPropertyGetter.getCandidateProperty(cn);
        candidate = new ConceptWProperty();
        candidate.concept = cn;
        candidate.properties.addAll(cnm);
        properties.addAll(cnm);
        System.out.println(candidate.concept +" has possible parts: " + cnm);
//        Collections.shuffle(concepts);        
        Collections.shuffle(properties);
    } 
    
    public boolean checkValid(ArrayList<String> p1, ArrayList<String> p2){
        if(cwplist == null || cwplist.size() < 2){
            return false;
        }
        ArrayList<String> c1 = cwplist.get(0).properties;
        for(String tp : c1){
            if(!p1.contains(tp)){
                return false;
            }
        }
        ArrayList<String> c2 = cwplist.get(1).properties;
        for(String tp : c2){
            if(!p2.contains(tp)){
                return false;
            }
        }
        return true;
    }
    
}
