package com.ecnu.ob.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.ecnu.ob.controller.NavgationTreeAction;
import com.ecnu.ob.model.ClusterInfo;
import com.ecnu.ob.model.DataBaseInfo;
import com.ecnu.ob.model.IndexInfo;
import com.ecnu.ob.model.ServerInfo;
import com.ecnu.ob.model.TableInfo;

@SuppressWarnings("serial")
public class ObNavPanel extends JScrollPane {
	
	public String[] navString = {"Clusters", "System Tables", "Database"};
	
	private List<String> dbNameList = new ArrayList<String>();
	
	//cluster节点
	private DefaultMutableTreeNode clusterNode;
	
	//tree对象
	private JTree navTree;
	
	//root节点
	private DefaultMutableTreeNode root;
	
	private DefaultMutableTreeNode systemNode;
	
	private DefaultMutableTreeNode databaseNode;
	
	public JTree getNavTree() {
		return navTree;
	}

	public ObNavPanel() {
		
	}
	
	public void createNavTree(List<ClusterInfo> clusters, List<TableInfo> sysTables, 
			List<DataBaseInfo> dbs, NavgationTreeAction navTreeAction) {
		this.root = new DefaultMutableTreeNode("root");
		
		/*************** Clusters ***************/
		clusterNode = new DefaultMutableTreeNode(navString[0]);
		for (int i = 0; i < clusters.size(); i++) {
//			DefaultMutableTreeNode node1_temp = new DefaultMutableTreeNode(clusters.get(i).getCluster_vip());
			DefaultMutableTreeNode node1_temp = new DefaultMutableTreeNode("Cluster " + clusters.get(i).getCluster_id());
			//node1_temp.setAllowsChildren(true);
			node1_temp.add(new DefaultMutableTreeNode());
			clusterNode.add(node1_temp);
		}
		
		/*************** System Tables ***************/
		systemNode= new DefaultMutableTreeNode(navString[1]);
		for (TableInfo tableInfo : sysTables) {
			DefaultMutableTreeNode node2_temp = new DefaultMutableTreeNode(tableInfo.getTable_name());
			systemNode.add(node2_temp);
		}
		
		/*************** Database ***************/
		databaseNode = new DefaultMutableTreeNode(navString[2]);	
		dbNameList.clear();
		for (DataBaseInfo dataBaseInfo : dbs) {
			dbNameList.add(dataBaseInfo.getDb_name());
			
			DefaultMutableTreeNode node3_Db = new DefaultMutableTreeNode(dataBaseInfo.getDb_name());
			/*************** Table ***************/
			DefaultMutableTreeNode node3_Db_Table = new DefaultMutableTreeNode("Table");
			List<TableInfo> tblist = dataBaseInfo.getTableList();
			for (TableInfo tableInfo : tblist) {
				
				DefaultMutableTreeNode node3_Db_Table_Name = new DefaultMutableTreeNode(tableInfo.getTable_name());
				node3_Db_Table.add(node3_Db_Table_Name);
			}
			/*************** Index ***************/
			DefaultMutableTreeNode node3_Db_Index = new DefaultMutableTreeNode("Index");
			node3_Db_Index.add(new DefaultMutableTreeNode());
			node3_Db.add(node3_Db_Table);
			node3_Db.add(node3_Db_Index);
			databaseNode.add(node3_Db);
		}
		
		root.add(clusterNode);
		root.add(systemNode);
		root.add(databaseNode);
		this.navTree = new JTree(root);
		this.navTree.setRootVisible(false);
		this.navTree.expandRow(root.getChildCount() - 1);
		this.setViewportView(this.navTree);
		this.navTree.addMouseListener(navTreeAction);
		this.navTree.addTreeExpansionListener(navTreeAction);
		
	}
	
