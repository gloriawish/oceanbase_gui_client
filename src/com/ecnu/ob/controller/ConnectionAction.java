package com.ecnu.ob.controller;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;

import com.ecnu.ob.gui.MainFrame;
import com.ecnu.ob.gui.component.ConnectionDialog;
import com.ecnu.ob.main.Main;
import com.ecnu.ob.manager.ClusterManager;
import com.ecnu.ob.model.ClusterInfo;

@SuppressWarnings("serial")
public class ConnectionAction extends AbstractAction {

	private ConnectionDialog connDialog;
	
	private NavgationTreeAction navTreeAction;
	
	public ConnectionAction(ConnectionDialog connDialog, NavgationTreeAction navTreeAction) {
		putValue(NAME, "Connect");
		putValue(SHORT_DESCRIPTION, "Connect to oeanbase");
		this.connDialog = connDialog;
		this.navTreeAction = navTreeAction;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String ip = connDialog.getIp();
		String port = connDialog.getPort();
		String user = connDialog.getUser();
		String passwd = connDialog.getPasswd();
		List<ClusterInfo> clusters = ClusterManager.getAllCluster(ip + ":" + port, user, passwd);
		if(clusters != null) {
			connDialog.setStatusToConnecting();
			MainFrame.DEFAULT_IP = ip;
			MainFrame.DEFAULT_PORT = port;
			MainFrame.DEFAULT_SSH_ADMIN = connDialog.getSSHUser();
			MainFrame.DEFAULT_SSH_PASSWD = connDialog.getSSHPasswd();
			
			Main.LMSIPPORT = ip + ":" + port;
			Main.USERNAME = user;
			Main.USERPASS = passwd;
			Main.AUTHSTATUS = true;
			Main.writeConfigurationFile();
			//º”‘ÿ ˝æ›
			navTreeAction.loadData();
			connDialog.setStatusToConnected();
			connDialog.setVisible(false);
		} else {
			connDialog.setStatusToDisconnected();
			MainFrame.alert("can't connect to [" + ip + ":" + port + "]");
		}
	}
}
