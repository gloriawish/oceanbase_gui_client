package com.ecnu.ob.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.ecnu.ob.gui.MainFrame;
import com.ecnu.ob.gui.ObContentPanel;
import com.ecnu.ob.gui.component.tab.RsAdminTabPanel;
import com.ecnu.ob.jdbc.RemoteConnection;
import com.ecnu.ob.jdbc.RemoteConnectionHelper;
import com.ecnu.ob.manager.ClusterManager;
import com.ecnu.ob.model.ClusterInfo;
import com.ecnu.ob.utils.Pair;

@SuppressWarnings("serial")
public class RsAdminAction extends AbstractAction {
	
	private ObContentPanel contentPanel;
	
	private RemoteConnection remoteShell;
	
	public RsAdminAction(ObContentPanel contentPanel) {
		putValue(NAME, "Execute");
		putValue(SHORT_DESCRIPTION, "use rs_admin to manager the clusters");
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
			String cmd = getQueryTabPanel().getCMD();
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