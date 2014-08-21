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
public class SimRank {
//    decay coefficient
    public static double cf = 0.6;
    public static double[][] computeCorrelation(double[][] edges){
        double[] degree = new double[edges.length];
        double[][] normEdges = new double[edges.length][edges.length];
        double[][] ncorr = new double[edges.length][edges.length];
        double[][] corr = new double[edges.length][edges.length];
        int maxIter = 100;
        double diff = 10e-3;
        
        double max = -1;
        for(int i = 0; i < degree.length; i++){
            degree[i] = 0;
            for(int j = 0; j < degree.length; j++){
                ncorr[i][j] = 0;
                if(i == j){
                    corr[i][j] = 1;
                }
                else
                    corr[i][j] = 0;
                
                if(edges[i][j] > 0 && i != j){
                    degree[i] += 1;
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
        System.out.print("Start SimRank iteration: ");
        for(int iter = 0; iter < maxIter; iter++){
            if(iter % 20 == 0){
                System.out.print("\n");
            }
            System.out.print(" " + iter);

            for(int row = 0; row < edges.length; row++){
                for(int col = row; col < edges.length; col++){
                    if(col == row){
                        ncorr[row][col] = 1;
                        continue;
                    }
                    
                    for(int i = 0; i < edges.length; i++){
                        if(normEdges[row][i] == 0){
                            continue;
                        }
                        for(int j = 0; j < edges.length; j++){
                            if(normEdges[col][j] == 0){
                                continue;
                            }
                            ncorr[row][col] += corr[i][j] * normEdges[row][i] * normEdges[col][j];
                        }
                    }
                    if(degree[col] == 0 || degree[row] == 0){
                        ncorr[row][col] = 0;
                    }
                    else
                        ncorr[row][col] *= cf/degree[row]/degree[col];
                    ncorr[col][row] = ncorr[row][col];
                }
            }
            if(matDiff(corr, ncorr) < diff){
                System.out.println("\nConverge!");
                break;
            }
            
            for (int i = 0; i < ncorr.length; i++) {
                System.arraycopy(ncorr[i], 0, corr[i], 0, corr[0].length);
            }
            
            
        }
        System.out.println("\nFinish SimRank iteration!");
        return corr;
    }
    

}
