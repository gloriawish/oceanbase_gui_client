package com.ecnu.ob.jdbc;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

public class ShellClient {
	
	
	
	private Connection conn = null;
	private InputStream in=null;
	private InputStream err=null;
	private OutputStream out=null;
	private String ip=null;
	private String userName=null;
	private String password=null;
	private Session session=null;
	public ShellClient(String ip,String uname,String pass)
	{
		this.ip=ip;
		this.userName=uname;
		this.password=pass;
	}
	
	public void start()
	{
		try {
			conn = new Connection(ip);
			conn.connect();
			if (conn.authenticateWithPassword(userName, password)) {
				session = conn.openSession();
				in=session.getStdout();
				//session.execCommand("ls");
				//processStdout();
				out=session.getStdin();
				out.write("ls".getBytes());
				out.flush();
				//err=session.getStderr();
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
	}
	
	public void execCommand()
	{
		/*
		String command="ls \n";
		if(out!=null)
		{
			try {
				out.write(command.getBytes());
				out.flush();
				processStdout();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
//		try {
//			session.execCommand("ls");
//			in=session.getStdout();
//			processStdout();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public void processStdout()
	{
		try {
			if(in!=null&&in.available()>0)
			{
				byte[] data=new byte[in.available()];
				int len=in.read(data);
				if(len<0)
					throw new Exception("network error.");
				String temp=new String(data,0,len,Charset.defaultCharset().toString());
				System.out.println(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void processStderr()
	{
		try {
			if(err!=null&&err.available()>0)
			{
				byte[] data=new byte[err.available()];
				int len=err.read(data);
				if(len<0)
					throw new Exception("network error.");
				String temp=new String(data,0,len,Charset.defaultCharset().toString());
				System.out.println("[ERROR] "+temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
