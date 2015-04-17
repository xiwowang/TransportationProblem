/**
 * @usage: 检验值计算并优化基础解
 * @author: Mizzle Qiu
 */
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import util.Pair;

class OptimalMethod {
	
	private double[][] sdCost;
	
	private int s;
	private int d;
	
	private double[] rowBase;
	private double[] colBase;
	private double[][] checkValues;
	
	private Pair<Integer, Integer> curPoint = new Pair<Integer, Integer>(0, 0);
	
	HashMap<Pair<Integer, Integer>, Integer> map = new HashMap<Pair<Integer, Integer>, Integer>();
	
	public void execute(double[][] sdCost, HashMap<Pair<Integer, Integer>, Integer> map){
		this.sdCost = sdCost;
		this.s = sdCost.length;
		this.d = sdCost[0].length;
		this.map.putAll(map);
		rowBase = new double[s];
		colBase = new double[d];
		checkValues = new double[s][d];
		
		while(optimal());

	}
	
	private boolean optimal(){
		// 求位势
		calcBase();
		// 计算检验值矩阵，计算最小的检验值
		calcCheckValues();
		// 所有检验值都非负，最优解达成
		if(checkValues[curPoint.k][curPoint.v] >= 0){
			return false;
		}else{
			circle();
			return true;
		}
	}
	
	public void calcBase(){
		HashSet<Integer> rowBaseResidueSet = new HashSet<Integer>();
		HashSet<Integer> colBaseResidueSet = new HashSet<Integer>();
		
		rowBase[0] = 0;
		for(int i=1; i<s; i++){
			rowBaseResidueSet.add(i);
		}
		for(int i=0; i<d; i++){
			colBaseResidueSet.add(i);
		}
		
		// map 中的基础解为m+n-1个，必然可以解出所有位势
		while(!rowBaseResidueSet.isEmpty() || !colBaseResidueSet.isEmpty()){
			for(Entry<Pair<Integer, Integer>, Integer> e : map.entrySet()){
				if(!rowBaseResidueSet.contains(e.getKey().k) && colBaseResidueSet.contains(e.getKey().v)){
					colBase[e.getKey().v] = sdCost[e.getKey().k][e.getKey().v] - rowBase[e.getKey().k];
					colBaseResidueSet.remove(e.getKey().v);
				}
				if(rowBaseResidueSet.contains(e.getKey().k) && !colBaseResidueSet.contains(e.getKey().v)){
					rowBase[e.getKey().k] =  sdCost[e.getKey().k][e.getKey().v] - colBase[e.getKey().v];
					rowBaseResidueSet.remove(e.getKey().k);
				}
			}
		}
	}
	
	public void calcCheckValues(){
		double min = Double.MAX_VALUE;
		for(int i=0; i<s; i++){
			for(int j=0; j<d; j++){
				this.checkValues[i][j] = this.sdCost[i][j] - this.rowBase[i] - this.colBase[j];
				if(this.checkValues[i][j] < min){
					min = this.checkValues[i][j];
					curPoint.k = i;
					curPoint.v = j;
				}
			}
		}
	}
	
	public void circle(){
		HashSet<Integer> rowSet = new HashSet<Integer>();
		HashSet<Integer> colSet = new HashSet<Integer>();
		
		for(Entry<Pair<Integer, Integer>, Integer> e : map.entrySet()){
			if(e.getKey().k == curPoint.k){
				colSet.add( e.getKey().v );
			}
			if(e.getKey().v == curPoint.v){
				rowSet.add( e.getKey().k );
			}
		}
		
		Pair<Integer, Integer> desPoint = null;
		for(int i : rowSet){
			for(int j : colSet){
				if(map.containsKey(new Pair<Integer, Integer>(i, j))){
					desPoint = new Pair<Integer, Integer>(i, j);

					Pair<Integer, Integer> rowPoint = new Pair<Integer, Integer>(curPoint.k, desPoint.v);
					Pair<Integer, Integer> colPoint = new Pair<Integer, Integer>(desPoint.k, curPoint.v);
					// 不能将curPoint作为map的key，因为后面可能会改变它的值
					Pair<Integer, Integer> point = new Pair<Integer, Integer>(curPoint.k, curPoint.v);

					int rowV = map.get(rowPoint);
					int colV = map.get(colPoint);
					int desV = map.get(desPoint);

					if(rowV > colV){
						map.remove(colPoint);
						map.put(desPoint, desV + colV);
						map.put(rowPoint, rowV - colV);
						map.put(point, colV);
					}else{
						map.remove(rowPoint);
						map.put(desPoint, desV + rowV);
						map.put(colPoint, colV - rowV);
						map.put(point, rowV);
					}
				}
			}
		}
	}
}
