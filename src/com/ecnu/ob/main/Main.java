package com.ecnu.ob.main;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.ecnu.ob.gui.MainFrame;
import com.ecnu.ob.utils.CommonUtil;

public class Main {

	private static String version = "0.1.4-20160224";
	
	public static String LMSIPPORT = MainFrame.DEFAULT_IP + ":" + MainFrame.DEFAULT_PORT;
	
	public static String USERNAME = "admin";
	
	public static String USERPASS = "admin";
	
	public static boolean AUTHSTATUS = false;
	
	private static String CONFIG_FILE = "config/oceanbase.cfg";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		List<String> configList = loadConfigurationFile();
		if(configList.size() == 4) {
			MainFrame.DEFAULT_IP = configList.get(0);
			MainFrame.DEFAULT_PORT = configList.get(1);
			MainFrame.DEFAULT_SSH_ADMIN = configList.get(2);
			MainFrame.DEFAULT_SSH_PASSWD = configList.get(3);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame.getInstance(version);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static List<String> loadConfigurationFile() {
		List<String> configList = new ArrayList<String>();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(CONFIG_FILE)));
			String line;
			while(null != (line = bufferedReader.readLine())) {
				String[] lines = line.split("=");
				if(lines.length == 2) {
					String value = lines[1].trim();
					if(value.length() > 0) {
						configList.add(value);
					}
				}
			}
		} catch(Exception e) {
			//e.printStackTrace();
			// do nothing
		}
		return configList;
	}
	
	public static void writeConfigurationFile() {
		String dir = CONFIG_FILE.substring(0, CONFIG_FILE.lastIndexOf("/"));
		if(dir != null) {
			File fileDir = new File(dir);
			fileDir.mkdirs();
		}
		File file = new File(CONFIG_FILE);
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file, false);
			fw.write("default_ip=" + MainFrame.DEFAULT_IP + CommonUtil.SEPARATOR);
			fw.write("default_port=" + MainFrame.DEFAULT_PORT + CommonUtil.SEPARATOR);
			fw.write("default_ssh_user=" + MainFrame.DEFAULT_SSH_ADMIN + CommonUtil.SEPARATOR);
			fw.write("default_ssh_pass=" + MainFrame.DEFAULT_SSH_PASSWD + CommonUtil.SEPARATOR);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
