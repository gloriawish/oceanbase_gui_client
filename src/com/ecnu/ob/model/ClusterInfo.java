package com.ecnu.ob.model;

import java.util.ArrayList;
import java.util.List;

import com.ecnu.ob.manager.ClusterManager;

/**
 * 集群信息抽象类
 * @author Administrator
 *
 */
public class ClusterInfo {
	
	private String lmsIPPort;
	private String userName;
	private String userPass;
	
	private String gm_create;
	private String gm_modify;
	private int cluster_id;
	private String cluster_vip;
	private int cluster_port;
	private int cluster_role;
	private String cluster_name;
	private String cluster_info;
	private int cluster_flow_percent;
	private int read_strategy;
	private int rootserver_port;
	
	private List<ServerInfo> rootServer;
	private List<ServerInfo> updateServer;
	private List<ServerInfo> chunkServer;
	private List<ServerInfo> mergeServer;
	
	private List<ServerInfo> allServer;
	
	public ClusterInfo(String lmsIPPort, String userName, String userPass) {
		rootServer = new ArrayList<ServerInfo>();
		updateServer = new ArrayList<ServerInfo>();
		chunkServer = new ArrayList<ServerInfo>();
		mergeServer = new ArrayList<ServerInfo>();
		this.lmsIPPort = lmsIPPort;
		this.userName = userName;
		this.userPass = userPass;
	}
	public List<ServerInfo> getRootServer() {
		return rootServer;
	}

	public List<ServerInfo> getUpdateServer() {
		return updateServer;
	}

	public List<ServerInfo> getChunkServer() {
		return chunkServer;
	}

	public List<ServerInfo> getMergeServer() {
		return mergeServer;
	}

	public String getGm_create() {
		return gm_create;
	}
	public void setGm_create(String gm_create) {
		this.gm_create = gm_create;
	}
	public String getGm_modify() {
		return gm_modify;
	}
	public void setGm_modify(String gm_modify) {
		this.gm_modify = gm_modify;
	}
	public int getCluster_id() {
		return cluster_id;
	}
	public void setCluster_id(int cluster_id) {
		this.cluster_id = cluster_id;
	}
	public String getCluster_vip() {
		return cluster_vip;
	}
	public void setCluster_vip(String cluster_vip) {
		this.cluster_vip = cluster_vip;
	}
	public int getCluster_port() {
		return cluster_port;
	}
	public void setCluster_port(int cluster_port) {
		this.cluster_port = cluster_port;
	}
	public int getCluster_role() {
		return cluster_role;
	}
	public void setCluster_role(int cluster_role) {
		this.cluster_role = cluster_role;
	}
	public String getCluster_name() {
		return cluster_name;
	}
	public void setCluster_name(String cluster_name) {
		this.cluster_name = cluster_name;
	}
	public String getCluster_info() {
		return cluster_info;
	}
	public void setCluster_info(String cluster_info) {
		this.cluster_info = cluster_info;
	}
	public int getCluster_flow_percent() {
		return cluster_flow_percent;
	}
	public void setCluster_flow_percent(int cluster_flow_percent) {
		this.cluster_flow_percent = cluster_flow_percent;
	}
	public int getRead_strategy() {
		return read_strategy;
	}
	public void setRead_strategy(int read_strategy) {
		this.read_strategy = read_strategy;
	}
	public int getRootserver_port() {
		return rootserver_port;
	}
	public void setRootserver_port(int rootserver_port) {
		this.rootserver_port = rootserver_port;
	}
	public List<ServerInfo> getAllServer() {
		//查找当前cluster的所有服务器
		allServer = ClusterManager.getClusterAllServer(lmsIPPort, userName, userPass,cluster_id);
		
		rootServer.clear();
		chunkServer.clear();
		mergeServer.clear();
		updateServer.clear();
		for (ServerInfo serverInfo : allServer) {
			if(serverInfo.getSvr_type() == ServerType.ROOTSERVER) {
				rootServer.add(serverInfo);
			}
			if(serverInfo.getSvr_type() == ServerType.UPDATESERVER) {
				updateServer.add(serverInfo);
			}
			if(serverInfo.getSvr_type() == ServerType.CHUNKSERVER) {
				chunkServer.add(serverInfo);
			}
			if(serverInfo.getSvr_type() == ServerType.MERGESERVER) {
				mergeServer.add(serverInfo);
			}
		}
		
		return allServer;
	}
	
	
}
