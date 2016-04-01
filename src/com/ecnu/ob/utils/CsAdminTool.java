package com.ecnu.ob.utils;


import java.util.ArrayList;
import java.util.List;

import com.ecnu.ob.gui.MainFrame;
import com.ecnu.ob.jdbc.RemoteConnection;
import com.ecnu.ob.jdbc.RemoteConnectionHelper;
import com.ecnu.ob.model.ObConstant;
import com.ecnu.ob.model.ServerInfo;

public class CsAdminTool {
	
	
	private RemoteConnection remoteShell;
	private ServerInfo chunkServer;
	
	public CsAdminTool(String rootServerIp,ServerInfo chunkServer) {
		this.chunkServer = chunkServer;
		remoteShell = RemoteConnectionHelper.getRemoteConnection(rootServerIp, MainFrame.DEFAULT_SSH_ADMIN, MainFrame.DEFAULT_SSH_PASSWD);
			
	}

	
	public String[] getDiskInfo() {
		if(remoteShell.isConnectSuccess()) {
			List<String> diskList = new ArrayList<String>();
			String cmd = ObConstant.SHOW_DISK;
			
			cmd = cmd.replace("<chunkserver_ip>", chunkServer.getSvr_ip());
			cmd = cmd.replace("<chunkserver_port>", String.valueOf(chunkServer.getSvr_port()));
			  
			Pair<Boolean,String> result = remoteShell.executeFormat(cmd, false);
	
			String[] lines = result.second.split("\n");
			
			for (int i = 0; i < lines.length; i++) {
				String line = lines[i].substring(6);
				if(line.startsWith("disk:")) {
					diskList.add(line);
				}
			}
			
			String[] diskArray = new String[diskList.size()];
			
			for (int i = 0; i < diskList.size(); i++) {
				diskArray[i] = diskList.get(i);
			}
			
			return diskArray;
		} else {
			return new String[]{"remote shell error."};
		}
	}
}
