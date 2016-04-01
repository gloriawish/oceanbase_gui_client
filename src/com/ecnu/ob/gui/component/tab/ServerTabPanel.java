package com.ecnu.ob.gui.component.tab;

import javax.swing.AbstractAction;

import com.ecnu.ob.model.ActionType;


@SuppressWarnings("serial")
public class ServerTabPanel extends BaseTabPanel {
	
	public ServerTabPanel(String title) {
		super(title);
		this.type = TabType.TAB_SERVER;
	}

	@Override
	public void setAction(AbstractAction action, ActionType actionType) {
		// TODO Auto-generated method stub
		
	}
	
}
