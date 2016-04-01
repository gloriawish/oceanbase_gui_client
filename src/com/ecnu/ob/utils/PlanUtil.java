package com.ecnu.ob.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.ecnu.ob.model.ObOperatorType;
import com.ecnu.ob.model.PlanTreeNode;

public class PlanUtil {
	public static Pair<Tree, String> reslove_explain_result(String explain_result)
	{
		//reconstruct the physical plan tree
		Tree physical_plan_tree = new Tree();
		Stack<String> doubleChildOperatorStorage = new Stack<String>();
		String[] explainSubResult =  explain_result.split("\n");
		String parents = null;
		String Root = null;
		for (int i = 0;i < explainSubResult.length;i++)
		{
			if((explainSubResult[i].contains("right") || explainSubResult[i].contains("Right") || explainSubResult[i].contains("Child1")) && parents != null)
			{
				parents = doubleChildOperatorStorage.pop();
			}
			if(explainSubResult[i].contains("("))
			{
				int indexOfSub = explainSubResult[i].indexOf("(");
				String operatorType = explainSubResult[i].substring(0, indexOfSub);
				if(ObOperatorType.isDoubleOperatorType(operatorType))
				{
					if(parents != null)
					{
						doubleChildOperatorStorage.push(operatorType+ ":" + i);
						physical_plan_tree.addNode(operatorType+ ":" + i, explainSubResult[i],parents);
						parents = operatorType+ ":" + i;
					}
					else
					{
						Root = operatorType+ ":" + i;
						parents = operatorType+ ":" + i;
						doubleChildOperatorStorage.push(operatorType+ ":" + i);
						physical_plan_tree.addNode(operatorType+ ":" + i, explainSubResult[i]);
					}
				}
				else
				{
					if(parents != null)
					{
						//System.out.println("operatorType " + operatorType+ i +" parents  "+ parents);
						physical_plan_tree.addNode(operatorType+ ":" + i, explainSubResult[i],parents);
						parents = operatorType+ ":" + i;
					}
					else
					{
						Root = operatorType+ ":" + i;
						parents = operatorType+ ":" + i;
						physical_plan_tree.addNode(operatorType+ ":" + i,explainSubResult[i]);
					}
				}
			}
		}
		//print the plan tree for test 
//		Iterator<TreeNode> depthIterator = physical_plan_tree.iterator(Root);
//		while(depthIterator.hasNext())
//		{
//			TreeNode node = depthIterator.next();
//			System.out.println(node.getIdentifier());
//		}
		//physical_plan_tree.display(Root);
		return new Pair<Tree, String>(physical_plan_tree, Root);
	}
	
	public static String reslove_operator(String operatorTpye, String operatorDetail)
	{
		String reslove_result = new String();
		
		return  reslove_result;
	}
	
	public static DefaultMutableTreeNode createNode(Tree tree, String identifier, int depth) {
		HashMap<String, TreeNode> nodeMap = tree.getNodes();
		ArrayList<String> children = nodeMap.get(identifier).getChildren();
		TreeNode tempNode = nodeMap.get(identifier);
		String temp = tempNode.getIdentifier();
		String detail = tempNode.getDetail();
		String[] temps = temp.split(":");
		DefaultMutableTreeNode node = null;
		if(temps.length == 2)
		{
		    node = new DefaultMutableTreeNode(new PlanTreeNode(temps[0], detail));
			depth++;
		}
		
		for(String child:children)
		{
			node.add(createNode(tree, child, depth));
		}
		return node;
	}
	
	public static void expandAll(JTree tree, TreePath parent) {
		javax.swing.tree.TreeNode node = (javax.swing.tree.TreeNode)parent.getLastPathComponent();
		if(node.getChildCount() >= 0) {
			for(Enumeration<?> e = node.children(); e.hasMoreElements();) {
				javax.swing.tree.TreeNode n = (javax.swing.tree.TreeNode)e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path);
			}
		}
		tree.expandPath(parent);
	}
}
