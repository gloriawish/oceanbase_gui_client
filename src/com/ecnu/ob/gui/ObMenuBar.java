package com.ecnu.ob.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class ObMenuBar extends JMenuBar {
	
	private JMenuItem connectItem;
	
	private JMenuItem rsAdminItem;
	
	private JMenuItem upsAdminItem;
	
	private JMenuItem csAdminItem;
	
	private JMenuItem obExportItem;
	
	private JMenuItem obImportItem;

	public ObMenuBar() {
		JMenu menu_1 = new JMenu("Database");
		add(menu_1);
		connectItem = new JMenuItem("Connect");
		menu_1.add(connectItem);
		JMenu menu_2 = new JMenu("Servers");
		rsAdminItem = new JMenuItem("RS Admin");
		upsAdminItem = new JMenuItem("UPS Admin");
		csAdminItem = new JMenuItem("CS Admin");
		menu_2.add(rsAdminItem);
		menu_2.add(upsAdminItem);
		menu_2.add(csAdminItem);
		add(menu_2);
		JMenu menu_3 = new JMenu("Tools");
		obExportItem = new JMenuItem("OB Export");
		obImportItem = new JMenuItem("OB Import");
		menu_3.add(obExportItem);
		menu_3.add(obImportItem);
		add(menu_3);
		JMenu menu_4 = new JMenu("Help");
		JMenuItem menuItem_4_1 = new JMenuItem("About Oceanbase client");
		
		menuItem_4_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainFrame.alert("OceanBase Client (ECNU) 2016 \nVersion 0.1\nAuthor Áõ°ØÖÚ,×£¾ý,ÓàêÉöÁ");
			}
			
		});
		
		menu_4.add(menuItem_4_1);
		add(menu_4);
	}
	
	public void addMenuBarAction(List<ActionListener> actionList) {
		if(actionList.size() == 6) {
			if(connectItem != null) {
				connectItem.addActionListener(actionList.get(0));
			}
			if(rsAdminItem != null) {
				rsAdminItem.addActionListener(actionList.get(1));
			}
			if(upsAdminItem != null) {
				upsAdminItem.addActionListener(actionList.get(2));
			}
			if(csAdminItem != null) {
				csAdminItem.addActionListener(actionList.get(3));
			}
			if(obExportItem != null) {
				obExportItem.addActionListener(actionList.get(4));
			}
			if(obImportItem != null) {
				obImportItem.addActionListener(actionList.get(5));
			}
		}
	}
}
