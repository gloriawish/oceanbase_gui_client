package com.ecnu.ob.gui.component.tab;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.ecnu.ob.gui.MainFrame;
import com.ecnu.ob.main.Main;
import com.ecnu.ob.manager.ClusterManager;
import com.ecnu.ob.model.ActionType;
import com.ecnu.ob.model.ClusterInfo;
import com.ecnu.ob.model.DataBaseInfo;
import com.ecnu.ob.model.ObConstant;
import com.ecnu.ob.model.RsAdminCmd;
import com.ecnu.ob.model.ServerInfo;
import com.ecnu.ob.model.TabInfo;
import com.ecnu.ob.model.TableInfo;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ObExportTabPanel extends BaseTabPanel {
	
	private JTextArea textAreaResult;
	
	private JTextArea cmdTextArea;
	
	private ClusterInfo master;
	public JTextField logFile;
	public JTextField queueSize;
	public JTextField dataFile;
	public JTextField logLevel;
	public JTextField cloumnDelima;
	public JTextField recordDelima;
	public JTextField selectSql;
	public JTextField maxFileSize;
	public JTextField maxRecordNumber;
	public JTextField badFile;
	public JTextField masterPercent;
	public JTextField slavePercent;
	public JTextField limitTime;
	public JTextField maxLimitTime;
	public JTextField consumer;
	public JComboBox database;
	
	public JComboBox table;
	
	private JButton btnExport;
	public JTextField confFile;
	/**
	 * Create the panel.
	 */
	public ObExportTabPanel(String title,TabInfo tabInfo) {
		super(title);
		setLayout(null);
		
		//TODO 初始化数据库
		
		master = ClusterManager.getMasterCluster();
		final JComboBox databaseCombox = new JComboBox();
		database = databaseCombox;
		final List<DataBaseInfo> dbs = ClusterManager.getAllDatabase(Main.LMSIPPORT, Main.USERNAME, Main.USERPASS);
		databaseCombox.addItem("NONE");
		if(dbs != null) {
			for(DataBaseInfo db: dbs) {
				databaseCombox.addItem(db.getDb_name());
			}
		}
		
		final JComboBox tableCombox = new JComboBox();
		table = tableCombox;
		tableCombox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			
				//String confPath = "/home/zhujun/data/ob_export/tpcc_export_config";
				//String exportPath = "/home/zhujun/data/ob_export/out_data";
				
				
				
			
			}
		});
		tableCombox.setBounds(114, 92, 302, 30);
		
		
		databaseCombox.setSelectedItem("NONE");
		databaseCombox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				//选中的时候事件
				if(e.getStateChange() == ItemEvent.SELECTED) {

					tableCombox.removeAllItems();
					String dbName = e.getItem().toString();
					
					if(databaseCombox.getSelectedIndex()-1 != -1) {
						DataBaseInfo dbInfo = dbs.get(databaseCombox.getSelectedIndex()-1);
						
						List<TableInfo> tableList = dbInfo.getTableList();
						
						for (TableInfo info : tableList) {
							tableCombox.addItem(info.getTable_name());
						}
					}
				}
			}
		});
		databaseCombox.setBounds(114, 38, 302, 30);
		add(databaseCombox);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 571, 1080, 209);
		add(scrollPane_1);
		
		this.textAreaResult = new JTextArea();
		scrollPane_1.setViewportView(textAreaResult);
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
		
		JLabel lblNewLabel = new JLabel("Database:");
		lblNewLabel.setBounds(20, 44, 66, 15);
		add(lblNewLabel);
		
		add(tableCombox);
		
		JLabel lblNewLabel_1 = new JLabel("Table:");
		lblNewLabel_1.setBounds(20, 98, 66, 15);
		add(lblNewLabel_1);
		
		JLabel lblLogFile = new JLabel("Log File:");
		lblLogFile.setBounds(20, 156, 66, 15);
		add(lblLogFile);
		
		logFile = new JTextField();
		logFile.setBounds(114, 151, 302, 30);
		add(logFile);
		logFile.setColumns(10);
		
		JLabel lblQueueSize = new JLabel("Queue Size:");
		lblQueueSize.setBounds(20, 216, 78, 15);
		add(lblQueueSize);
		
		queueSize = new JTextField();
		queueSize.setText("1000");
		queueSize.setColumns(10);
		queueSize.setBounds(114, 209, 302, 30);
		add(queueSize);
		
		JLabel lblDataFile = new JLabel("Data File:");
		lblDataFile.setBounds(20, 276, 72, 15);
		add(lblDataFile);
		
		dataFile = new JTextField();
		dataFile.setColumns(10);
		dataFile.setBounds(114, 269, 302, 30);
		add(dataFile);
		
		JLabel lblLogLevel = new JLabel("Log Level:");
		lblLogLevel.setBounds(20, 335, 72, 15);
		add(lblLogLevel);
		
		logLevel = new JTextField();
		logLevel.setText("INFO");
		logLevel.setColumns(10);
		logLevel.setBounds(114, 328, 302, 30);
		add(logLevel);
		
		JLabel lblCloumnDelimer = new JLabel("Cloumn Delima:");
		lblCloumnDelimer.setBounds(20, 393, 90, 15);
		add(lblCloumnDelimer);
		
		cloumnDelima = new JTextField();
		cloumnDelima.setText("15");
		cloumnDelima.setColumns(10);
		cloumnDelima.setBounds(114, 386, 302, 30);
		add(cloumnDelima);
		
		JLabel lblRecordDelima = new JLabel("Record Delima:");
		lblRecordDelima.setBounds(561, 105, 109, 15);
		add(lblRecordDelima);
		
		recordDelima = new JTextField();
		recordDelima.setText("10");
		recordDelima.setColumns(10);
		recordDelima.setBounds(680, 99, 302, 30);
		add(recordDelima);
		
		JLabel lblSelectSql = new JLabel("Select Statement:");
		lblSelectSql.setBounds(561, 159, 109, 15);
		add(lblSelectSql);
		
		selectSql = new JTextField();
		selectSql.setColumns(10);
		selectSql.setBounds(680, 153, 302, 30);
		add(selectSql);
		
		JLabel lblMaxFileSize = new JLabel("Max File Size:");
		lblMaxFileSize.setBounds(561, 217, 109, 15);
		add(lblMaxFileSize);
		
		maxFileSize = new JTextField();
		maxFileSize.setText("1024");
		maxFileSize.setColumns(10);
		maxFileSize.setBounds(680, 211, 302, 30);
		add(maxFileSize);
		
		JLabel lblMaxRecordNumber = new JLabel("Max Record Number:");
		lblMaxRecordNumber.setBounds(561, 275, 109, 15);
		add(lblMaxRecordNumber);
		
		maxRecordNumber = new JTextField();
		maxRecordNumber.setText("10000000");
		maxRecordNumber.setColumns(10);
		maxRecordNumber.setBounds(680, 269, 302, 30);
		add(maxRecordNumber);
		
		badFile = new JTextField();
		badFile.setColumns(10);
		badFile.setBounds(680, 329, 302, 30);
		add(badFile);
		
		JLabel lblBadFile = new JLabel("Bad File:");
		lblBadFile.setBounds(561, 335, 109, 15);
		add(lblBadFile);
		
		masterPercent = new JTextField();
		masterPercent.setText("50");
		masterPercent.setColumns(10);
		masterPercent.setBounds(680, 388, 302, 30);
		add(masterPercent);
		
		JLabel lblMasterPercent = new JLabel("Master Percent:");
		lblMasterPercent.setBounds(561, 394, 109, 15);
		add(lblMasterPercent);
		
		slavePercent = new JTextField();
		slavePercent.setText("50");
		slavePercent.setColumns(10);
		slavePercent.setBounds(680, 446, 302, 30);
		add(slavePercent);
		
		JLabel lblSlavePercent = new JLabel("Slave Percent:");
		lblSlavePercent.setBounds(561, 452, 109, 15);
		add(lblSlavePercent);
		
		limitTime = new JTextField();
		limitTime.setText("20");
		limitTime.setColumns(10);
		limitTime.setBounds(114, 447, 302, 30);
		add(limitTime);
		
		JLabel lblLimitTime = new JLabel("Limit Time:");
		lblLimitTime.setBounds(20, 453, 90, 15);
		add(lblLimitTime);
		
		maxLimitTime = new JTextField();
		maxLimitTime.setText("25");
		maxLimitTime.setColumns(10);
		maxLimitTime.setBounds(680, 507, 302, 30);
		add(maxLimitTime);
		
		JLabel lblMaxLimitTime = new JLabel("Max Limit Time:");
		lblMaxLimitTime.setBounds(561, 513, 109, 15);
		add(lblMaxLimitTime);
		
		consumer = new JTextField();
		consumer.setText("3");
		consumer.setColumns(10);
		consumer.setBounds(114, 507, 302, 30);
		add(consumer);
		
		JLabel lblConstum = new JLabel("Consumer:");
		lblConstum.setBounds(20, 513, 90, 15);
		add(lblConstum);
		
		btnExport = new JButton("Export");
//		btnExport.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				MainFrame.alert("notice");
//				
//			}
//		});
		btnExport.setBounds(426, 522, 109, 39);
		add(btnExport);
		
		JLabel lblConfigure = new JLabel("Configure:");
		lblConfigure.setBounds(561, 44, 109, 15);
		add(lblConfigure);
		
		confFile = new JTextField();
		confFile.setColumns(10);
		confFile.setBounds(680, 38, 302, 30);
		add(confFile);
	}
	
	public void setResult(String result) {
		this.textAreaResult.setText(result);
	}
	@Override
	public void setAction(AbstractAction action, ActionType actionType) {
		// TODO Auto-generated method stub
		if(btnExport!=null) {
			btnExport.setAction(action);
		}
	}
}
