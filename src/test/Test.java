package test;

import core.TableContext;

public class Test {

	public static void main(String[] args) throws Exception {
		
		TableContext tableContext = new TableContext();
		
		//(add, update, queryRows, queryAll, queryList, delete)
		
		// 1.创建所有表的所有模块
		tableContext.loadDB();
		tableContext.createAppointedModuleFiles(false, false, false, false, false, false);

		// 2.创建指定表的所有模块
		tableContext.loadDB("agency_xj");
		tableContext.createAppointedModuleFiles(false, false, false, false, false, false);
		
		// 3.创建指定表的指定模块
		tableContext.loadDB("agency_xj", "fans_xj");
		tableContext.createAppointedModuleFiles(false, false, false, false, false, false, "Bean","Dao");
		
		System.out.println("创建完毕");
	}
	
}
