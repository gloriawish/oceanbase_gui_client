package com.ecnu.ob.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.ecnu.ob.gui.MainFrame;
import com.ecnu.ob.gui.ObContentPanel;
import com.ecnu.ob.gui.ObNavPanel;
import com.ecnu.ob.gui.component.tab.BaseTabPanel.TabType;
import com.ecnu.ob.gui.component.tab.QueryTabPanel.QueryTabType;
import com.ecnu.ob.model.TabInfo;

@SuppressWarnings("serial")
public class OpenQueryAction extends AbstractAction {
	
	private ObNavPanel navPanel;
	
	private ObContentPanel contentPanel;
	
	public OpenQueryAction(ObNavPanel navPanel, ObContentPanel contentPanel) {
		putValue(NAME, "Query");
		putValue(SHORT_DESCRIPTION, "Query SQL");
		this.navPanel = navPanel;
		this.contentPanel = contentPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		contentPanel.addTab("Query", new TabInfo(null, null, true, MainFrame.DEFAULT_QUERY, null, 
				navPanel.getDbNameList(), QueryTabType.EMPTY), TabType.TAB_QEURY);
	}

}
