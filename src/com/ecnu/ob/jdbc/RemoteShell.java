package com.ecnu.ob.jdbc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;

/**
 * @author LiYuming
 * @time 2015/06/01
 * @function execute a shell command on a remote machine
 */
public class RemoteShell {

	//execute a shell command on the target machine
	public static String exec(String ip, String userName, String passwd, String cmd) {
		Connection conn = null;
		InputStream in=null;
		String result=null;
		try {
			conn = new Connection(ip);
			conn.connect();
			if (conn.authenticateWithPassword(userName, passwd)) {
				Session session = conn.openSession();
				session.execCommand(cmd);				
				in=session.getStdout();
				result=processStdout(in,Charset.defaultCharset().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String exec(String ip, String userName, String passwd, List<String> cmds) {
		Connection conn = null;
		InputStream in=null;
		StringBuilder sb=new StringBuilder();
		String result=null;
		try {
			conn = new Connection(ip);
			conn.connect();
			if (conn.authenticateWithPassword(userName, passwd)) {
				Session session = conn.openSession();
				
				for (int i = 0; i < cmds.size(); i++) {
					sb.append(cmds.get(i));
					if(i!=cmds.size())
						sb.append(";");
				}
				System.out.println("[shell] "+sb.toString());
				session.execCommand(sb.toString());
				in=session.getStdout();
				result=processStdout(in,Charset.defaultCharset().toString());
				
				processStderr(session.getStderr(),Charset.defaultCharset().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public static void exec_scp(String remote_ip, String remot_user, String remot_passwd, String remot_file_path,String localpath){
		Connection conn_remote = null;
		try {
				conn_remote = new Connection(remote_ip);
				conn_remote.connect();
				boolean isAuthed = conn_remote.authenticateWithPassword(remot_user,remot_passwd);
				if(isAuthed)
				{
					SCPClient scpClient =  conn_remote.createSCPClient();
					scpClient.get(remot_file_path, localpath);
				}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn_remote.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
    private static String processStdout(InputStream in,String charset) throws IOException
    {
    	StringBuffer sb=new StringBuffer();
    	BufferedReader br=null;
    	try {
			br= new BufferedReader(new InputStreamReader(in,charset));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String inputLine=br.readLine();
    	while(inputLine!=null)
    	{
    		sb.append(inputLine+"\n");
    		System.out.println(inputLine);
    		inputLine=br.readLine();
    	}
    	br.close();
    	return sb.toString();
    }
    
    private static String processStderr(InputStream in,String charset) throws IOException
    {
    	StringBuffer sb=new StringBuffer();
    	BufferedReader br=null;
    	try {
			br= new BufferedReader(new InputStreamReader(in,charset));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String inputLine=br.readLine();
    	while(inputLine!=null)
    	{
    		sb.append(inputLine+"\n");
    		System.out.println(inputLine);
    		inputLine=br.readLine();
    	}
    	br.close();
    	return sb.toString();
    }
	//test
//	public static void main(String [] args) throws IOException{
//		
//		System.out.println(RemoteShell.exec("10.11.1.201", "admin", "dbt135", "ls"));
//		long a = System.nanoTime();
//		RemoteShell.exec("10.11.1.201", "admin", "dbt135", "ls");
//		System.out.println((System.nanoTime() - a)/1000000);
//	}
} 

