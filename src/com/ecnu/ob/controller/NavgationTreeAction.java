package com.ecnu.ob.controller;

import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.ecnu.ob.gui.MainFrame;
import com.ecnu.ob.gui.ObContentPanel;
import com.ecnu.ob.gui.ObNavPanel;
import com.ecnu.ob.gui.component.NavgationPopupMenu;
import com.ecnu.ob.gui.component.ProcessDialog;
import com.ecnu.ob.gui.component.tab.BaseTabPanel.TabType;
import com.ecnu.ob.gui.component.tab.QueryTabPanel;
import com.ecnu.ob.gui.component.tab.QueryTabPanel.QueryTabType;
import com.ecnu.ob.jdbc.Query;
import com.ecnu.ob.main.Main;
import com.ecnu.ob.manager.ClusterManager;
import com.ecnu.ob.manager.DataBaseManager;
import com.ecnu.ob.model.ClusterInfo;
import com.ecnu.ob.model.Column;
import com.ecnu.ob.model.ColumnValue;
import com.ecnu.ob.model.DataBaseInfo;
import com.ecnu.ob.model.TabInfo;
import com.ecnu.ob.model.TableInfo;
import com.ecnu.ob.utils.CommonUtil;
import com.ecnu.ob.utils.Triple;

public class NavgationTreeAction extends MouseAdapter implements TreeExpansionListener {
	
	private static long SLEEPTIME = 5000;
	
	private ObNavPanel navPanel;
	
	private ObContentPanel contentPanel;
	
	final NavgationPopupMenu popupMenu;
	
	private List<ClusterInfo> clusters;
	private List<TableInfo> sysTables;
	private List<DataBaseInfo> dbs;
	
	public NavgationTreeAction(ObNavPanel navPanel, ObContentPanel contentPanel) {
		this.navPanel = navPanel;
		this.contentPanel = contentPanel;
		popupMenu  = new NavgationPopupMenu(this);
	}
	
	public void mousePressed(MouseEvent e) {
		JTree tree = (JTree) e.getSource();
		int modes = e.getModifiers();
		TreePath path = tree.getPathForLocation(e.getX(), e.getY());
		if((modes & InputEvent.BUTTON3_MASK) != 0) {
			popupMenu.setSelectedPath(path);
			popupMenu.show(tree, e.getX(), e.getY());
		}
		if(path != null) {
			tree.setSelectionPath(path);
			if((modes & InputEvent.BUTTON1_MASK) != 0) {
				if(e.getClickCount()==2) {//双击
					//expand(tree, false);//展开
				} else {
					valueChanged(tree);
				}
			} 
		}
	}

	public void loadData() {
		clusters = ClusterManager.getAllCluster(Main.LMSIPPORT, Main.USERNAME, Main.USERPASS);
		sysTables = DataBaseManager.getAllSystemTable(Main.LMSIPPORT, Main.USERNAME, Main.USERPASS);
		dbs = ClusterManager.getAllDatabase(Main.LMSIPPORT, Main.USERNAME, Main.USERPASS);
		navPanel.createNavTree(clusters, sysTables, dbs, this);
	}
	
