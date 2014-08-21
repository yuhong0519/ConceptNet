/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package NodeSimilarity;

/**
 *
 * @author Hong
 */
public class MatrixUtil {
    /**
     * Compute data1' * data2
     * @param data1
     * @param data2
     * @return 
     */
    public static double innerProduct(double[] data1, double[] data2){
        double ret = 0;
        if(data1.length != data2.length){
            System.err.println("Error using inner product!!!");
            return ret;
        }
        for(int i = 0; i < data1.length; i++){
            ret += data1[i]*data2[i];
        }
        return ret;
    }
    
    public static double cosineDist(double[] data1, double[] data2){
        double ret = 0;
        if(data1.length != data2.length){
            System.err.println("Error using inner product!!!");
            return -1;
        }
        ret = innerProduct(data1, data2) / length(data1) / length(data2);
        return ret;
    } 
    
    public static double matDiff(double[][] m1, double[][] m2){
        double diff = 0;
        if(m1.length != m2.length){
            System.err.println("Error calling matrix diff: dimension mismatch!");
            return -1;
        }
        for(int i = 0; i < m1.length; i++){
            if(m1[i].length != m2[i].length){
                System.err.println("Error calling matrix diff: dimension mismatch!");
                return -1;
            }
            for(int j = 0; j < m1[i].length; j++){
                diff += Math.pow(m1[i][j] - m2[i][j], 2);                
            }
        }        
        return diff;
    }
    
    /**
     * Return a * b
     * @param a
     * @param b
     * @return 
     */
    public static double[][] matrixMult(double[][] a, double[][] b){
        if(a == null || b == null){
            return null;
        }
        if(a[0].length != b.length){
            System.err.println("Matrix dimension mismatch!");
            return null;
        }
        double[][] c = new double[a.length][b[0].length];
        for(int i = 0; i < c.length; i++){
            for(int j = 0; j < c[0].length; j++){
                c[i][j] = 0;
                for(int k = 0; k < a[0].length; k++){
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        
        return c;
    }
    
    /**
     * Return a' * b
     * @param a
     * @param b
     * @return 
     */
    public static double[] matrixMult(double[] a, double[][] b){
        if(a == null || b == null){
            return null;
        }
        if(a.length != b.length){
            System.err.println("Matrix dimension mismatch!");
            return null;
        }
        double[] c = new double[b[0].length];
        for(int i = 0; i < c.length; i++){
            c[i] = 0;
            for(int j = 0; j < b.length; j++){
                c[i] += a[j] * b[j][i];
            }            
        }        
        return c;
    }
    
    public static void copyArray(double[][] source, double[][] dest){
        for (int i = 0; i < source.length; i++) {
            System.arraycopy(source[i], 0, dest[i], 0, dest[0].length);
        }
    }
    
    public static void copyArray(double[] source, double[] dest){
        
        System.arraycopy(source, 0, dest, 0, dest.length);

    }
    
    /**
     * a = a + b
     * @param a
     * @param b 
     */
    public static void add(double[] a, double[] b){
        if(a == null || b == null){
            System.err.println("Cannot add null vector!");
            return;
        }
        if(a.length != b.length){
            System.err.println("Matrix dimension mismatch!");
            return;
        }    
        for (int i = 0; i < a.length; i++) {
            a[i] += b[i];
        }
    }
    
        /**
     * a = a + b * scale
     * @param a
     * @param b 
     */
    public static void add(double[] a, double[] b, double scale){
        if(a == null || b == null){
            System.err.println("Cannot add null vector!");
            return;
        }
        if(a.length != b.length){
            System.err.println("Matrix dimension mismatch!");
            return;
        }    
        for (int i = 0; i < a.length; i++) {
            a[i] += b[i] * scale;
        }
    }
    
            /**
     * a = a + b * scale
     * @param a
     * @param b 
     */
    public static void add(double[][] a, double[][] b, double scale){
        if(a == null || b == null){
            System.err.println("Cannot add null vector!");
            return;
        }
        if(a.length != b.length || a[0].length != b[0].length){
            System.err.println("Matrix dimension mismatch!");
            return;
        }    
        for (int i = 0; i < a.length; i++) {
            for(int j = 0; j < a[i].length; j++){
                a[i][j] += b[i][j] * scale;
            }
            
        }
    }
    
        /**
     * compute data * value
     * @param data
     * @param value 
     */
    public static void scaleVector(double[] data, double value){
        for(int i = 0; i < data.length; i++){
            data[i] *= value;
        }        
    }
    
    public static void scaleMatrix(double[][] data, double value){
        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < data[i].length; j++){
                data[i][j] *= value;
            }
            
        }        
    }
    
    /**
     * Compute the length of data
     * @param data
     * @return (data' * data)^0.5
     */
    public static double length(double[] data){
        double len = 0;
        for(double d : data){
            len += d*d;
        }
        len = Math.sqrt(len);
        return len;
    }
    
    /**
     * Normalize data, after normalization, length(data)=1
     * @param data 
     */    
    public static void normalize(double[] data){
        double len = length(data);
        if(len == 0){
            System.err.println("Error trying to scale vector of length 0.");
            return;
        }
        scaleVector(data, 1/len);
    }
}
