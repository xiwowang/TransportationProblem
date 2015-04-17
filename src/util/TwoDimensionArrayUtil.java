/**
 * @usage: A Util to Deal with Copy/Sort/Max/Min actions for two dimension array
 * @author Mizzle Qiu
 */
package util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Set;

public class TwoDimensionArrayUtil {
	
	public static int DESC = -1;
	public static int ASC = 1;
	
	public static double[][] copyOf(double[][] array){
		int r = array.length;
		int c = array[0].length;
		double[][] copy = new double[r][c];
		for(int i=0; i<r; i++){
			copy[i] = Arrays.copyOf(array[i], c);
		}
		return copy;
	}
	
	public static int max(double[] cur){
		int n = cur.length;
		int maxIndex = -1;
		double max = -Double.MAX_VALUE;
		for(int i=0; i<n; i++){
			if(cur[i] > max){
				max = cur[i];
				maxIndex = i;
			}
		}
		return maxIndex;
	}
	
	public static int rowMax(double[][] array, int row){
		double[] cur = array[row];
		return max(cur);
	}
	
	public static int rowMin(double[][] array, int row){
		double[] cur = array[row];
		int n = cur.length;
		
		int minIndex = -1;
		double min = Double.MAX_VALUE;
		for(int i=0; i<n; i++){
			if(cur[i] < min){
				min = cur[i];
				minIndex = i;
			}
		}
		return minIndex;
	}
	
	public static int rowMin(double[][] array, int row, Set<Integer> rowSet){
		double[] cur = array[row];
		int n = cur.length;
		
		int minIndex = -1;
		double min = Double.MAX_VALUE;
		for(int i=0; i<n; i++){
			if(rowSet.contains(i) && cur[i] < min){
				min = cur[i];
				minIndex = i;
			}
		}
		return minIndex;
	}
	
	public static int colMax(double[][] array, int col){
		int m = array.length;
		
		int maxIndex = -1;
		double max = -Double.MAX_VALUE;
		for(int i=0; i<m; i++){
			if(array[i][col] > max){
				max = array[i][col];
				maxIndex = i;
			}
		}
		return maxIndex;
	}
	
	public static int colMin(double[][] array, int col){
		int m = array.length;
		
		int minIndex = -1;
		double min = Double.MAX_VALUE;
		for(int i=0; i<m; i++){
			if(array[i][col] < min){
				min = array[i][col];
				minIndex = i;
			}
		}
		return minIndex;
	}
	
	public static int colMin(double[][] array, int col, Set<Integer> set){
		int m = array.length;
		
		int minIndex = -1;
		double min = Double.MAX_VALUE;
		for(int i=0; i<m; i++){
			if(set.contains(i) && array[i][col] < min){
				min = array[i][col];
				minIndex = i;
			}
		}
		return minIndex;
	}
	
	public static Integer[] sortIndex(final double[] cur, final int sortFlag){
		int n = cur.length;
		
		Integer[] indexes = new Integer[n];
		for(int i=0; i<n; i++){
			indexes[i] = i;
		}
		
		Arrays.sort(indexes, new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				Double double1 = cur[o1];
				Double double2 = cur[o2];
				return sortFlag * (double1.compareTo(double2));
			}
			
		});
		return indexes;
	}
	
	public static Integer[] sortRowIndex(double[][] array, int row, final int sortFlag){
		double[] cur = array[row];
		return sortIndex(cur, sortFlag);
	}
	
	public static Integer[] sortColIndex(final double[][] array, final int col, final int sortFlag){
		int m = array.length;
		
		Integer[] indexes = new Integer[m];
		for(int i=0; i<m; i++){
			indexes[i] = i;
		}
		
		Arrays.sort(indexes, new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				Double double1 = array[o1][col];
				Double double2 = array[o2][col];
				return sortFlag * (double1.compareTo(double2));
			}
			
		});
		return indexes;
	}
	
	public static Integer[] maxN(double[] cur, int k){
		int n = cur.length;
		
		Integer[] indexes = sortIndex(Arrays.copyOfRange(cur, 0, k), DESC);
		LinkedList<Integer> ll = new LinkedList<Integer>();
		ll.addAll(Arrays.asList(indexes));
		
		for(int i=k; i<n; i++){
			int start = k;
			while(start > 0 && cur[ll.get(start-1)] < cur[i]){
				start --;
			}
			ll.add(start, i);
		}
		ll.subList(0, k).toArray(indexes);
		return indexes;
	}
	
	public static Integer[] minN(double[] cur, int k){
		int n = cur.length;
		
		Integer[] indexes = sortIndex(Arrays.copyOfRange(cur, 0, k), ASC);
		LinkedList<Integer> ll = new LinkedList<Integer>();
		ll.addAll(Arrays.asList(indexes));
		
		for(int i=k; i<n; i++){
			int start = k;
			while(start > 0 && cur[ll.get(start-1)] > cur[i]){
				start --;
			}
			ll.add(start, i);
		}
		ll.subList(0, k).toArray(indexes);
		return indexes;
	}
	
	public static Integer[] rowMaxN(double[][] array, int row, int k){
		return maxN(array[row], k);
	}
	
	public static Integer[] rowMinN(double[][] array, int row, int k){
		return minN(array[row], k);
	}
	
	public static Integer[] colMinN(double[][] array, int col, int k){
		int n = array.length;
		double[] cur = new double[n];
		for(int i=0; i<n; i++){
			cur[i] = array[i][col];
		}
		return minN(cur, k);
	}
}
