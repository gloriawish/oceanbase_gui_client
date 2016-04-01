package com.ecnu.ob.gui.component;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;

import com.ecnu.ob.gui.component.tab.BaseTabPanel;
import com.ecnu.ob.manager.ClusterManager;
import com.ecnu.ob.model.ActionType;
import com.ecnu.ob.model.ClusterInfo;
import com.ecnu.ob.model.ServerType;
import com.ecnu.ob.model.TabInfo;
import com.ecnu.ob.utils.CsAdminTool;

@SuppressWarnings("serial")
public class ServerInfoDetailPanel extends BaseTabPanel {

	/**
	 * Create the panel.
	 */
	public ServerInfoDetailPanel(String title,TabInfo tabInfo) {
		super(title);
		setBackground(SystemColor.control);
		
		setLayout(null);
		
		JLabel lblClusterId = new JLabel("Cluster ID:");
		lblClusterId.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblClusterId.setBounds(50, 40, 136, 15);
		add(lblClusterId);
		
		JLabel lblServerType = new JLabel("Server Type:");
		lblServerType.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblServerType.setBounds(50, 65, 136, 15);
		add(lblServerType);
		
		JLabel lblServerIp = new JLabel("Server IP:");
		lblServerIp.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblServerIp.setBounds(50, 90, 136, 15);
		add(lblServerIp);
		
		JLabel lblServerPort = new JLabel("Server Port:");
		lblServerPort.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblServerPort.setBounds(50, 117, 136, 15);
		add(lblServerPort);
		
		JLabel lblNewLabel = new JLabel("Inner Port:");
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblNewLabel.setBounds(50, 142, 136, 15);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Server Role:");
		lblNewLabel_1.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(50, 167, 136, 15);
		add(lblNewLabel_1);
		
		JLabel lblServerVersion = new JLabel("Server Version:");
		lblServerVersion.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblServerVersion.setBounds(50, 192, 136, 15);
		add(lblServerVersion);
		
		JLabel obClusterID = new JLabel(tabInfo.serverInfo.getCluster_id() + "");
		obClusterID.setFont(new Font("宋体", Font.PLAIN, 14));
		obClusterID.setBounds(212, 40, 200, 15);
		add(obClusterID);
		
		JLabel obServerType = new JLabel(tabInfo.serverInfo.getSvr_type().toString());
		obServerType.setFont(new Font("宋体", Font.PLAIN, 14));
		obServerType.setBounds(212, 65, 200, 15);
		add(obServerType);
		
		JLabel obServerIP = new JLabel(tabInfo.serverInfo.getSvr_ip());
		obServerIP.setFont(new Font("宋体", Font.PLAIN, 14));
		obServerIP.setBounds(212, 90, 200, 15);
		add(obServerIP);
		
		JLabel obServerPort = new JLabel(tabInfo.serverInfo.getSvr_port() + "");
		obServerPort.setFont(new Font("宋体", Font.PLAIN, 14));
		obServerPort.setBounds(212, 117, 200, 15);
		add(obServerPort);
		
		JLabel obInnerPort = new JLabel(tabInfo.serverInfo.getInner_port() + "");
		obInnerPort.setFont(new Font("宋体", Font.PLAIN, 14));
		obInnerPort.setBounds(212, 142, 200, 15);
		add(obInnerPort);
		
		JLabel obServerRole = new JLabel(tabInfo.serverInfo.getSvr_role() + "");
		obServerRole.setFont(new Font("宋体", Font.PLAIN, 14));
		obServerRole.setBounds(212, 167, 200, 15);
		add(obServerRole);
		
		JLabel obServerVersion = new JLabel(tabInfo.serverInfo.getSvr_version());
		obServerVersion.setFont(new Font("宋体", Font.PLAIN, 14));
		obServerVersion.setBounds(212, 192, 300, 15);
		add(obServerVersion);
		
		
		
		ClusterInfo master = ClusterManager.getMasterCluster();
		String rootServerIp = master.getRootServer().get(0).getSvr_ip();
		
		JList list = new JList();
		list.setVisibleRowCount(10);
		list.setFont(new Font("宋体", Font.PLAIN, 14));
		list.setBackground(SystemColor.control);
		if(tabInfo.serverInfo.getSvr_type() == ServerType.CHUNKSERVER) {
			final CsAdminTool csAdmin = new CsAdminTool(rootServerIp, tabInfo.serverInfo);
			list.setModel(new AbstractListModel() {
				String[] values = csAdmin.getDiskInfo();
				public int getSize() {
					return values.length;
				}
				public Object getElementAt(int index) {
					return values[index];
				}
			});
		}
		list.setBounds(50, 260, 362, 213);
		
		add(list);
		if(tabInfo.serverInfo.getSvr_type() == ServerType.CHUNKSERVER) {
			JLabel lblDiskStatus = new JLabel("Disk Status:");
			lblDiskStatus.setFont(new Font("Consolas", Font.PLAIN, 14));
			lblDiskStatus.setBounds(50, 236, 136, 15);
			add(lblDiskStatus);
		}
		
	}

	@Override
	public void setAction(AbstractAction action, ActionType actionType) {
		// TODO Auto-generated method stub
		
	}
}
