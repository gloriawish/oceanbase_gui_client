package com.ecnu.ob.test;

import java.util.List;

import com.ecnu.ob.manager.ClusterManager;
import com.ecnu.ob.model.DataBaseInfo;
import com.ecnu.ob.model.TableInfo;

public class TestRemoteShell {

	public static void main(String[] args) {
		List<DataBaseInfo> dbs = ClusterManager.getAllDatabase("182.119.80.66:62880", "admin", "admin");
		
		List<TableInfo> tblist = dbs.get(1).getTableList();
		System.out.println(dbs.get(1).getDb_name());
		System.out.println(tblist.size());
		
		long start = System.currentTimeMillis();
		for (TableInfo tableInfo : tblist) {
			System.out.println(tableInfo.getIndexList().size());
		}
		long end = System.currentTimeMillis();
		
		System.out.println("use time:" + (end - start));
		
	}
}
