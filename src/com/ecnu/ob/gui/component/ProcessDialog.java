package com.ecnu.ob.gui.component;

import java.awt.FlowLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author zhujun
 *
 */
@SuppressWarnings("serial")
public class ProcessDialog extends JDialog {
	
	private String notice;
	public ProcessDialog(JFrame parent,String notice) {
		super(parent);
		this.notice = notice;
		
	}
	
	public void closeDialog() {
		this.setVisible(false);
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public void showDialog() {
		this.setTitle("系统提示");
		this.setSize(400, 100);
		this.setLocationRelativeTo(null);
		
		getContentPane().setLayout(null);
		JPanel rowPanel = new JPanel();
		rowPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		rowPanel.setBounds(50, 10, 220, 100);
		
		JPanel row1 = new JPanel();
		JLabel lblIp = new JLabel(notice);
		row1.add(lblIp);
		rowPanel.add(row1);
		getContentPane().add(rowPanel);
		
		this.setModal(true);
		this.setVisible(true);
	}
}
