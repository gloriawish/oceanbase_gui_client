package com.ecnu.ob.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Tree{
	private final static int ROOT = 0;
	
	private HashMap<String, TreeNode> nodes;
	
	private TraveralStrategy traveralStrategy;
	
	//Constructors
	public Tree(){
		this(TraveralStrategy.DepthFirst);
	}
	
	public Tree(TraveralStrategy traveralStrategy)
	{
		this.nodes = new HashMap<String, TreeNode>();
		this.traveralStrategy = traveralStrategy;
	}
	
	//Properties
	public HashMap<String, TreeNode> getNodes()
	{
		return nodes;
	}
	
	public TraveralStrategy getTraveralStrategy()
	{
		return traveralStrategy;
	}
	
	public void setTraveralStrategy(TraveralStrategy traveralStrategy)
	{
		this.traveralStrategy = traveralStrategy;
	}
	
	//Public interface
	public TreeNode addNode(String identifier,String detail)
	{
		return this.addNode(identifier,detail, null);
	}
	
	public TreeNode addNode(String identifier, String detail,String parent)
	{
		TreeNode node = new TreeNode(identifier,detail);
		nodes.put(identifier, node);
		
		if (parent != null)
		{
			//System.out.println("TREE       "+nodes.get(parent)+"parent    "+parent);
			nodes.get(parent).addChild(identifier);
		}
		
		return node;
	}
	
	public void display(String identifier)
	{
		this.display(identifier, ROOT);
	}
	
	public void display(String identifier, int depth)
	{
		ArrayList<String> children = nodes.get(identifier).getChildren();
		
		if(depth == ROOT)
		{
			System.out.println(nodes.get(identifier).getIdentifier());
		}
		else
		{
			String tabs = String.format("%0" + depth + "d", 0).replace("0", "    ");
			System.out.println(tabs + nodes.get(identifier).getIdentifier());
		}
		depth++;
		
		for(String child:children)
		{
			this.display(child, depth);
		}
	}
	
	public Iterator<TreeNode> iterator(String identifier)
	{
		return this.iterator(identifier, traveralStrategy);
	}
	
	public Iterator<TreeNode> iterator(String identifier, TraveralStrategy traveralStrategy)
	{
		return new DepthFirstTreeIterator(nodes, identifier);
		//return traveralStrategy ==TraveralStrategy.BreadFirst? new BreadthFirstTreeIterator(nodes, identifier); new DepthFirstTreeIterator(nodes, identifier);
	}
	
	
}
