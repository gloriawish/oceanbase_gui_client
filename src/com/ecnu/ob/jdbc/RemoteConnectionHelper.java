package com.ecnu.ob.jdbc;

import java.util.HashMap;
import java.util.Map;

public class RemoteConnectionHelper {
	
	
	public static Map<String,RemoteConnection> cahce;
	
	static {
		cahce = new HashMap<String, RemoteConnection>();
	}
	
	
	public static RemoteConnection getRemoteConnection(String ip, String username, String password) {
		String key = ip + username + password;
		if(cahce.containsKey(ip+username+password)) {
			return cahce.get(key);
		} else {
			RemoteConnection remoteConnection = new RemoteConnection(ip, username, password);
			cahce.put(key, remoteConnection);
			return remoteConnection;
		}
		
	}

}
