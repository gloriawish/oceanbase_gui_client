package com.ecnu.ob.gui;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ObStatusPanel extends JPanel {

	public ObStatusPanel() {
		JLabel lblNewLabel = new JLabel("status");
		add(lblNewLabel);
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
	}
}