	//加载集群信息
	public void loadCluster(List<ClusterInfo> clusters) {
		DefaultTreeModel treeModel = (DefaultTreeModel)this.navTree.getModel();
		TreePath parentPath = this.navTree.getSelectionPath();
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)parentPath.getLastPathComponent();
		for (ClusterInfo clusterInfo : clusters) {
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("Cluster " + clusterInfo.getCluster_id());
			newNode.add(new DefaultMutableTreeNode());
//				newNode.setAllowsChildren(true);
			treeModel.insertNodeInto(newNode, parentNode, parentNode.getChildCount());
		}
	}
	
	//加载这个集群的所有server
	public void loadServer(List<ServerInfo> serverList) {
		DefaultTreeModel treeModel = (DefaultTreeModel)this.navTree.getModel();
		TreePath parentPath = this.navTree.getSelectionPath();
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)parentPath.getLastPathComponent();
		for (ServerInfo serverInfo : serverList) {
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(serverInfo.toString());		
//			newNode.setAllowsChildren(true);
			treeModel.insertNodeInto(newNode, parentNode, parentNode.getChildCount());
		}
	}
	
	public List<String> getDbNameList() {
		return dbNameList;
	}
	
	public void loadIndexTable(List<TableInfo> tableList) {
		DefaultTreeModel treeModel = (DefaultTreeModel)this.navTree.getModel();
		TreePath parentPath = this.navTree.getSelectionPath();
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)parentPath.getLastPathComponent();
		
		for (TableInfo tableInfo : tableList) {
			
			List<IndexInfo> indexList = tableInfo.getIndexList();
			if(indexList.size() > 0) {
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(tableInfo.getTable_name());
				for (IndexInfo indexInfo : indexList) {
					DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(indexInfo.getIndexName());
					newNode.add(subNode);
				}
				treeModel.insertNodeInto(newNode, parentNode, parentNode.getChildCount());
			}
			
		}
			
	}
	
	/**
	 * 加载表数据
	 * @param tableList
	 */
	public void loadTable(List<TableInfo> tableList) {
		
		DefaultTreeModel treeModel = (DefaultTreeModel)this.navTree.getModel();
		TreePath parentPath = this.navTree.getSelectionPath();
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)parentPath.getLastPathComponent();
		
		for (TableInfo tableInfo : tableList) {
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(tableInfo.getTable_name());		
//			newNode.setAllowsChildren(true);
			treeModel.insertNodeInto(newNode, parentNode, parentNode.getChildCount());
			
		}
	}
	
	/**
	 * 加载所有库
	 * @param dbs
	 */
	public void loadDatabases(List<DataBaseInfo> dbs) {
		
		DefaultTreeModel treeModel = (DefaultTreeModel)this.navTree.getModel();
//		TreePath parentPath = this.navTree.getSelectionPath();
//		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)parentPath.getLastPathComponent();
		DefaultMutableTreeNode parentNode = databaseNode;
		dbNameList.clear();
		for (DataBaseInfo dataBaseInfo : dbs) {
			dbNameList.add(dataBaseInfo.getDb_name());
			
			DefaultMutableTreeNode dbNode = new DefaultMutableTreeNode(dataBaseInfo.getDb_name());
			/*************** Table ***************/
			DefaultMutableTreeNode dbTableNode = new DefaultMutableTreeNode("Table");
			List<TableInfo> tblist = dataBaseInfo.getTableList();
			for (TableInfo tableInfo : tblist) {
				
				DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(tableInfo.getTable_name());
				dbTableNode.add(tableNode);
			}
			/*************** Index ***************/
			DefaultMutableTreeNode dnIndexNode = new DefaultMutableTreeNode("Index");
			dbNode.add(dbTableNode);
			dbNode.add(dnIndexNode);
			
			treeModel.insertNodeInto(dbNode, parentNode, parentNode.getChildCount());
		}

	}
	/**
	 * 加载一个数据库的表和索引
	 * @param db
	 */
	public void loadDatabase(DataBaseInfo db) {
		
		DefaultTreeModel treeModel = (DefaultTreeModel)this.navTree.getModel();
		TreePath parentPath = this.navTree.getSelectionPath();
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)parentPath.getLastPathComponent();
		
		DefaultMutableTreeNode dbTableNode = new DefaultMutableTreeNode("Table");
		List<TableInfo> tblist = db.getTableList();
		for (TableInfo tableInfo : tblist) {
			
			DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(tableInfo.getTable_name());
			dbTableNode.add(tableNode);
		}
		/*************** Index ***************/
		DefaultMutableTreeNode dnIndexNode = new DefaultMutableTreeNode("Index");
		dnIndexNode.add(new DefaultMutableTreeNode());
		treeModel.insertNodeInto(dbTableNode, parentNode, parentNode.getChildCount());
		treeModel.insertNodeInto(dnIndexNode, parentNode, parentNode.getChildCount());
	}
	
	/**
	 * 加载系统表
	 * @param dbs
	 */
	public void loadSystemTable(List<TableInfo> sysTables) {
		
		DefaultTreeModel treeModel = (DefaultTreeModel)this.navTree.getModel();
//		TreePath parentPath = this.navTree.getSelectionPath();
//		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)parentPath.getLastPathComponent();
		DefaultMutableTreeNode parentNode = systemNode;
		for (TableInfo tableInfo : sysTables) {
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(tableInfo.getTable_name());
			treeModel.insertNodeInto(newNode, parentNode, parentNode.getChildCount());
		}

	}

	
}
