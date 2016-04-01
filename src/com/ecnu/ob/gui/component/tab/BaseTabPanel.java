package com.ecnu.ob.gui.component.tab;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ecnu.ob.model.ActionType;

@SuppressWarnings("serial")
public abstract class BaseTabPanel extends JPanel {
	
	public enum TabType {
		TAB_QEURY,
		TAB_SERVER,
		TAB_RSADMIN,
		
		TAB_UPSADMIN,
		TAB_CSADMIN,
		TAB_OBEXPORT,
		TAB_OBIMPORT,
	}
	
	protected TabType type;
	
	protected JPanel titlePanel = new JPanel();
	
	protected JLabel closeBtn = new JLabel("x");
	
	protected JLabel titleLabel;
	
	public BaseTabPanel(String title) {
		titleLabel = new JLabel(title);
		
		titlePanel.setOpaque(false);
		titleLabel.setOpaque(false);
		closeBtn.setPreferredSize(new Dimension(15, 15));
		closeBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		closeBtn.setForeground(Color.GRAY);
		closeBtn.setHorizontalAlignment(JLabel.CENTER);
		
		titleLabel.setHorizontalAlignment(JLabel.LEFT);
		titlePanel.add(titleLabel);
		titlePanel.add(closeBtn);
	}
	
	public void setTabTitle(String title) {
		if(titleLabel != null) {
			titleLabel.setText(title);
		}
	}
	
	public String getTabTitle() {
		if(titleLabel != null) {
			return titleLabel.getText();
		}
		return null;
	}

	public TabType getType() {
		return type;
	}
	
	public JPanel getTitlePanel() {
		return titlePanel;
	}
	
	public JLabel getCloseBtn() {
		return closeBtn;
	}
	
	public abstract void setAction(AbstractAction action, ActionType actionType);
	
}
