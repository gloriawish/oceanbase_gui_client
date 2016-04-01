package com.ecnu.ob.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JToolBar;

import com.ecnu.ob.controller.OpenToolTabPanelAction.ToolActionType;
import com.ecnu.ob.gui.component.ConnectionDialog;

@SuppressWarnings("serial")
public class ObToolBar extends JToolBar {
	
	private JButton rsAdminBtn;
	
	private JButton upsAdminBtn;
	
	private JButton csAdminBtn;
	
	private JButton obExportBtn;
	
	private JButton obImportBtn;

	private JButton queryBtn;
	
	public ObToolBar(final ConnectionDialog connDialog) {
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		final JButton btnNewButton = new JButton("Connection");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				connDialog.setVisible(true);
			}
		});
		add(btnNewButton);
		
		rsAdminBtn = new JButton("RS Admin");
		add(rsAdminBtn);
		
		upsAdminBtn = new JButton("UPS Admin");
		add(upsAdminBtn);
		
		csAdminBtn = new JButton("CS Admin");
		add(csAdminBtn);
		
		obExportBtn = new JButton("OB Export");
		add(obExportBtn);
		
		obImportBtn = new JButton("OB Import");
		add(obImportBtn);
		queryBtn = new JButton("Query");
		add(queryBtn);
	}
	
	public void addToolTabAction(ActionListener action, ToolActionType type) {
		if(type == ToolActionType.RS_ADMIN_ACTION && rsAdminBtn != null) {
			this.rsAdminBtn.addActionListener(action);
		} else if(type == ToolActionType.UPS_ADMIN_ACTION && upsAdminBtn != null) {
			this.upsAdminBtn.addActionListener(action);
		} else if(type == ToolActionType.CS_ADMIN_ACTION && csAdminBtn != null) {
			this.csAdminBtn.addActionListener(action);
		} else if(type == ToolActionType.OB_EXPORT_ACTION && obExportBtn != null) {
			this.obExportBtn.addActionListener(action);
		} else if(type == ToolActionType.OB_IMPORT_ACTION && obImportBtn != null) {
			this.obImportBtn.addActionListener(action);
		}
	}
	
	public void setQueryAction(AbstractAction action) {
		if(queryBtn != null) {
			this.queryBtn.setAction(action);
		}
	}
	
}
