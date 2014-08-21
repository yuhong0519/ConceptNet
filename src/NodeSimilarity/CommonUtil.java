package NodeSimilarity;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Vector;


public class CommonUtil {
	

        
	
	public static void printObject(Vector ve, String file){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for(int i = 0; i < ve.size(); i++){
				bw.write(""+ve.get(i));
				bw.write("  ");				
			}
			bw.newLine();
			bw.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
        
       
	
	public static void printObject(double[][] data, String file){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for(int i = 0; i < data.length; i++){
				for(int j = 0; j < data[0].length; j++){
					bw.write(""+data[i][j]);
					bw.write("  ");
				}
				bw.newLine();
			}
			bw.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
 
                       
        public static void printObject(int[][] data, String file){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for(int i = 0; i < data.length; i++){
				for(int j = 0; j < data[0].length; j++){
					bw.write(""+data[i][j]);
					bw.write("  ");
				}
				bw.newLine();
			}
			bw.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}

       
        
        public static void printObject(float[] data, String file){
		try{
			//BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			
                        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	
                        for(int i = 0; i < data.length; i++){
//				for(int j = 0; j < data[0].length; j++){
					bw.write(""+data[i]);
					bw.write("  ");
//				}
				bw.newLine();
			}
			bw.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
        
        public static void printObject(int[] data, String file){
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));

                for(int i = 0; i < data.length; i++){
                        bw.write(""+data[i]);
                        bw.write("  ");
                        bw.newLine();
                }
                bw.close();
            } catch(IOException e){
                e.printStackTrace();
            }
	}
        
        public static void printObject(double[] data, String file){
		try{
			//BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			
                        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	
                        for(int i = 0; i < data.length; i++){
//				for(int j = 0; j < data[0].length; j++){
					bw.write(""+data[i]);
					bw.write("  ");
//				}
				bw.newLine();
			}
			bw.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
                
        
        public static void printObject(double[][] data, String file, char sep){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for(int i = 0; i < data.length; i++){
				for(int j = 0; j < data[0].length; j++){
					bw.write(""+data[i][j]);
					bw.write(sep);
				}
				bw.newLine();
			}
			bw.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
        
                
        public static void printObject(ArrayList<float[]> data, String file){
		try{
                        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                        for(int i = 0; i < data.size(); i++){
                    		for(int j = 0; j < data.get(i).length; j++){
					bw.write(""+data.get(i)[j]);
                                        if(j < data.get(i).length-1){
                                            bw.write("\t");
                                        }
//					bw.write(sep);
				}
				bw.newLine();
			}
			bw.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
        
        public static void printStringList(ArrayList<String> data, String file){
		try{
                        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                        for(int i = 0; i < data.size(); i++){
                    		bw.write(data.get(i));                                
				bw.newLine();
			}
			bw.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
        
        
        public static ArrayList<float[]> readDataArray(String filename){
            ArrayList<float[]> dataArray = new ArrayList<float[]>();
            try{
                    BufferedReader br = new BufferedReader(new FileReader(filename));
                    String line;
                    while((line = br.readLine()) != null){
                        line = line.trim();
                        String sep = "\t";
                        if(line.indexOf(sep) == -1){
                                sep = "  ";
                        }

                        String[] vs = line.split(sep);
                        float[] data = new float[vs.length];
                        for(int i = 0; i < vs.length; i++){
                                data[i] = Float.parseFloat(vs[i]);
                        }
                        dataArray.add(data);
                    }
                    

            }
            catch(IOException e){
                    e.printStackTrace();
            }
            return dataArray;
            
	}
                
        
	public static void printObject(double data, String file){
		try{
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.write(""+data);
				bw.newLine();
				bw.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
		
                        
	public static double[] readVector(String filename){
		try{
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = br.readLine().trim();
			String sep = "\t";
			if(line.indexOf(sep) == -1){
				sep = "  ";
			}
			
			String[] vs = line.split(sep);
			double[] data = new double[vs.length];
			for(int i = 0; i < vs.length; i++){
				data[i] = Double.parseDouble(vs[i]);
				if(data[i] == 0){
					data[i] = Double.NaN;
				}
			}
			
			return data;
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
        
       public static ArrayList<String> readStringList(String filename){
            ArrayList<String> data = new ArrayList<String>();	
           try{
                        
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
                        while((line = br.readLine()) != null){
                            data.add(line);
                        }
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return data;
	}
                
	

	
//Read a matrix into two dimension double array. NaNflag is true if we need NaN for zero item.
	public static double[][] readData(String filename, boolean NaNflag){
		ArrayList<String> al = new ArrayList<String>();
		
		try{
			int x = 1, y = 0;
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = br.readLine().trim();
			String sep = "\t";
			if(line.indexOf(sep) == -1){
				sep = "  ";
			}
			
			String[] vs = line.split(sep);
			
			al.add(line);
			while((line = (br.readLine())) != null){
				line = line.trim();
				al.add(line);
				x++;
			}
			double[][] data = new double[x][];
			
			
			for(int i = 0; i < x; i++){
				line = al.get(i);
				vs = line.split(sep);
				y = vs.length;
				data[i] = new double[y];
				for(int j = 0; j < y; j++){
					data[i][j] = Double.parseDouble(vs[j]);
					if(data[i][j] == 0 && NaNflag){
						data[i][j] = Double.NaN;
					}
				}
				
			}
			return data;
			
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
		
	}
        
        //Read a matrix into two dimension double array. NaNflag is true if we need NaN for zero item.
	public static int[][] readIntData(String filename){
		ArrayList<String> al = new ArrayList<String>();
		
		try{
			int x = 1, y = 0;
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = br.readLine().trim();
			String sep = "\t";
			if(line.indexOf(sep) == -1){
				sep = "  ";
			}
                        if(line.indexOf(sep) == -1){
				sep = " ";
			}
			
			String[] vs = line.split(sep);
			
			al.add(line);
			while((line = (br.readLine())) != null){
				line = line.trim();
				al.add(line);
				x++;
			}
			int[][] data = new int[x][];
			
			
			for(int i = 0; i < x; i++){
				line = al.get(i);
				vs = line.split(sep);
				y = vs.length;
				data[i] = new int[y];
				for(int j = 0; j < y; j++){
					data[i][j] = Integer.parseInt(vs[j]);
					
				}
				
			}
			return data;
			
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
		
	}
	

        
/**Get num random number from 0 (inclusive) to max (exclusive)
 * 
 */
        public static ArrayList<Integer> getRandom(int max, int num){
            ArrayList<Integer> ret = new ArrayList<Integer>();
            Random r = new Random();
            for(int i = 0; i < num; i++){
                int k = r.nextInt(max);
                while(Collections.binarySearch(ret, k) >= 0){
                    k = r.nextInt(max);
                }
                ret.add(k);
                Collections.sort(ret);
            }
            return ret;
        }
        
       
        
        /**
         * Scale data to the interval [min, max]
         * @param data the data to scale
         * @param min the min value
         * @param max  the max value
         */
        public static void scaleData(double[][] data, double min, double max){
            double dmin = 10000000;
            double dmax = -10000000;
            if(data.length == 0 || max < min){
                return;
            }
            
            for(int i = 0; i < data.length; i++){
                for(int j = 0; j < data[0].length; j++){
                    if(data[i][j] > dmax){
                        dmax = data[i][j];
                    }
                    if(data[i][j] < dmin){
                        dmin = data[i][j];
                    }
                }
            }
            
            for(int i = 0; i < data.length; i++){
                for(int j = 0; j < data[0].length; j++){
                    data[i][j] = (data[i][j] - dmin) / (dmax-dmin) * (max - min) + min;
                }
            }
            
        }
	
}
