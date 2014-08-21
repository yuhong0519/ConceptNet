/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package NodeSimilarity;
import static NodeSimilarity.MatrixUtil.*;

/**
 *
 * @author Hong
 */
public class SpreadingActivation {
    
    public static int kmax = 5;
    public static double alpha = 0.9;
    
    private static double[][] sumweight = null;
    
    
    private static void computeSumweight(double[][] weight, int k, double al){
        
        sumweight = new double[weight.length][weight.length];
        double[][] alw = new double[weight.length][weight.length];
        double[][] tpmult = new double[weight.length][weight.length];
        
        for(int i = 0; i < sumweight.length; i++){
            for(int j = 0; j < sumweight.length; j++){
                if(i == j){
                    sumweight[i][j] = 1;
                }
                else{
                    sumweight[i][j] = 0;
                }
            }
        }
        MatrixUtil.copyArray(weight, alw);
        MatrixUtil.scaleMatrix(alw, al);
        MatrixUtil.add(sumweight, alw, 1);
        
        
        MatrixUtil.copyArray(alw, tpmult);
        for(int i = 2; i < k; i++){
            tpmult = MatrixUtil.matrixMult(tpmult, alw);
            MatrixUtil.add(sumweight, tpmult, 1);
        }
    }
    
    private static double[] spreadingVector(double[] init){
//        double[] ret = new double[init.length];
//        double[] ak = new double[init.length];
//        double scale = al;
//        MatrixUtil.copyArray(init, ret);
//        MatrixUtil.normalize(ret);
//        MatrixUtil.copyArray(ret, ak);
        
//        for(int i = 1; i < k; i++){
//            ak = MatrixUtil.matrixMult(ak, weight);
//            MatrixUtil.normalize(ak);
//            MatrixUtil.add(ret, ak, scale);
//            scale *= al;
//        }
//        if(sumweight == null){
//            computeSumweight(weight, k, al);
//        }
        double[] ret = MatrixUtil.matrixMult(init, sumweight);
        MatrixUtil.normalize(ret);
        return ret;
    }
    
    public static double[][] computeCorrelation(double[][] edges, int center){
        double[] degree = new double[edges.length];
        double[][] normEdges = new double[edges.length][edges.length];
//        double[][] ncorr = new double[edges.length][edges.length];
        double[][] corr = new double[edges.length][edges.length];
//        int maxIter = 100;
//        double diff = 10e-10;
        
        double max = -1;
        for(int i = 0; i < degree.length; i++){
            degree[i] = 0;
            for(int j = 0; j < degree.length; j++){
//                ncorr[i][j] = 0;
                if(i == j){
                    corr[i][j] = 1;
                }
                else
                    corr[i][j] = 0;
                
                if(edges[i][j] > 0 && i != j){
//                    degree[i] += 1;
                    normEdges[i][j] = edges[i][j]; 
                    if(normEdges[i][j] > max){
                        max = normEdges[i][j];
                    }
                }
            }
        }        
        for(int i = 0; i < degree.length; i++){
            for(int j = 0; j < degree.length; j++){
                normEdges[i][j] /= max;
            }
        }
        for(int i = 0; i < degree.length; i++){
            for(int j = 0; j < degree.length; j++){
                degree[i] += normEdges[i][j];
            }
        }
        
        
        System.out.print("Compute SA cummulative matrix. Total: " + edges.length);
        
        double[] cvector = new double[edges.length];
        double[] tpvector = new double[edges.length];
        for(int i = 0; i < cvector.length; i++){
            if(i == center){
                cvector[i] = 1;
            }
            else{
                cvector[i] = 0;
            }
        }
        computeSumweight(normEdges, kmax, alpha);
        double[] csa = spreadingVector(cvector);
        System.out.print("Start spreading activation iteration. Total: " + edges.length);
        for(int col = 0; col < edges.length; col++){
             if(col % 100 == 0){
                System.out.print("\n");
            }
            if(col % 10 == 0) 
                System.out.print(" " + col);
            if(col == center){
                corr[center][col] = 1;
                continue;
            }
            for(int i = 0; i < tpvector.length; i++){
                if(i == col){
                    tpvector[i] = 1;
                }
                else{
                    tpvector[i] = 0;
                }
            }
            double[] tpsa = spreadingVector(tpvector);
            corr[center][col] = MatrixUtil.cosineDist(tpsa, csa);
            corr[col][center] = corr[center][col];
        }

        System.out.println("\nFinish spreading activation iteration!");
        return corr;
    }
}
