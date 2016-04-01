package com.ecnu.ob.gui.component;

import java.awt.FlowLayout;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.ecnu.ob.gui.MainFrame;

@SuppressWarnings("serial")
public class ConnectionDialog extends JDialog {
	
	private JTextField txtIp;
	
	private JTextField txtPort;
	
	private JTextField txtUser;
	
	private JPasswordField txtPasswd;
	
	private JTextField sshUser;
	
	private JPasswordField sshPasswd;
	
	private JButton connectBtn;
	
	private JTextField txtStatus;
	
	public ConnectionDialog(JFrame parent) {
		super(parent);
		this.setTitle("Connection to Oceanbase");
		this.setSize(440, 370);
		this.setLocationRelativeTo(null);
		
		getContentPane().setLayout(null);
		JPanel rowPanel = new JPanel();
		rowPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		rowPanel.setBounds(50, 20, 330, 320);
		
		JPanel row1 = new JPanel();
		row1.add(new JLabel("MergeServer IP :"));
		
		txtIp = new JTextField();
		txtIp.setText(MainFrame.DEFAULT_IP);
		txtIp.setColumns(17);
		row1.add(txtIp);
		
		JPanel row2 = new JPanel();
		row2.add(new JLabel("Port :"));
		
		txtPort = new JTextField();
		txtPort.setText(MainFrame.DEFAULT_PORT);
		txtPort.setColumns(17);
		row2.add(txtPort);
		//mysql
		JPanel row3 = new JPanel();
		row3.add(new JLabel("User :"));
		
		txtUser = new JTextField();
		txtUser.setColumns(17);
		txtUser.setText("admin");
		row3.add(txtUser);
		
		JPanel row4 = new JPanel();
		row4.add(new JLabel("Password :"));
		
		txtPasswd = new JPasswordField();
		txtPasswd.setText("admin");
		txtPasswd.setColumns(17);
		row4.add(txtPasswd);
		//ssh
		JPanel row5 = new JPanel();
		row5.add(new JLabel("SSH User :"));
		
		sshUser = new JTextField();
		sshUser.setColumns(17);
		sshUser.setText(MainFrame.DEFAULT_SSH_ADMIN);
		row5.add(sshUser);
		
		JPanel row6 = new JPanel();
		row6.add(new JLabel("SSH Password :"));
		
		sshPasswd = new JPasswordField();
		sshPasswd.setText(MainFrame.DEFAULT_SSH_PASSWD);
		sshPasswd.setColumns(17);
		row6.add(sshPasswd);
		
		JPanel row7 = new JPanel();
		row7.add(new JLabel("Status :"));
		
		txtStatus = new JTextField();
		txtStatus.setBackground(UIManager.getColor("Label.background"));
		txtStatus.setColumns(17);
		setStatusToDisconnected();
		txtStatus.setBorder(BorderFactory.createLineBorder(txtStatus.getBackground()));
		row7.add(txtStatus);
		
		JPanel row8 = new JPanel();
		connectBtn = new JButton();
		row8.add(connectBtn);
		
		rowPanel.add(row1);
		rowPanel.add(row2);
		rowPanel.add(row3);
		rowPanel.add(row4);
		rowPanel.add(row5);
		rowPanel.add(row6);
		rowPanel.add(row7);
		rowPanel.add(row8);
		getContentPane().add(rowPanel);
	}
	
	public String getIp() {
		return txtIp.getText().trim();
	}
	
	public String getPort() {
		return txtPort.getText().trim();
	}
	
	public String getUser() {
		return txtUser.getText().trim();
	}
	
	public String getPasswd() {
		return String.valueOf(txtPasswd.getPassword());
	}
	
	public String getSSHUser() {
		return sshUser.getText().trim();
	}
	
	public String getSSHPasswd() {
		return String.valueOf(sshPasswd.getPassword());
	}
	
	public void setConntectBtnAction(AbstractAction action) {
		connectBtn.setAction(action);
	}
	
	public void setStatusToConnecting() {
		txtStatus.setText("connecting...");
	}
	
	public void setStatusToDisconnected() {
		txtStatus.setText("disconnected");
	}
	
	public void setStatusToConnected() {
		txtStatus.setText("connected");
	}
}
