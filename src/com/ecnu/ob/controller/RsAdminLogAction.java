package com.ecnu.ob.controller;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;

import com.ecnu.ob.gui.MainFrame;
import com.ecnu.ob.gui.ObContentPanel;
import com.ecnu.ob.gui.component.tab.RsAdminTabPanel;
import com.ecnu.ob.jdbc.RemoteConnection;
import com.ecnu.ob.jdbc.RemoteConnectionHelper;
import com.ecnu.ob.main.Main;
import com.ecnu.ob.manager.ClusterManager;
import com.ecnu.ob.model.ClusterInfo;
import com.ecnu.ob.utils.Pair;

@SuppressWarnings("serial")
public class RsAdminLogAction extends AbstractAction {
	
	private ObContentPanel contentPanel;
	
	private RemoteConnection remoteShell;
	
	public RsAdminLogAction(ObContentPanel contentPanel) {
		putValue(NAME, "Rs Admin Log");
		putValue(SHORT_DESCRIPTION, "use rs_admin to manager the clusters");
		this.contentPanel = contentPanel;
		//rootServer上面执行rs_admin
		List<ClusterInfo> clusters = ClusterManager.getAllCluster(Main.LMSIPPORT, Main.USERNAME, Main.USERPASS);
		ClusterInfo master = null;
		//find the master cluster
		for (ClusterInfo clusterInfo : clusters) {
			if(clusterInfo.getCluster_role() == 1) {//主机群
				master = clusterInfo;
				break;
			}
		}
		if(master != null) {
		  String rootServerIp = master.getRootServer().get(0).getSvr_ip();
		  //remoteShell = new RemoteConnection(rootServerIp, MainFrame.DEFAULT_SSH_ADMIN, MainFrame.DEFAULT_SSH_PASSWD);
		  remoteShell = RemoteConnectionHelper.getRemoteConnection(rootServerIp, MainFrame.DEFAULT_SSH_ADMIN, MainFrame.DEFAULT_SSH_PASSWD);
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(remoteShell !=null && remoteShell.isConnectSuccess()) {
			String cmd = "tail -n 100 rs_admin.log";
			Pair<Boolean,String> result = remoteShell.executeFormat(cmd, false);
			if(result.first) {
				
				getQueryTabPanel().setResult(result.second);
				
			} else {
				MainFrame.alert("remote shell execute error.");
			}
			
		} else {
			MainFrame.alert("remote shell connection error.");
		}
		
	}
	
	private RsAdminTabPanel getQueryTabPanel() {
		return (RsAdminTabPanel)contentPanel.getCurrentTabPanel();
	}

}