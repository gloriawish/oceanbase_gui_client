package com.ecnu.ob.gui.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreePath;

import com.ecnu.ob.controller.NavgationTreeAction;

@SuppressWarnings("serial")
public class NavgationPopupMenu extends JPopupMenu {
	
	private JMenuItem openItem;
	
	private JMenuItem refreshItem;
	
	private TreePath selectedPath;
	
	public NavgationPopupMenu(final NavgationTreeAction navgationTreeAction) {
		openItem = new JMenuItem("open");
		openItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				navgationTreeAction.handleOpen(selectedPath);
			}
		});
		add(openItem);
		
		//ÓÒ¼ü¼ÓÔØÊý¾Ý
		refreshItem = new JMenuItem("refresh");
		refreshItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(selectedPath == null) {
					navgationTreeAction.handleRefreshAllAction();
				} else {
					navgationTreeAction.handleRefreshAction();
				}
			}
		});
		add(refreshItem);
	}
	
	public void setSelectedPath(TreePath selectedPath) {
		this.selectedPath = selectedPath;
		boolean isPathSelected = (selectedPath != null);
		openItem.setEnabled(isPathSelected);
	}
}
