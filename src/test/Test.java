package test;

import core.TableContext;
import creatorImpl.XMLMapperCreatorMySQL;

public class Test {

	public static void main(String[] args) throws Exception {
		TableContext tableContext = new TableContext();

		// 1.创建所有表的所有模块
		// loadAllTableAndWriteAllModule(tableContext);

		// 2.创建指定表的所有模块
		// loadAppointedTableAndWriteAllModule(tableContext);

		// 3.创建指定表的指定模块
		 loadAppointedTableAndWriteAppointedModule(tableContext);

//		System.out.println("创建完毕");
//
//		Thread.sleep(20000);
//
//		Class c = Class.forName("com.jiefupay.newplat.bean.Agency_xjBean");
//
//		Object o = c.newInstance();
//		Method setM = c.getMethod("setUnionid", String.class);
//		setM.invoke(o, "fengdianlong");
//
//		Method getM = c.getMethod("getUnionid");
//		String string = (String) getM.invoke(o);
//
//		System.out.println(string);

	}
	
	//(add, update, queryRows, queryAll, queryList, delete)
	
	// 1.创建所有表的所有模块的所有增删改查
	public static void loadAllTableAndWriteAllModule(TableContext tableContext) {
		tableContext.loadDB();
		tableContext.createAppointedModuleFiles(false, false, false, false, false, false);
	}

	// 2.创建指定表的所有模块
	public static void loadAppointedTableAndWriteAllModule(TableContext tableContext) {
		tableContext.loadDB("agency_xj");
		tableContext.createAppointedModuleFiles(false, false, false, false, false, false);
	}

	// 3.创建指定表的指定模块的指定方法
	public static void loadAppointedTableAndWriteAppointedModule(TableContext tableContext) {
		tableContext.loadDB("agency_xj", "fans_xj");
		tableContext.createAppointedModuleFiles(false, false, false, false, false, false, "Bean","Dao");
	}

}
