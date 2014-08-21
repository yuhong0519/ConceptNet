/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conceptNetPropertySupplement;

/**
 *
 * @author Hong
 */
public class Results {
    private double precision = 0;
    private double recall = 0;
    private int numP = 0;
    private int numR = 0;
    
    public double getPrecision(){
        return precision;
    }
    public double getRecall(){
        return recall;
    }
    
    public void addPrecision(double p){
        precision = (precision * numP + p) / (numP + 1);
        numP++;
    }
    public void addRecall(double p){
        recall = (recall * numR + p) / (numR + 1);
        numR++;
    }
    public void addResults(Results r){
        if(numP + r.numP != 0)
            precision = (precision * numP + r.precision * r.numP) / (numP + r.numP);
        numP += r.numP;
        if(numR + r.numR != 0)
            recall = (recall * numR + r.recall * r.numR) / (numR + r.numR);
        numR += r.numR;
        
    }
    
    @Override
    public String toString(){
        return "Precision: " + precision + ", Recall: " + recall + "\n";
    }
}
