package com.ecnu.ob.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

import com.ecnu.ob.gui.MainFrame;
import com.ecnu.ob.utils.Pair;

public class RemoteConnection {
	
	private Connection conn = null;
	
	private boolean isAuthenticated;
	
	ExecutorService executor = Executors.newSingleThreadExecutor();
	
	public RemoteConnection(String ip, String username, String password) {
		try {
			conn = new Connection(ip);
			conn.connect();
			isAuthenticated = conn.authenticateWithPassword(username, password);
			if(!isAuthenticated) {
				System.out.println("[ERROR] Connect to " + ip + "(USER:" + username + ", PASS:" + password + ") error!");
				
				MainFrame.alert("[ERROR] Connect to " + ip + "(USER:" + username + ", PASS:" + password + ") error!");
			}
		} catch (Exception e) {
			System.out.println("[ERROR] Connect to " + ip + " timeout!");
			e.printStackTrace();
		}
	}
	
	public boolean isConnectSuccess() {
		return isAuthenticated;
	}
	
	public Pair<Boolean, String> executeFormat(String cmd, boolean isCount) {
		boolean ret = true;
		String result = null;
		CountRunnable run = null;
		try {
			if(isAuthenticated) {
				Session session = conn.openSession();
				if(isCount) {
					run = new CountRunnable();
					executor.submit(run);
				}
				session.execCommand(cmd);
				String err = readInputStream(session.getStderr());
				String out = readInputStream(session.getStdout());
				if(err != null && err.length() > 0) {
					ret = false;
					result = err;
				} else if(out != null && out.length() > 0){
					result = out;
				}
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(run != null) {
				run.stop();
			}
		}
		return new Pair<Boolean, String>(ret, result);
	}
	
	public void executeNoResult(String cmd) {
		try {
			if(isAuthenticated) {
				Session session = conn.openSession();
				session.execCommand(cmd);
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Pair<Boolean, String> executeOneLine(String cmd) {
		String result = null;
		boolean ret = true;
		try { 
			if(isAuthenticated) {
				Session session = conn.openSession();
				session.execCommand(cmd);
				String err = readInputStream(session.getStderr());
				if(err != null && err.length() > 0) { 
					ret = false;
					result = err;
				} else {
					StringBuffer retBuffer = new StringBuffer();
					BufferedReader bReader = new BufferedReader(
							new InputStreamReader(session.getStdout(), Charset.defaultCharset().toString()));
					String line = null;
					while(null != (line = bReader.readLine())) {
						retBuffer.append(line);
					}
					bReader.close();
					result = retBuffer.toString();
				}
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Pair<Boolean, String>(ret, result);
	}
	
	public Pair<Boolean, String> execute(String cmd) {
		String result = null;
		boolean ret = true;
		try {
			if(isAuthenticated) {
				Session session = conn.openSession();
				session.execCommand(cmd);
				String err = readInputStream(session.getStderr());
				if(err != null && err.length() > 0) { 
					ret = false;
					result = err;
				} else {
					StringBuffer retBuffer = new StringBuffer();
					BufferedReader bReader = new BufferedReader(
							new InputStreamReader(session.getStdout(), Charset.defaultCharset().toString()));
					String line = null;
					while(null != (line = bReader.readLine())) {
						retBuffer.append(line + System.getProperty("line.separator"));
					}
					bReader.close();
					result = retBuffer.toString();
				}
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Pair<Boolean, String>(ret, result);
	}
	
	public String readSystemLog(String cmd, int showLines) {
		String result = null;
		try {
			if(isAuthenticated) {
				Session session = conn.openSession();
				session.execCommand(cmd);
				BufferedReader bReader = new BufferedReader(
						new InputStreamReader(session.getStdout(), Charset.defaultCharset().toString()));
				String line = null;
				Set<String> errorSet = new HashSet<String>();
				List<String> errorList = new ArrayList<String>();
				while(null != (line = bReader.readLine())) {
					line = line.replaceAll("\\[[^\\]]*\\] ", "");
					if(!errorSet.contains(line)) {
						errorSet.add(line);
						errorList.add(line);
					}
				}
				bReader.close();
				StringBuffer retBuffer = new StringBuffer();
				int errorLines = errorList.size();
				if(errorLines > 0) {
					retBuffer.append("ERROR lines£º" + errorLines + System.getProperty("line.separator"));
					if(errorLines <= showLines) {
						int index = 1;
						for(String errorLog: errorList) {
							String indexStr = index + "  ";
							retBuffer.append(indexStr.substring(0, 2) + ":" + errorLog + System.getProperty("line.separator"));
							index++;
						}
					} else {
						retBuffer.append("1 : ..." + System.getProperty("line.separator"));
						int index = showLines;
						for(int i = errorLines - 2; i < errorLines; i++) {
							String indexStr = index + "  ";
							retBuffer.append(indexStr.substring(0, 2) + ":" + errorList.get(i) + System.getProperty("line.separator"));
							index++;
						}
					}
				}
				result = retBuffer.toString();
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	private String readInputStream(InputStream in) throws IOException {
		StringBuffer retBuffer = new StringBuffer();
		BufferedReader bReader = new BufferedReader(
				new InputStreamReader(in, Charset.defaultCharset().toString()));
		String line = null;
		int index = 1;
		while(null != (line = bReader.readLine())) {
			if(!line.startsWith("kill: usage:")
					&& !line.startsWith("cat:")) {
				String indexStr = index + "       ";
				if(line.contains("kill: (1) - Operation not permitted")) {
					line = "Server Not Start";
				} 
				retBuffer.append(indexStr.substring(0, 5) + ":" + line + System.getProperty("line.separator"));
				index++;
			}
		}
		bReader.close();
		String ret = retBuffer.toString();
		if(ret != null && ret.length() > System.getProperty("line.separator").length()) {
			ret = ret.substring(0, ret.length() - System.getProperty("line.separator").length());
		}
		return ret;
	}
	
	public void close() {
		try {
			if(conn != null) {
				conn.close();
			}
			if(executor != null) {
				executor.shutdownNow();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
