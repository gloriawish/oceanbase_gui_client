package com.ecnu.ob.model;

public class PlanTreeNode {

	public String operatorName;
	
	public String detail;
	
	public PlanTreeNode(String operatorName, String detail) {
		this.operatorName = operatorName;
		this.detail = detail;
	}
	
	public String toString() {
		return operatorName;
	}
}
