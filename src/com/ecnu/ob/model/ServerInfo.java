package com.ecnu.ob.model;

/**
 * __all_server里面的信息
 * @author Administrator
 *
 */
public class ServerInfo {
	
	private String gm_create;
	private String gm_modify;
	private int cluster_id;
	private ServerType svr_type;
	private String svr_ip;
	private int svr_port;
	private int inner_port;
	private int svr_role;
	private String svr_version;
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
	public ServerType getSvr_type() {
		return svr_type;
	}
	public void setSvr_type(ServerType svr_type) {
		this.svr_type = svr_type;
	}
	public String getSvr_ip() {
		return svr_ip;
	}
	public void setSvr_ip(String svr_ip) {
		this.svr_ip = svr_ip;
	}
	public int getSvr_port() {
		return svr_port;
	}
	public void setSvr_port(int svr_port) {
		this.svr_port = svr_port;
	}
	public int getInner_port() {
		return inner_port;
	}
	public void setInner_port(int inner_port) {
		this.inner_port = inner_port;
	}
	public int getSvr_role() {
		return svr_role;
	}
	public void setSvr_role(int svr_role) {
		this.svr_role = svr_role;
	}
	public String getSvr_version() {
		return svr_version;
	}
	public void setSvr_version(String svr_version) {
		this.svr_version = svr_version;
	}
	@Override
	public String toString() {
		String summary = "";
		switch (svr_type) {
			case CHUNKSERVER:
				summary += "CS";
				break;
			case MERGESERVER:
				summary += "MS";
				break;
			case ROOTSERVER:
				summary += "RS";
				break;
			case UPDATESERVER:
				summary += "UPS";
				break;
		}
		summary += "(" +svr_ip + ":" + svr_port + ")";
		return summary;
	}
}
