package com.ecnu.ob.test;

import java.util.List;

import com.ecnu.ob.manager.ClusterManager;
import com.ecnu.ob.model.ClusterInfo;

public class TestClusterMnager {
	
	public static void main(String[] args) {
		
		
		List<ClusterInfo> clusters = ClusterManager.getAllCluster("182.119.80.66:62880", "admin", "admin");
		
		System.out.println(clusters.size());
		
		System.out.println(clusters.get(0).getCluster_vip());
	
		
		
	}

}
