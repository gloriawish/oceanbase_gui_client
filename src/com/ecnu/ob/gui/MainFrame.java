package com.ecnu.ob.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.ecnu.ob.controller.ConnectionAction;
import com.ecnu.ob.controller.NavgationTreeAction;
import com.ecnu.ob.controller.OpenQueryAction;
import com.ecnu.ob.controller.OpenToolTabPanelAction;
import com.ecnu.ob.controller.OpenToolTabPanelAction.ToolActionType;
import com.ecnu.ob.gui.component.ConnectionDialog;

public class MainFrame {
	
	public static boolean debug = false;
	
	public static int TIMEOUT = 3000;
	
	private static MainFrame mainFrame;
	
	public static JFrame frame;
	
	public static String DEFAULT_IP = "182.119.80.66";
	
	public static String DEFAULT_PORT = "62880";
	
	public static String DEFAULT_SSH_ADMIN = "admin";
	
	public static String DEFAULT_SSH_PASSWD = "admin";
	
	public static String DEFAULT_QUERY = "";
	
	public static MainFrame getInstance(String version) {
		if(mainFrame == null) {
			mainFrame = new MainFrame(version);
		}
		return mainFrame;
	}

	/**
	 * Create the application.
	 */
	public MainFrame(String version) {
		//Locale localeCN = new Locale("zh", "CN");
		//ResourceBundle resCN = ResourceBundle.getBundle("client", localeCN);
		//resCN.getString("");
		
		frame = new JFrame();
		MainFrame.initGlobalFont(new Font("MicroSoft YAHEI", Font.PLAIN, 13));
		frame.setTitle("Oceanbase Client V" + version);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("icon.png")));
		setFullScreem(frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container container = frame.getContentPane();
		/*********************   top  *********************/
		ObMenuBar menuBar = new ObMenuBar();
		frame.setJMenuBar(menuBar);
		
		final ConnectionDialog connDialog = new ConnectionDialog(frame);
		if(!debug) {
			connDialog.setVisible(true);
		}
		
		ObToolBar toolBar = new ObToolBar(connDialog);
		container.add(toolBar, BorderLayout.NORTH);
		
		/********************* center *********************/
		JSplitPane splitPane = new JSplitPane();
		
		ObContentPanel contentPanel = new ObContentPanel();
		ObNavPanel navPanel = new ObNavPanel();
		
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);
		splitPane.setLeftComponent(navPanel);
		splitPane.setRightComponent(contentPanel);
		splitPane.setDividerSize(1);
		splitPane.setDividerLocation(150);
		container.add(splitPane, BorderLayout.CENTER);
		
		/********************* bottom *********************/
		ObStatusPanel statusPanel = new ObStatusPanel();
		container.add(statusPanel, BorderLayout.SOUTH);
		
		frame.setVisible(true);
	
		/***************** register actions ***************/
		NavgationTreeAction navTreeAction = new NavgationTreeAction(navPanel, contentPanel);
		if(debug) {
			navTreeAction.loadData();
		}
		
		ConnectionAction connAction = new ConnectionAction(connDialog, navTreeAction);
		connDialog.setConntectBtnAction(connAction);
		
		OpenToolTabPanelAction rsAdminAction = new OpenToolTabPanelAction(contentPanel, ToolActionType.RS_ADMIN_ACTION);
		toolBar.addToolTabAction(rsAdminAction, ToolActionType.RS_ADMIN_ACTION);
		
		OpenToolTabPanelAction upsAdminAction = new OpenToolTabPanelAction(contentPanel, ToolActionType.UPS_ADMIN_ACTION);
		toolBar.addToolTabAction(upsAdminAction, ToolActionType.UPS_ADMIN_ACTION);
		
		OpenToolTabPanelAction csAdminAction = new OpenToolTabPanelAction(contentPanel, ToolActionType.CS_ADMIN_ACTION);
		toolBar.addToolTabAction(csAdminAction, ToolActionType.CS_ADMIN_ACTION);
		
		OpenToolTabPanelAction obExportAction = new OpenToolTabPanelAction(contentPanel, ToolActionType.OB_EXPORT_ACTION);
		toolBar.addToolTabAction(obExportAction, ToolActionType.OB_EXPORT_ACTION);
		
		OpenToolTabPanelAction obImportAction = new OpenToolTabPanelAction(contentPanel, ToolActionType.OB_IMPORT_ACTION);
		toolBar.addToolTabAction(obImportAction, ToolActionType.OB_IMPORT_ACTION);
		
		OpenQueryAction queryAction = new OpenQueryAction(navPanel, contentPanel);
		toolBar.setQueryAction(queryAction);
		
		//²Ëµ¥À¸ÊÂ¼þ
		List<ActionListener> actionList = new ArrayList<ActionListener>();
		actionList.add(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				connDialog.setVisible(true);
			}
		});
		actionList.add(rsAdminAction);
		actionList.add(upsAdminAction);
		actionList.add(csAdminAction);
		actionList.add(obExportAction);
		actionList.add(obImportAction);
		menuBar.addMenuBarAction(actionList);
	}
	
	private void setFullScreem(JFrame frame) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle bounds = new Rectangle(screenSize);
		Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());
		bounds.x += insets.left;
		bounds.y += insets.top;
		bounds.width -= insets.left + insets.right;
		bounds.height -= insets.top + insets.bottom;
		frame.setBounds(bounds);
	}
	
	private static void initGlobalFont(Font font) {
		FontUIResource fontRes = new FontUIResource(font);
		for(Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if(value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}
	
	public static void alert(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}

}
