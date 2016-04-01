package com.ecnu.ob.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ecnu.ob.gui.ObContentPanel;
import com.ecnu.ob.gui.component.tab.BaseTabPanel.TabType;
import com.ecnu.ob.model.TabInfo;

public class OpenToolTabPanelAction implements ActionListener {
	
	private ObContentPanel contentPanel;
	
	private ToolActionType type;
	
	public enum ToolActionType {
		RS_ADMIN_ACTION,
		UPS_ADMIN_ACTION,
		CS_ADMIN_ACTION,
		OB_EXPORT_ACTION,
		OB_IMPORT_ACTION,
	}
	
	public OpenToolTabPanelAction(ObContentPanel contentPanel, ToolActionType type) {
		this.contentPanel = contentPanel;
		this.type = type;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(type == ToolActionType.RS_ADMIN_ACTION) {
			contentPanel.addTab("RS Admin", new TabInfo(), TabType.TAB_RSADMIN);
		} else if(type == ToolActionType.UPS_ADMIN_ACTION) {
			contentPanel.addTab("UPS Admin", new TabInfo(), TabType.TAB_UPSADMIN);
		} else if(type == ToolActionType.CS_ADMIN_ACTION) {
			contentPanel.addTab("CS Admin", new TabInfo(), TabType.TAB_CSADMIN);
		} else if(type == ToolActionType.OB_EXPORT_ACTION) {
			contentPanel.addTab("OB Export", new TabInfo(), TabType.TAB_OBEXPORT);
		} else if(type == ToolActionType.OB_IMPORT_ACTION) {
			contentPanel.addTab("OB Import", new TabInfo(), TabType.TAB_OBIMPORT);
		}
	}

}
