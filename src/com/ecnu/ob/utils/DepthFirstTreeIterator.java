package com.ecnu.ob.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import com.ecnu.ob.utils.TreeNode;
//import com.ecnu.ob.utils.tree;

public class DepthFirstTreeIterator implements Iterator<TreeNode> {
	private LinkedList<TreeNode> list;
	
	public DepthFirstTreeIterator(HashMap<String, TreeNode> tree, String identifier)
	{
		list = new LinkedList<TreeNode>();
		
		if (tree.containsKey(identifier))
		{
			this.buildList(tree,identifier);
		}
	}
	
	private void buildList(HashMap<String, TreeNode> tree, String identifier)
	{
		list.add(tree.get(identifier));
		ArrayList<String> children = tree.get(identifier).getChildren();
		for(String child : children)
		{
			this.buildList(tree, child);
		}
	}
	
	public boolean hasNext()
	{
		return !list.isEmpty();
	}
	
	public TreeNode next()
	{
		return list.poll();
	}
	
	public void remove(){
		throw new UnsupportedOperationException();
	}
}
