package com.ecnu.ob.gui.component.tab;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.ecnu.ob.manager.ClusterManager;
import com.ecnu.ob.model.ActionType;
import com.ecnu.ob.model.ClusterInfo;
import com.ecnu.ob.model.ObConstant;
import com.ecnu.ob.model.RsAdminCmd;
import com.ecnu.ob.model.ServerInfo;
import com.ecnu.ob.model.TabInfo;

@SuppressWarnings("serial")
public class CsAdminTabPanel extends BaseTabPanel {

	//private List<ClusterInfo> clusters;
	private JButton btnExecuteCsAdmin;
	
	private JButton btnUpsAdminLog;
	
	private JTextArea textAreaResult;
	
	private JTextArea cmdTextArea;
	
	private ClusterInfo master;
	/**
	 * Create the panel.
	 */
	public CsAdminTabPanel(String title,TabInfo tabInfo) {
		super(title);
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 1080, 120);
		add(scrollPane);
		
		final JTextArea textAreaCmd = new JTextArea();
		cmdTextArea = textAreaCmd;
		textAreaCmd.setFont(new Font("Consolas", Font.PLAIN, 14));
		scrollPane.setViewportView(textAreaCmd);
		textAreaCmd.setRows(6);
		textAreaCmd.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		master = ClusterManager.getMasterCluster();
		JComboBox comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				//选中的时候事件
				if(e.getStateChange() == ItemEvent.SELECTED) {

					RsAdminCmd cmd = (RsAdminCmd) e.getItem();
					switch (cmd) {
					case BOOT_STARP:
						textAreaCmd.setText(buildCmd(ObConstant.BOOT_STARP));
						break;
					case ALL_SERVER:
						textAreaCmd.setText(buildCmd(ObConstant.ALL_SERVER));
						break;
					case CHANGE_UPS_MASTER:
						textAreaCmd.setText(buildCmd(ObConstant.CHANGE_UPS_MASTER));
						break;
					case CLEAN_ROOT_TABLE:
						textAreaCmd.setText(buildCmd(ObConstant.CLEAN_ROOT_TABLE));
						break;
//					case CREATE_TABLET:
//						textAreaCmd.setText(buildCmd(ObConstant.CREATE_TABLET));
//						break;
					case DUMP_ROOT_TABLE:
						textAreaCmd.setText(buildCmd(ObConstant.DUMP_ROOT_TABLE));
						break;
					case DUMP_SERVER:
						textAreaCmd.setText(buildCmd(ObConstant.DUMP_SERVER));
						break;
					case DUMP_UNUSUAL_TABLETS:
						textAreaCmd.setText(buildCmd(ObConstant.DUMP_UNUSUAL_TABLETS));
						break;
					case FROZEN_VERSION:
						textAreaCmd.setText(buildCmd(ObConstant.FROZEN_VERSION));
						break;
//					case MAJOR_FREEZE:
//						textAreaCmd.setText(buildCmd(ObConstant.MAJOR_FREEZE));
//						break;
					case MERGE:
						textAreaCmd.setText(buildCmd(ObConstant.MERGE));
						break;
//					case MINOR_FREEZE:
//						textAreaCmd.setText(buildCmd(ObConstant.MINOR_FREEZE));
//						break;
					case REFRESH_SCHEMA:
						textAreaCmd.setText(buildCmd(ObConstant.REFRESH_SCHEMA));
						break;
					case SET_OBI_ROLE_MASTER:
						textAreaCmd.setText(buildCmd(ObConstant.SET_OBI_ROLE_MASTER));
						break;
					case SET_OBI_ROLE_SLAVE:
						textAreaCmd.setText(buildCmd(ObConstant.SET_OBI_ROLE_SLAVE));
						break;
					default:
						textAreaCmd.setText("");
						break;
					}
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(RsAdminCmd.values()));
		comboBox.setBounds(10, 140, 200, 30);
		add(comboBox);
		
