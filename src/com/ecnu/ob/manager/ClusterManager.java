package com.ecnu.ob.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ecnu.ob.jdbc.Connector;
import com.ecnu.ob.main.Main;
import com.ecnu.ob.model.ClusterInfo;
import com.ecnu.ob.model.DataBaseInfo;
import com.ecnu.ob.model.ObConstant;
import com.ecnu.ob.model.ServerInfo;
import com.ecnu.ob.model.ServerType;

public class ClusterManager {

	
	public static ClusterInfo MASTER = null;
	
	public static ClusterInfo getMasterCluster() {
		if(MASTER == null) {
			List<ClusterInfo> clusters = ClusterManager.getAllCluster(Main.LMSIPPORT, Main.USERNAME, Main.USERPASS);
			//find the master cluster
			for (ClusterInfo clusterInfo : clusters) {
				if(clusterInfo.getCluster_role() == 1) {//主机群
					MASTER = clusterInfo;
					break;
				}
			}
		}
		return MASTER;
	}
	
	/**
	 * 获取所有的集群，以及集群对应的server的信息(不必延迟加载)
	 * @param lmsServerIPPort
	 * @param userName
	 * @param userPass
	 * @return
	 */
	public static List<ClusterInfo> getAllCluster(String lmsServerIPPort, String userName, String userPass) {
		List<ClusterInfo> clusterInfos = new ArrayList<ClusterInfo>();
		Connection conn = Connector.getJDBCConnection(lmsServerIPPort, "", userName, userPass);
		if(conn != null) {
			try {
				List<ServerInfo> serverInfos = getAllServer(lmsServerIPPort, userName, userPass);
				Statement stmt = conn.createStatement();
				ResultSet set = stmt.executeQuery(ObConstant.SELECT_ALL_CLUSTER);
				while(set.next()) {
					ClusterInfo cinfo = new ClusterInfo(lmsServerIPPort,userName,userPass);
					cinfo.setGm_create(set.getString("gm_create"));
					cinfo.setGm_modify(set.getString("gm_modify"));
					cinfo.setCluster_id(set.getInt("cluster_id"));
					cinfo.setCluster_vip(set.getString("cluster_vip"));
					cinfo.setCluster_port(set.getInt("cluster_port"));
					cinfo.setCluster_role(set.getInt("cluster_role"));
					cinfo.setCluster_name(set.getString("cluster_name"));
					cinfo.setCluster_info(set.getString("cluster_info"));
					cinfo.setCluster_flow_percent(set.getInt("cluster_flow_percent"));
					cinfo.setRead_strategy(set.getInt("read_strategy"));
					cinfo.setRootserver_port(set.getInt("rootserver_port"));
					
					for (ServerInfo serverInfo : serverInfos) {
						if(serverInfo.getCluster_id() == cinfo.getCluster_id()) {
							if(serverInfo.getSvr_type() == ServerType.CHUNKSERVER) {
								cinfo.getChunkServer().add(serverInfo);
							}
							if(serverInfo.getSvr_type() == ServerType.MERGESERVER) {
								cinfo.getMergeServer().add(serverInfo);
							}
							if(serverInfo.getSvr_type() == ServerType.ROOTSERVER) {
								cinfo.getRootServer().add(serverInfo);
							}
							if(serverInfo.getSvr_type() == ServerType.UPDATESERVER) {
								cinfo.getUpdateServer().add(serverInfo);
							}
						}
					}
					
					clusterInfos.add(cinfo);
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			return null;
		}
		//Set Master
		for (ClusterInfo clusterInfo : clusterInfos) {
			if(clusterInfo.getCluster_role() == 1) {//主机群
				MASTER = clusterInfo;
				break;
			}
		}
		
		return clusterInfos;
	}
	
	public static List<ServerInfo> getAllServer(String lmsServerIPPort, String userName, String userPass) {
		List<ServerInfo> serverInfos = new ArrayList<ServerInfo>();
		Connection conn = Connector.getJDBCConnection(lmsServerIPPort, "", userName, userPass);
		if(conn != null) {
			try {
				
				Statement stmt = conn.createStatement();
				ResultSet set = stmt.executeQuery(ObConstant.SELECT_ALL_SERVER);
				
				while(set.next()) {
					ServerInfo sinfo = new ServerInfo();
					sinfo.setGm_create(set.getString("gm_create"));
					sinfo.setGm_modify(set.getString("gm_modify"));
					sinfo.setCluster_id(set.getInt("cluster_id"));
					if(set.getString("svr_type").equals("chunkserver")) {
						sinfo.setSvr_type(ServerType.CHUNKSERVER);
					}
					if(set.getString("svr_type").equals("mergeserver")) {
						sinfo.setSvr_type(ServerType.MERGESERVER);
					}
					if(set.getString("svr_type").equals("rootserver")) {
						sinfo.setSvr_type(ServerType.ROOTSERVER);
					}
					if(set.getString("svr_type").equals("updateserver")) {
						sinfo.setSvr_type(ServerType.UPDATESERVER);
					}
					sinfo.setSvr_ip(set.getString("svr_ip"));
					sinfo.setSvr_port(set.getInt("svr_port"));
					sinfo.setInner_port(set.getInt("inner_port"));
					sinfo.setSvr_role(set.getInt("svr_role"));
					sinfo.setSvr_version(set.getString("svr_version"));
					serverInfos.add(sinfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		} else {
			return null;
		}
		return serverInfos;
	}
	
	public static List<ServerInfo> getClusterAllServer(String lmsServerIPPort, String userName, String userPass, int clusterId) {
		List<ServerInfo> serverInfos = new ArrayList<ServerInfo>();
		Connection conn = Connector.getJDBCConnection(lmsServerIPPort, "", userName, userPass);
		if(conn != null) {
			try {
				
				Statement stmt = conn.createStatement();
				ResultSet set = stmt.executeQuery(String.format(ObConstant.SELECT_CLUSTER_ALL_SERVER,clusterId));
				
				while(set.next()) {
					ServerInfo sinfo = new ServerInfo();
					sinfo.setGm_create(set.getString("gm_create"));
					sinfo.setGm_modify(set.getString("gm_modify"));
					sinfo.setCluster_id(set.getInt("cluster_id"));
					if(set.getString("svr_type").equals("chunkserver")) {
						sinfo.setSvr_type(ServerType.CHUNKSERVER);
					}
					if(set.getString("svr_type").equals("mergeserver")) {
						sinfo.setSvr_type(ServerType.MERGESERVER);
					}
					if(set.getString("svr_type").equals("rootserver")) {
						sinfo.setSvr_type(ServerType.ROOTSERVER);
					}
					if(set.getString("svr_type").equals("updateserver")) {
						sinfo.setSvr_type(ServerType.UPDATESERVER);
					}
					sinfo.setSvr_ip(set.getString("svr_ip"));
					sinfo.setSvr_port(set.getInt("svr_port"));
					sinfo.setInner_port(set.getInt("inner_port"));
					sinfo.setSvr_role(set.getInt("svr_role"));
					sinfo.setSvr_version(set.getString("svr_version"));
					serverInfos.add(sinfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		} else {
			return null;
		}
		return serverInfos;
	}
	
	public static List<DataBaseInfo> getAllDatabase(String lmsServerIPPort, String userName, String userPass) {
		List<DataBaseInfo> dbInfos = new ArrayList<DataBaseInfo>();
		Connection conn = Connector.getJDBCConnection(lmsServerIPPort, "", userName, userPass);
		if(conn != null) {
			try {
				Statement stmt = conn.createStatement();
				ResultSet set = stmt.executeQuery(ObConstant.SELECT_ALL_DATABSE);
				
				while(set.next()) {
					
					DataBaseInfo dinfo = new DataBaseInfo(lmsServerIPPort,userName,userPass);
					dinfo.setGm_create(set.getString("gm_create"));
					dinfo.setGm_modify(set.getString("gm_modify"));
					dinfo.setDb_id(set.getInt("db_id"));
					dinfo.setDb_name(set.getString("db_name"));
					dinfo.setStat(set.getInt("stat"));
					
					dbInfos.add(dinfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		} else {
			return null;
		}
		return dbInfos;
	}
}
