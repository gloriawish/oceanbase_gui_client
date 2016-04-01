package com.ecnu.ob.model;

import java.util.HashMap;
import java.util.Map;

public enum ObOperatorType {
	UNKNOWN,
	ObMergeJoin,
	ObSemiJoin,
	ObBloomFilterJoin,
	ObNestLoopJoin,
	ObMergeUnion,
	ObMergeExcept,
	ObMergeIntersect,
	ObMuitipleGetMerge,
	ObSort,
	ObProject,
	ObTableRpcScan,
	ObRpcScan;
	
	private static Map<String,ObOperatorType> opMap;
	static {
		
		opMap = new HashMap<String, ObOperatorType>();
		
		opMap.put("Merge Join", ObMergeJoin);
		opMap.put("Semi Join", ObSemiJoin);
		opMap.put("BloomFilter Join", ObBloomFilterJoin);
		opMap.put("Merge Join", ObNestLoopJoin);
		opMap.put("MergeUnion", ObMergeUnion);
		opMap.put("MergeExcept", ObMergeExcept);
		opMap.put("MergeIntersect", ObMergeIntersect);
		opMap.put("MuitipleGetMerge", ObMuitipleGetMerge);
		opMap.put("Sort", ObSort);
		opMap.put("Project", ObProject);
		opMap.put("TableRpcScan", ObTableRpcScan);
		opMap.put("RpcScan",ObRpcScan);
	}
	
//	private static Map<String,ObOperatorType> map_single_operator;
//	static{
//		map_single_operator = new HashMap<String,ObOperatorType>();
//		map_single_operator.put(arg0, arg1);
//	}
	
	private static Map<String,ObOperatorType> map_double_operator;
	static{
		map_double_operator = new HashMap<String,ObOperatorType>();
		map_double_operator.put("Merge Join", ObMergeJoin);
		map_double_operator.put("Semi Join", ObSemiJoin);
		map_double_operator.put("BloomFilter Join", ObBloomFilterJoin);
		map_double_operator.put("Merge Join", ObNestLoopJoin);
		map_double_operator.put("MergeUnion", ObMergeUnion);
		map_double_operator.put("MergeExcept", ObMergeExcept);
		map_double_operator.put("MergeIntersect", ObMergeIntersect);
		map_double_operator.put("MuitipleGetMerge", ObMuitipleGetMerge);
	}
	
//	public static boolean isObOperatorType(String type) {
//		return map.containsKey(type);
//	}
	
	public static boolean isDoubleOperatorType(String inputType){
		return map_double_operator.containsKey(inputType);
	}
	
	public static ObOperatorType getOpType(String opType){
		if(opMap.containsKey(opType))
		{
			return opMap.get(opType);
		}
		else
		{
			return UNKNOWN;
		}
	}
	
}
