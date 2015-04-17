/**
 * @usage: 求平衡运输问题最优解
 * @author: Mizzle Qiu
 */
import java.util.HashMap;
import java.util.Map.Entry;

import util.Pair;


public class Transportantion {
	
	public static HashMap<Pair<Integer, Integer>, Integer> solve(int[] supplys, int[] demands, double[][] sdCost){
		VogelMethod v = new VogelMethod();
		v.execute(supplys, demands, sdCost);
		
		OptimalMethod o = new OptimalMethod();
		o.execute(sdCost, v.map);
		
		return o.map;
	}
	
	public static void main(String[] args){
		int[] supplys = new int[]{60, 50, 10};
		int[] demands = new int[]{30, 40, 30, 20};
		double[][] sdCost = new double[][]{new double[]{4D, 8D, 11D, 8D},
														new double[]{3D, 8D, 12D, 7D},
														new double[]{7D, 3D, 5D, 11D}};
		
		HashMap<Pair<Integer, Integer>, Integer> map = Transportantion.solve(supplys, demands, sdCost);
		for(Entry<Pair<Integer, Integer>, Integer> e : map.entrySet()){
			System.out.println(e.getKey().k + "-->" + e.getKey().v + ": " + e.getValue());
		}
	}
}
