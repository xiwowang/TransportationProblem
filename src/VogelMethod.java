/**
 * @usage: 伏格尔法(最小差值法)计算初始解
 * @author: Mizzle Qiu
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import util.Pair;
import util.TwoDimensionArrayUtil;

class VogelMethod {
	
	private int s; // supply count;
	private int d; // demand count;
	
	private int[] supplys;
	private int[] demands;
	private double[][] sdCost;
	
	private double[] supplyBalance;
	private double[] demandBalance;
	
	HashMap<Pair<Integer, Integer>, Integer> map = new HashMap<Pair<Integer, Integer>, Integer>();
	
	public void execute(int[] supplys, int[] demands, double[][] sdCost){
		this.s = supplys.length;
		this.supplys = Arrays.copyOf(supplys,  s);
		this.d = demands.length;
		this.demands = Arrays.copyOf(demands, d);
		this.sdCost = sdCost;
		this.supplyBalance = new double[s];
		this.demandBalance = new double[d];
		calcBalance();
		calcResult();
	}
	
	private void calcBalance(){
		for(int i=0; i<s; i++){
			Integer[] ints = TwoDimensionArrayUtil.rowMinN(sdCost, i, 2);
			supplyBalance[i] = sdCost[i][ints[1]] - sdCost[i][ints[0]];
		}
		
		for(int i=0; i<d; i++){
			Integer[] ints = TwoDimensionArrayUtil.colMinN(sdCost, i, 2);
			demandBalance[i] = sdCost[ints[1]][i] - sdCost[ints[0]][i];
		}
	}
	
	private void calcResult(){
		Integer[] supplyIndexes = TwoDimensionArrayUtil.sortIndex(supplyBalance, TwoDimensionArrayUtil.DESC);
		Integer[] demandIndexes =  TwoDimensionArrayUtil.sortIndex(demandBalance, TwoDimensionArrayUtil.DESC);
		
		HashSet<Integer> residueDemandSet = new HashSet<Integer>();
		for(int i=0; i<d; i++){
			residueDemandSet.add(i);
		}
		HashSet<Integer> residueSupplySet = new HashSet<Integer>();
		for(int i=0; i<s; i++){
			residueSupplySet.add(i);
		}
		
		int curSupply = 0;
		int curDemand = 0;
		while(residueSupplySet.size() > 1 && residueDemandSet.size() > 1){
			int i;
			int j;
			if(curSupply < s-1 && !residueSupplySet.contains(supplyIndexes[curSupply])){
				curSupply ++;
				continue;
			}
			if(curDemand < d-1 && !residueDemandSet.contains(demandIndexes[curDemand])){
				curDemand ++;
				continue;
			}
			
			if(curSupply < s && (curDemand > d-1 || supplyBalance[supplyIndexes[curSupply]] >= demandBalance[demandIndexes[curDemand]]) ){
				i = supplyIndexes[curSupply];
				j = TwoDimensionArrayUtil.rowMin(sdCost, i, residueDemandSet);
				curSupply ++;
			}else{
				j = demandIndexes[curDemand];
				i = TwoDimensionArrayUtil.colMin(sdCost, j, residueSupplySet);
				curDemand ++;
			}
			
			int supply = supplys[i];
			int demand = demands[j];
			if(supply >= demand){
				supplys[i] -= demand;
				map.put(new Pair<Integer, Integer>(i, j), demand);
				residueDemandSet.remove(j);
			}else{
				demands[j] -= supply;
				map.put(new Pair<Integer, Integer>(i, j), supply);
				residueSupplySet.remove(i);
			}
		}
		
		for(int i : residueSupplySet){
			for(int j : residueDemandSet){
				int supply = supplys[i];
				int demand = demands[j];
				map.put(new Pair<Integer, Integer>(i, j),  Math.min(supply, demand));
			}
		}
	}

}
