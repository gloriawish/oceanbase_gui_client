package com.ecnu.ob.utils;

import java.util.ArrayList;
//import java.util.List;

public class TreeNode {
	
	private String identifier;
	private String detail;
	private ArrayList<String> children;
	
	//Constructor
	public TreeNode(String identifier,String detail)
	{
		this.identifier = identifier;
		this.detail = detail;
		children = new ArrayList<String>();
	}
	
	//Properties
	public String getIdentifier()
	{
		return identifier;
	}
	
	public String getDetail()
	{
		return detail;
	}
	
	public ArrayList<String> getChildren()
	{
		return children;
	}
	
	public void addChild(String identifier)
	{
		children.add(identifier);
	}
}
