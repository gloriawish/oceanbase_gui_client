package com.ecnu.ob.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;


import com.ecnu.ob.gui.MainFrame;
import com.ecnu.ob.gui.ObContentPanel;
import com.ecnu.ob.gui.component.tab.ObExportTabPanel;
import com.ecnu.ob.gui.component.tab.RsAdminTabPanel;
import com.ecnu.ob.jdbc.RemoteConnection;
import com.ecnu.ob.jdbc.RemoteConnectionHelper;
import com.ecnu.ob.main.Main;
import com.ecnu.ob.manager.ClusterManager;
import com.ecnu.ob.model.ClusterInfo;
import com.ecnu.ob.utils.Pair;

@SuppressWarnings("serial")
public class ObExportAction extends AbstractAction {
	
	
	private ObContentPanel contentPanel;
	
	private RemoteConnection remoteShell;
	
	public ObExportAction(ObContentPanel contentPanel) {
		putValue(NAME, "Export");
		//putValue(SHORT_DESCRIPTION, "use rs_admin to manager the clusters");
		this.contentPanel = contentPanel;
		//rootServer…œ√Ê÷¥––rs_admin
		ClusterInfo master = ClusterManager.getMasterCluster();
		if(master != null) {
		  String rootServerIp = master.getRootServer().get(0).getSvr_ip();
		  remoteShell = RemoteConnectionHelper.getRemoteConnection(rootServerIp, MainFrame.DEFAULT_SSH_ADMIN, MainFrame.DEFAULT_SSH_PASSWD);
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(remoteShell !=null && remoteShell.isConnectSuccess()) {
			String dbName = null;
			if(getQueryTabPanel().database.getSelectedItem()!=null) {
				dbName = getQueryTabPanel().database.getSelectedItem().toString();
			}
			String tableName = null;
			if(getQueryTabPanel().table.getSelectedItem()!=null) {
				tableName = getQueryTabPanel().table.getSelectedItem().toString();
			}
			String logFile = getQueryTabPanel().logFile.getText();
			
			String queueSize = getQueryTabPanel().queueSize.getText();
			
			String dataFile = getQueryTabPanel().dataFile.getText();
			
			String logLevel = getQueryTabPanel().logLevel.getText();
			
			String clomunDelima = getQueryTabPanel().cloumnDelima.getText();
			
			String recordDelima = getQueryTabPanel().recordDelima.getText();
			
			String selectStatement = getQueryTabPanel().selectSql.getText();
			
			String maxFileSize = getQueryTabPanel().maxFileSize.getText();
			
			String maxRecordNumber = getQueryTabPanel().maxRecordNumber.getText();
			
			String badFile = getQueryTabPanel().badFile.getText();
			
			String masterPercent = getQueryTabPanel().masterPercent.getText();
			
			String slavePercent = getQueryTabPanel().slavePercent.getText();
			
			String limitTime = getQueryTabPanel().limitTime.getText();
			
			String maxLimitTime = getQueryTabPanel().maxLimitTime.getText();
			
			String consumer = getQueryTabPanel().consumer.getText();
			
			String confile = getQueryTabPanel().confFile.getText();
			
			if(dbName == null) {
				MainFrame.alert("please select database");
				return;
			}
			if(tableName == null) {
				MainFrame.alert("please select table");
				return;
			}
			
			if(logFile.length() == 0) {
				MainFrame.alert("please input log file");
				return;
			}
			
			if(confile.length() == 0) {
				MainFrame.alert("please input configure file path.");
				return;
			}
			
			if(dataFile.length() == 0) {
				MainFrame.alert("please input data file path.");
				return;
			}
		
			
			String cmd = "ob_export";
			if(confile.length() != 0) {
				cmd += " -c " + confile;
			} 
			
			cmd += " -t " + dbName + "." + tableName;
			
			//root server ip 
			ClusterInfo master = ClusterManager.getMasterCluster();
			if(master != null) {
			  String rootServerIp = master.getRootServer().get(0).getSvr_ip();
			  cmd += " -h " + rootServerIp;
			}
			
			cmd += " -l " + logFile;
			
			if(!queueSize.equals("1000")) {
				cmd += " -q " + queueSize;
			}
			
			if(!logLevel.equals("INFO")) {
				cmd += " -g " + logLevel;
			}
			
			if(!clomunDelima.equals("15")) {
				cmd += " --delima " + clomunDelima;
			}
			
			if(!recordDelima.equals("10")) {
				cmd += " --rec_delima " + recordDelima;
			}
			
			if(selectStatement.length() != 0) {
				cmd += " --select_statement " + selectStatement;
			}
			
			if(!maxFileSize.equals("1024")) {
				cmd += " --maxfilesize " + maxFileSize;
			}
			
			if(!maxRecordNumber.equals("10000000")) {
				cmd += " --maxrecordnum " + maxRecordNumber;
			}
			
			
		} else {
			MainFrame.alert("remote shell connection error.");
		}
	}
	
	private ObExportTabPanel getQueryTabPanel() {
		return (ObExportTabPanel)contentPanel.getCurrentTabPanel();
	}

}