package com.ecnu.ob.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

//import com.alipay.oceanbase.OBGroupDataSource;

public class Connector {

	public static Map<String,Connection> CONNECTIONPOOL = new HashMap<String,Connection>();
	//获得一个OB的连接
	/*public static Connection getOBConnection(String ip_port,String dbName, String userName, String password){
		Map<String, String> configParams = new HashMap<String, String>();  
		//默认连接池属性是: maxActive:20,minIdle:1,initialSize:0
		//configParams.put("initialSize", "0");
		//configParams.put("minIdle", "1");
		//configParams.put("maxActive", "20");
		//configParams.put("maxWait", "50000000");
		configParams.put("username", userName);
		configParams.put("password", password);
		configParams.put("database", dbName);
		configParams.put("clusterAddress", ip_port);
		OBGroupDataSource obGroupDataSource = new OBGroupDataSource();
		obGroupDataSource.setDataSourceConfig(configParams);
		try {
			obGroupDataSource.init();
			return obGroupDataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/
	
	//MySql JDBC连接
	public static Connection getJDBCConnection(String ip_port,String dbName, String userName, String password){
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://"+ip_port+"/"+dbName;
		try {
			Class.forName(driver);
			if(CONNECTIONPOOL.containsKey(url)) {
				Connection conn = CONNECTIONPOOL.get(url);
				return conn;
			} else {
				Connection conn = DriverManager.getConnection(url, userName, password);
				CONNECTIONPOOL.put(url, conn);
				return conn;
			}
		} catch (Exception e) {
			//TODO
			//e.printStackTrace();
			System.out.println("connect error!");
		}
		return null;
	}

}