	public void valueChanged(JTree tree) {
		TreePath treePath = tree.getSelectionPath();
		if(treePath != null && treePath.getPathCount() > 2) {
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			Object userObject = selectedNode.getUserObject();
			if(userObject != null) {
				String title = userObject.toString();
				if(isQueryLeaf(treePath)) {
					String dbname = "";
					if(treePath.getPathCount() > 4) {
						dbname = tree.getSelectionPath().getPathComponent(2).toString();
					}
					Query q = new Query(Main.LMSIPPORT, dbname, Main.USERNAME, Main.USERPASS);
					
					double timeUsed = 0.0f;
					long rowCount = 0;
					
					Vector<Column> headers = new Vector<Column>();
					Map<String, Column> headerMap = new HashMap<String, Column>();
					Vector<String> headerNames = new Vector<String>();
					
					Vector<Vector<ColumnValue>> rows = new Vector<Vector<ColumnValue>>();
					String defaultSql = "select * from " + title + " limit 0, 100";
					try {
						long startTime = System.currentTimeMillis();
						ResultSet rs = q.executeQuery(defaultSql, 5);
						timeUsed = ((double)(System.currentTimeMillis() - startTime))/1000;
						Triple<Vector<String>, Vector<Vector<ColumnValue>>, Long> data = CommonUtil.parseTable(rs);
						
						//Get column TYPE and ROWKEY
						ResultSet headerRs = q.executeQuery("desc " + title, 3);
						while(headerRs.next()) {
							String cName = headerRs.getString(1);
							String cType = headerRs.getString(2);
							int cRowkey = headerRs.getInt(4);
							headerMap.put(cName, new Column(cName, cType, cRowkey));
						}
						headerNames.addAll(data.first);
						rows.addAll(data.second);
						rowCount = data.third;
						
						for(String name: headerNames) {
							headers.add(headerMap.get(name));
						}
					} catch (Exception e1) {
						if(MainFrame.debug) {
							e1.printStackTrace();
						}
						String[] exc = e1.toString().split(":");
						if(exc.length > 0) {
							MainFrame.alert(exc[exc.length - 1].trim());
						}
					} finally {
						q.close();
					}
					contentPanel.addTab(title, new TabInfo(headers, rows, (rowCount == 0), defaultSql, dbname, 
							navPanel.getDbNameList(), QueryTabType.TABLE), TabType.TAB_QEURY);
					getQueryTabPanel().setResultLabel(" " + rowCount + " rows in set (" + timeUsed + " sec)");
				} else if(isClusterLeaf(treePath)) {
					TabInfo tinfo = new TabInfo();
					DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode)selectNode.getParent();
					int serverIndex= parent.getIndex(selectNode);
					int clusterIndex  = getSelectNodeIndex(tree);
					
					tinfo.clusterInfo = clusters.get(clusterIndex);
					tinfo.serverInfo = tinfo.clusterInfo.getAllServer().get(serverIndex);
					
					contentPanel.addTab(title, tinfo, TabType.TAB_SERVER);
				}
			} 
			
		}
		if(treePath.getPathCount() == 2) {
			if(tree.getExpandedDescendants(treePath) == null) {
				tree.expandPath(treePath);//展开
			} else {
				tree.collapsePath(treePath);//关闭
			}
		}
	}
	
	private boolean isQueryLeaf(TreePath treePath) {
		String sub = ((DefaultMutableTreeNode)treePath.getPathComponent(1)).getUserObject().toString();
		return (treePath.getPathCount() > 4 
				&& !(treePath.getPathCount() == 5 
				&& ((DefaultMutableTreeNode)treePath.getPathComponent(3)).getUserObject().toString().equals("Index")))
				|| sub.equals(navPanel.navString[1]);
	}
	
	private boolean isClusterLeaf(TreePath treePath) {
		String sub = ((DefaultMutableTreeNode)treePath.getPathComponent(1)).getUserObject().toString();
		return (treePath.getPathCount() > 3 && sub.equals(navPanel.navString[0]));
	}
	
	private QueryTabPanel getQueryTabPanel() {
		return (QueryTabPanel)contentPanel.getCurrentTabPanel();
	}
	
	/**
	 * 延迟展开加载数据，重新加载数据
	 * @param tree
	 */
	public void expand(final JTree tree, final TreePath treePath, boolean forceReload) {
		// 只有指定强制刷新或当前节点子节点未加载时才刷新
		DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		if(!forceReload && selectNode.getChildCount() == 1 
				&& ((DefaultMutableTreeNode)selectNode.getChildAt(0)).getUserObject() == null) {
			forceReload = true;
		}
		if(!forceReload) {
			return;
		}
		
		//延迟展开cluster里面的集群的server
		if(treePath.getPathCount() == 3 && treePath.getPathComponent(1).toString().equals("Clusters")) {
			final ProcessDialog process = new ProcessDialog(MainFrame.frame,"正在加载集群服务器信息...");
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode)selectNode.getParent();
			int selectIndex = parent.getIndex(selectNode);
			//获取选中位置的集群的model
			final ClusterInfo clusterInfo = NavgationTreeAction.this.clusters.get(selectIndex);
			process.setNotice(String.format("正在加载集群[%s]的服务器信息...", clusterInfo.getCluster_id()));
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					//清空旧数据
					removeSelectNodeItems(tree);
					//加载所有的server
					NavgationTreeAction.this.navPanel.loadServer(clusterInfo.getAllServer());
					tree.expandPath(treePath);//展开
					try {
						Thread.sleep(SLEEPTIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					process.closeDialog();
				}
			});
			thread.start();
			process.showDialog();
		}
		
		//判断是否是展开索引目录 [Root Database [DBNAME] Index]
		if(treePath.getPathCount() == 4 && treePath.getPathComponent(1).toString().equals("Database") && treePath.getLastPathComponent().toString().equals("Index")) {
			final ProcessDialog process = new ProcessDialog(MainFrame.frame,"正在加载索引信息...");
			//获取选中索引
			int selectIndex = getSelectNodeIndex(tree);
			final DataBaseInfo dbInfo = NavgationTreeAction.this.dbs.get(selectIndex);
			process.setNotice(String.format("正在加载数据库[%s]的索引信息...", dbInfo.getDb_name()));
			//开启线程来操作
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					//清空旧数据
					removeSelectNodeItems(tree);
					//加载所有的server
					NavgationTreeAction.this.navPanel.loadIndexTable(dbInfo.getTableList());
					tree.expandPath(treePath);//展开
					try {
						Thread.sleep(SLEEPTIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					process.closeDialog();
				}
			});
			thread.start();
			process.showDialog();
		}
		
		
		//判断是否是展开表[Root Database [DBNAME] Table]
		if(treePath.getPathCount() == 4 && treePath.getPathComponent(1).toString().equals("Database") && treePath.getLastPathComponent().toString().equals("Table")) {
			final ProcessDialog process = new ProcessDialog(MainFrame.frame,"正在加载表数据...");
			int selectIndex = getSelectNodeIndex(tree);
			final DataBaseInfo dbInfo = NavgationTreeAction.this.dbs.get(selectIndex);
			process.setNotice(String.format("正在加载数据库[%s]的表信息...", dbInfo.getDb_name()));
			//开启线程来操作
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					//清空旧数据
					removeSelectNodeItems(tree);
					//加载所有的server
					NavgationTreeAction.this.navPanel.loadTable(dbInfo.getTableList());
					tree.expandPath(treePath);//展开		
					//tree.collapsePath(path);
					try {
						Thread.sleep(SLEEPTIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					process.closeDialog();
				}
			});
			thread.start();
			process.showDialog();
			
		}
		
		//判断是否是展开库[Root Database]
		if(treePath.getPathCount() == 2 && treePath.getPathComponent(1).toString().equals("Database") && treePath.getLastPathComponent().toString().equals("Database")) {
			final ProcessDialog process = new ProcessDialog(MainFrame.frame,"正在加载库信息...");
			//开启线程来操作
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					//清空旧数据
					removeSelectNodeItems(tree);
					//重新拉取数据库信息
					dbs = ClusterManager.getAllDatabase(Main.LMSIPPORT, Main.USERNAME, Main.USERPASS);
					NavgationTreeAction.this.navPanel.loadDatabases(dbs);
					tree.expandPath(treePath);//展开
					try {
						Thread.sleep(SLEEPTIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					process.closeDialog();
				}
			});
			thread.start();
			process.showDialog();
		}
		
		
		//判断是否是展开系统表[Root System]
		if(treePath.getPathCount() == 2 && treePath.getPathComponent(1).toString().equals("System Tables") && treePath.getLastPathComponent().toString().equals("System Tables")) {
			final ProcessDialog process = new ProcessDialog(MainFrame.frame,"正在加载系统表信息...");
			//开启线程来操作
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					//清空旧数据
					removeSelectNodeItems(tree);
					//重新拉取数据库信息
					sysTables = DataBaseManager.getAllSystemTable(Main.LMSIPPORT, Main.USERNAME, Main.USERPASS);
					NavgationTreeAction.this.navPanel.loadSystemTable(sysTables);
					tree.expandPath(treePath);//展开
					try {
						Thread.sleep(SLEEPTIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					process.closeDialog();
				}
			});
			thread.start();
			process.showDialog();
		}
		
		
		//判断是否是展开集群[Root Cluster]
		if(treePath.getPathCount() == 2 && treePath.getPathComponent(1).toString().equals("Clusters") && treePath.getLastPathComponent().toString().equals("Clusters")) {
			final ProcessDialog process = new ProcessDialog(MainFrame.frame,"正在加载集群信息...");
			//开启线程来操作
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					//清空旧数据
					removeSelectNodeItems(tree);
					//重新拉取数据库信息
					clusters = ClusterManager.getAllCluster(Main.LMSIPPORT, Main.USERNAME, Main.USERPASS);
					NavgationTreeAction.this.navPanel.loadCluster(clusters);
					tree.expandPath(treePath);//展开
					try {
						Thread.sleep(SLEEPTIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					process.closeDialog();
				}
			});
			thread.start();
			process.showDialog();
		}
		
		//判断是否是展开数据库[Root Database [DATABASE]]
		if(treePath.getPathCount() == 3 && treePath.getPathComponent(1).toString().equals("Database") ) {
			final ProcessDialog process = new ProcessDialog(MainFrame.frame,"正在加载数据库信息...");
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode)selectNode.getParent();
			int selectIndex = parent.getIndex(selectNode);
			final DataBaseInfo dbInfo = NavgationTreeAction.this.dbs.get(selectIndex);
			process.setNotice(String.format("正在加载数据库[%s]的信息...", dbInfo.getDb_name()));
			//开启线程来操作
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					//清空旧数据
					removeSelectNodeItems(tree);
					//加载所有的server
					NavgationTreeAction.this.navPanel.loadDatabase(dbInfo);
					tree.expandPath(treePath);//展开		
					//tree.collapsePath(path);
					try {
						Thread.sleep(SLEEPTIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					process.closeDialog();
				}
			});
			thread.start();
			process.showDialog();
		}
	}
	
	public void handleRefreshAction() {
		JTree tree = NavgationTreeAction.this.navPanel.getNavTree();
		expand(tree, tree.getSelectionPath(), true);
	}
	
	public void handleRefreshAllAction() {
		
		final ProcessDialog process = new ProcessDialog(MainFrame.frame,"正在刷新全部数据...");
		//开启线程来操作
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				loadData();
				contentPanel.refreshDbNameList(navPanel.getDbNameList());
				try {
					Thread.sleep(SLEEPTIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				process.closeDialog();
			}
		});
		thread.start();
		process.showDialog();
	}
	
	public void handleOpen(TreePath path) {
		JTree tree = NavgationTreeAction.this.navPanel.getNavTree();
		tree.setSelectionPath(path);
		expand(tree, tree.getSelectionPath(), false);//展开
		if(isQueryLeaf(path) || isClusterLeaf(path)) {
			valueChanged(tree);
		} else {
			tree.expandPath(path);//展开
		}
	}
	
	/**
	 * 移除选中节点的子节点
	 * @param tree
	 */
	public void removeSelectNodeItems(JTree tree) {
		
		DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode temp = null;
		List<DefaultMutableTreeNode> deleteList = new ArrayList<DefaultMutableTreeNode>();
		for (int i = 0; i < selectNode.getChildCount(); i++) {
			temp = (DefaultMutableTreeNode) selectNode.getChildAt(i);
			deleteList.add(temp);
		}
		for (DefaultMutableTreeNode defaultMutableTreeNode : deleteList) {
			treeModel.removeNodeFromParent(defaultMutableTreeNode);
		}
	}
	
	/**
	 * 获取选中节点所在位置索引，针对Database目录下
	 * @param tree
	 * @return
	 */
	public int getSelectNodeIndex(JTree tree) {
		DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode)selectNode.getParent();
		
		DefaultMutableTreeNode superParent = (DefaultMutableTreeNode)parent.getParent();
		
		int selectIndex = superParent.getIndex(parent);
		return selectIndex;
	}
	
	
	public static void main(String[] args) {
		Query q = new Query("182.119.80.66:62880", "", "admin", "admin");
		try {
			ResultSet rs = q.executeQuery("desc __all_server", 3);
			while(rs.next()) {
				System.out.println(rs.getObject(1).toString() + rs.getObject(2) + rs.getObject(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			q.close();
		}
	}

	@Override
	public void treeCollapsed(TreeExpansionEvent e) {
		
	}

	@Override
	public void treeExpanded(TreeExpansionEvent e) {
		JTree tree = navPanel.getNavTree();
		TreePath path = e.getPath();
		tree.setSelectionPath(path);
		expand(tree, path, false);//展开
	}

}
