package com.ecnu.ob.test;

import java.util.List;

import com.ecnu.ob.manager.ClusterManager;
import com.ecnu.ob.model.DataBaseInfo;
import com.ecnu.ob.model.TableInfo;

public class TestDataBaseManager {

	public static void main(String[] args) {
		
		
		List<DataBaseInfo> dbs = ClusterManager.getAllDatabase("182.119.80.66:62880", "admin", "admin");
		
		System.out.println("database number: " +dbs.size());
		
//		int i = 1;
//		for (DataBaseInfo dataBaseInfo : dbs) {
//			if(i==1) {
//				i++;
//				continue;
//			}
//			List<TableInfo> tblist = dataBaseInfo.getTableList();
//			System.out.println("==================================");
//			System.out.println("table size: " + tblist.size());
//			for (TableInfo tableInfo : tblist) {
//				System.out.println(tableInfo.getTable_name());
//			}
//			System.out.println("==================================");
//		}
		
		List<TableInfo> tblist = dbs.get(2).getTableList();
		System.out.println(dbs.get(2).getDb_name());
		System.out.println(tblist.size());
		
		
		
	}
}
