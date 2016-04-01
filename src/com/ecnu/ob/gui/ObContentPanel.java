package com.ecnu.ob.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

import com.ecnu.ob.controller.ExecuteSQLAction;
import com.ecnu.ob.controller.ExplainSQLAction;
import com.ecnu.ob.controller.ObExportAction;
import com.ecnu.ob.controller.RsAdminAction;
import com.ecnu.ob.controller.RsAdminLogAction;
import com.ecnu.ob.gui.component.ServerInfoDetailPanel;
import com.ecnu.ob.gui.component.tab.BaseTabPanel;
import com.ecnu.ob.gui.component.tab.BaseTabPanel.TabType;
import com.ecnu.ob.gui.component.tab.CsAdminTabPanel;
import com.ecnu.ob.gui.component.tab.ObExportTabPanel;
import com.ecnu.ob.gui.component.tab.QueryTabPanel;
import com.ecnu.ob.gui.component.tab.RsAdminTabPanel;
import com.ecnu.ob.gui.component.tab.ServerTabPanel;
import com.ecnu.ob.gui.component.tab.UpsAdminTabPanel;
import com.ecnu.ob.model.ActionType;
import com.ecnu.ob.model.TabInfo;

@SuppressWarnings("serial")
public class ObContentPanel extends JTabbedPane {
	
	public ObContentPanel() {
		super();
		/*
		if(MainFrame.debug) {
			Vector<Column> headers = new Vector<Column>();
			headers.add(new Column("a", "a", 1));
			headers.add(new Column("b", "b", 0));
			Vector<Vector<ColumnValue>> rows = new Vector<Vector<ColumnValue>>();
			Vector<ColumnValue> row1 = new Vector<ColumnValue>();
			row1.add(new ColumnValue("aa", ColumnValueType.STRING));
			row1.add(new ColumnValue("bb", ColumnValueType.STRING));
			rows.add(row1);
			addTab("test", new TabInfo(headers, rows, false, "select * from __all_server where cluster_id=2", "", 
					null, QueryTabType.TABLE), TabType.TAB_QEURY);
		}
		*/
	}
	
	public void addTab(String title, TabInfo tabInfo, TabType type) {
		if(getTabCount() > 6) {
			MainFrame.alert("Too many windows are openned!");
		} else {
			boolean isExist = false; 
			for(int i = 0; i < getTabCount(); i++) {
				String tmp = ((JLabel)((JPanel)getTabComponentAt(i)).getComponent(0)).getText();
				if(title.equals(tmp)) {
					isExist = true;
					setSelectedIndex(i);
					break;
				} 
			}
			if(!isExist) {
				final BaseTabPanel tabPanel;
				if(type == TabType.TAB_QEURY) {
					tabPanel = new QueryTabPanel(title, tabInfo.queryResult.headers, 
							tabInfo.queryResult.rows, tabInfo.queryResult.isEmptyRow, tabInfo.defaultSql, 
							tabInfo.dbName, tabInfo.dbNameList, tabInfo.queryTabType);
					// register Actions
					ExecuteSQLAction executeAction = new ExecuteSQLAction(this);
					tabPanel.setAction(executeAction, ActionType.EXECUTE_SQL_ACTION);
					ExplainSQLAction explainAction = new ExplainSQLAction(this);
					tabPanel.setAction(explainAction, ActionType.EXPLAIN_SQL_ACTION);
				} else if(type == TabType.TAB_RSADMIN) {
					tabPanel = new RsAdminTabPanel(title, tabInfo);
					RsAdminAction rsAdminAction = new RsAdminAction(this);
					RsAdminLogAction rsAdminLogAction = new RsAdminLogAction(this);
					tabPanel.setAction(rsAdminAction, ActionType.EXECUTE_RS_ADMIN);
					tabPanel.setAction(rsAdminLogAction, ActionType.RS_ADMIN_LOG);
				} else if(type == TabType.TAB_SERVER){
					//打开Server信息页面
					//tabPanel = new ServerInfoPanel(title,tabInfo);
					tabPanel = new ServerInfoDetailPanel(title, tabInfo);
				} else if(type == TabType.TAB_UPSADMIN){
					//tabPanel = new ServerTabPanel(title);
					tabPanel = new UpsAdminTabPanel(title, tabInfo);
				}  else if(type == TabType.TAB_CSADMIN){
					//tabPanel = new ServerTabPanel(title);
					tabPanel = new CsAdminTabPanel(title, tabInfo);
				}  else if(type == TabType.TAB_OBEXPORT){
					//tabPanel = new ServerTabPanel(title);
					tabPanel = new ObExportTabPanel(title, tabInfo);
					ObExportAction action = new ObExportAction(this);
					tabPanel.setAction(action, null);
				}  else {
					tabPanel = new ServerTabPanel(title);
				} 
				
				addTab("i", (Component) tabPanel);
				
				final JPanel titlePanel = tabPanel.getTitlePanel();
				final JLabel closeBtn = tabPanel.getCloseBtn();
				
				setTabComponentAt(indexOfComponent((Component) tabPanel), titlePanel);
				setSelectedComponent((Component) tabPanel);
				closeBtn.addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent arg0) {
						remove(indexOfTabComponent(titlePanel));
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						closeBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
						closeBtn.setForeground(Color.BLACK);
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						closeBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
						closeBtn.setForeground(Color.GRAY);
					}
					@Override
					public void mousePressed(MouseEvent arg0) {}
					@Override
					public void mouseReleased(MouseEvent arg0) {}
				});
				
				/* 右键菜单 */
				final JPopupMenu tabMenu = new JPopupMenu();
				tabMenu.removeAll();
				JMenuItem item1 = new JMenuItem("close");
				JMenuItem item2 = new JMenuItem("close others");
				JMenuItem item3 = new JMenuItem("close all");
				tabMenu.add(item1);
				tabMenu.add(item2);
				tabMenu.add(item3);
				item1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						remove(indexOfTabComponent(titlePanel));
					}
				});
				item2.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						removeAll();
						addTab("i", (Component) tabPanel);
						setTabComponentAt(indexOfComponent((Component) tabPanel), titlePanel);
						setSelectedComponent((Component) tabPanel);
					}
				});
				item3.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						removeAll();
					}
				});
				
				titlePanel.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						setSelectedComponent((Component) tabPanel);
						int modes = e.getModifiers();
						if((modes & InputEvent.BUTTON3_MASK) != 0) {
							tabMenu.show(titlePanel, e.getX(), e.getY());
						}
					}
				});
			}
		}
	}
	
	public Component getCurrentTabPanel() {
		return this.getSelectedComponent();
	}
	
	public void refreshDbNameList(List<String> dbNameList) {
		Component[] tabs = this.getComponents();
		if(tabs != null) {
			for(Component tab: tabs) {
				if(tab instanceof QueryTabPanel) {
					((QueryTabPanel)tab).setDbNameBox(dbNameList);
				}
			}
		}
	}

}