		this.btnExecuteCsAdmin = new JButton("Execute");
		this.btnExecuteCsAdmin.setText("Execute");
//		btnExecuteRsAdmin.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				System.out.println("执行");
//				
//			}
//		});
		btnExecuteCsAdmin.setBounds(220, 140, 93, 30);
		add(btnExecuteCsAdmin);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 180, 1080, 600);
		add(scrollPane_1);
		
		this.textAreaResult = new JTextArea();
		scrollPane_1.setViewportView(textAreaResult);
		
		JButton btnNewButton = new JButton("Clear");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaCmd.setText("");
				textAreaResult.setText("");
			}
		});
		btnNewButton.setBounds(449, 140, 93, 30);
		add(btnNewButton);
		
		JLabel lblRs = new JLabel("RS:");
		lblRs.setBounds(552, 148, 24, 15);
		add(lblRs);
		String rootServerIp = "";
		//String updateServerIp = "";
		if(master != null) {
			rootServerIp = master.getRootServer().get(0).getSvr_ip();
			/*
			ServerInfo updateServer = null;
			  for (ServerInfo serverInfo : master.getUpdateServer()) {
				  if(serverInfo.getSvr_role() == 1) {
					  updateServer = serverInfo;
				  }
			  }
			 updateServerIp = updateServer.getSvr_ip();
			 */
		}
		JLabel obRootServerIp = new JLabel(rootServerIp);
		obRootServerIp.setForeground(Color.BLUE);
		obRootServerIp.setFont(new Font("Consolas", Font.PLAIN, 12));
		obRootServerIp.setBounds(572, 149, 100, 15);
		add(obRootServerIp);
		
		btnUpsAdminLog = new JButton("Cs Admin Log");
		btnUpsAdminLog.setBounds(323, 140, 120, 30);
		add(btnUpsAdminLog);
	}
	
	private String buildCmd(String cmd) {
		//<rootserver_ip> <rootserver_port> 
		//<chunkserver_ip> <chunkserver_port>
		//<updateserver_ip> <updateserver_port>
		
//		ClusterInfo master = null;
//		
//		//find the master cluster
//		for (ClusterInfo clusterInfo : clusters) {
//			if(clusterInfo.getCluster_role() == 1) {//主机群
//				master = clusterInfo;
//				break;
//			}
//		}
		
		if(master != null) {
		  String rootServerIp = master.getRootServer().get(0).getSvr_ip();
		  String rootServerPort = String.valueOf(master.getRootServer().get(0).getSvr_port());
		  
		  String chunkServerIp = master.getChunkServer().get(0).getSvr_ip();
		  String chunkServerPort = String.valueOf(master.getChunkServer().get(0).getSvr_port());
		 
		  ServerInfo updateServer = null;
		  for (ServerInfo serverInfo : master.getUpdateServer()) {
			  if(serverInfo.getSvr_role() == 1) {
				  updateServer = serverInfo;
			  }
		  }
		  
		  String updateServerIp = updateServer.getSvr_ip();
		  String updateServerPort = String.valueOf(updateServer.getSvr_port());
		  
		  cmd = cmd.replace("<rootserver_ip>", rootServerIp);
		  cmd = cmd.replace("<rootserver_port>", rootServerPort);
		  
		  cmd = cmd.replace("<chunkserver_ip>", chunkServerIp);
		  cmd = cmd.replace("<chunkserver_port>", chunkServerPort);
		  
		  cmd = cmd.replace("<updateserver_ip>", updateServerIp);
		  cmd = cmd.replace("<updateserver_port>", updateServerPort);
		  
		  cmd = cmd.replace("<ups_ip>", updateServerIp);
		  cmd = cmd.replace("<ups_port>", updateServerPort);
		
		}
		
		return cmd;
	}

	public void setResult(String result) {
		this.textAreaResult.setText(result);
	}
	public String getCMD() {
		return cmdTextArea.getText();
	}
	@Override
	public void setAction(AbstractAction action, ActionType actionType) {
		// TODO Auto-generated method stub
		if(this.btnExecuteCsAdmin != null && actionType == ActionType.EXECUTE_RS_ADMIN) {
			this.btnExecuteCsAdmin.setAction(action);
		}
		if(this.btnUpsAdminLog != null && actionType == ActionType.RS_ADMIN_LOG) {
			this.btnUpsAdminLog.setAction(action);
		}
	}
}
