package core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bean.ColumnInfo;
import bean.TableInfo;
import convertorImpl.MysqlTypeConvertor;
import convertorImpl.SqlServerTypeConvertor;
import creatorImpl.JavaBeanCreator;
import creatorImpl.JavaControllerCreator;
import creatorImpl.JavaDaoCreator;
import creatorImpl.JavaServiceCreator;
import creatorImpl.XMLMapperCreatorMySQL;
import creatorImpl.XMLMapperCreatorSQLServer;
import utils.StringUtils;

/**
 * 所有表结构和类结构的关系，并可以根据表结构生成类结构。
 * @author Malcolm
 *
 */
public class TableContext {
	
	/**
	 *存放所有表信息的Map
	 */
	public static Map<String,TableInfo> tables = new HashMap<String,TableInfo>();
	public static Map<Class,TableInfo> beanClassTableMap = new HashMap<Class,TableInfo>();
	
	/**
	 * 加载所有数据库信息 将表结构 生成类结构
	 * @param table 要加载的表名 不定参数，可以为空
	 */
	public  void loadDB(String... table) {  
		try {
			Connection con = DBManager.getConnction(); 

			DatabaseMetaData dbmd = con.getMetaData();  //获取数据库元数据对象 通过此元对象可以获取数据库中的所有东西。 （与Statement、PreparedStatement类似）

			ResultSet tableRS  = dbmd.getTables(null, "%", "%", new String[]{"TABLE"}); //查询所有的表

			
			out:while(tableRS.next()){ 
				
				//遍历所有的表
				String  tableName = (String) tableRS.getObject("TABLE_NAME");	 
				
				if(table.length!=0 && table!=null) {
					boolean flag = false;
					for (int i = 0; i < table.length; i++) {
						if (table[i].equalsIgnoreCase(tableName)) {
							flag = true;
							break;
						}
					}
					
					if (!flag) {
						continue;
					}
				}
				
				TableInfo ti = new TableInfo(tableName,new HashMap<String,ColumnInfo>(),new ArrayList<ColumnInfo>());
				tables.put(tableName, ti);
				//查询每个表中的所有字段
				ResultSet columnRS = dbmd.getColumns(null, "%", tableName, "%"); 
				while(columnRS.next()){
					ColumnInfo ci = new ColumnInfo(columnRS.getString("COLUMN_NAME"),columnRS.getString("TYPE_NAME"),0); //构造器生成列信息对象  字段名称；字段类型；键类型
					ti.getColumns().put(columnRS.getString("COLUMN_NAME"), ci);  //表中取出字段Map，并将key：name  value：columnInfo放入
				}
				//查询每一个表的所有主键（多个主键的情况下）
				ResultSet primaryKeysRS = dbmd.getPrimaryKeys(null, "%", tableName);  
				while(primaryKeysRS.next()){
					ColumnInfo ci2 = ti.getColumns().get(primaryKeysRS.getObject("COLUMN_NAME"));
					ci2.setKeyType(1); 
					ti.getPrikeys().add(ci2);
				}
				//如果是联合主键，取联合主键的第一个为主键。    确保只有唯一主键能用。
				if(ti.getPrikeys().size()>0){ 
					ti.setOnlyPrikey(ti.getPrikeys().get(0));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 生成文件
	 * @param typeConvertor
	 * @param modules 需要生成的模块
	 */
	public void createAppointedModuleFiles(Boolean add, Boolean update, Boolean queryRows, Boolean queryAll, Boolean queryList, Boolean delete, String... modules) {
		
		//自动根据数据库驱动生成 相应的类型转换器和xml生成器
		TypeConvertor typeConvertor = null;
		XMLMapperCreator xmlMapperCreator = null;
				
		if( DBManager.conf.getDriver().toLowerCase().contains("mysql") ) {
			typeConvertor = new MysqlTypeConvertor();
			xmlMapperCreator = new XMLMapperCreatorMySQL(add, update, queryRows, queryAll, queryList, delete);
		}else if( DBManager.conf.getDriver().toLowerCase().contains("sqlserver") ){
			typeConvertor = new SqlServerTypeConvertor();
			xmlMapperCreator = new XMLMapperCreatorSQLServer(add, update, queryRows, queryAll, queryList, delete);
		}
		
		for(TableInfo tableInfo:tables.values()){
			
			//如果没有传，生成所有模块的代码文件
			if (modules.length==0) {
				//Bean模块代码生成
				JavaBeanCreator javaBeanCreator = new JavaBeanCreator(tableInfo, typeConvertor, "Bean","java");
				javaBeanCreator.createJavaFile();
				
				//Dao模块代码生成
				JavaDaoCreator javaDaoCreator = new JavaDaoCreator(tableInfo, "Dao","java");
				javaDaoCreator.createJavaFile();
				
				//Service模块代码生成
				JavaServiceCreator javaServiceCreator = new JavaServiceCreator(tableInfo, "Service","java");
				javaServiceCreator.createJavaFile();
				
				//Controller模块代码生成
				JavaControllerCreator javaControllerCreator = new JavaControllerCreator(tableInfo, "Controller","java");
				javaControllerCreator.createJavaFile();
				
				//Mapper代码生成
				XMLMapperCreator xmlMapperCreatorIn = new XMLMapperCreatorMySQL(tableInfo, xmlMapperCreator);
				xmlMapperCreatorIn.createXMLFile(tableInfo);
				
				continue;
			}
			
			//如果传了参数，则，生成响应的模块
			for (int i = 0; i < modules.length; i++) {
				String string = modules[i];
				if (string.equals("Bean")) {
					//Bean模块代码生成
					JavaBeanCreator javaBeanCreator = new JavaBeanCreator(tableInfo, typeConvertor, "Bean","java");
					javaBeanCreator.createJavaFile();
				}else if(string.equals("Dao")) {
					//Dao模块代码生成
					JavaDaoCreator javaDaoCreator = new JavaDaoCreator(tableInfo, typeConvertor, "Dao","java");
					javaDaoCreator.createJavaFile();
				}else if(string.equals("Service")) {
					//Service模块代码生成
					JavaServiceCreator javaServiceCreator = new JavaServiceCreator(tableInfo, typeConvertor, "Service","java");
					javaServiceCreator.createJavaFile();
				}else if(string.equals("Controller")) {
					//Controller模块代码生成
					JavaControllerCreator javaControllerCreator = new JavaControllerCreator(tableInfo, typeConvertor, "Controller","java");
					javaControllerCreator.createJavaFile();
				}else if(string.equals("XML")){
					//Mapper代码生成
					XMLMapperCreatorMySQL xmlMapperCreatorMySQL = new XMLMapperCreatorMySQL(tableInfo, null, null, null, null, null, null);
					xmlMapperCreatorMySQL.createXMLFile(tableInfo);
				}
			}
		}
	}
	
	
	/**
	 * 将po包的所有类都加载生成Class对象。
	 */
	public static void loadPoFile(){
		for(TableInfo tableInfo:tables.values()){
			try {
				Class c = Class.forName("com."+ DBManager.conf.getCompanyName() +"."+ DBManager.conf.getProjectName() +".bean."+ StringUtils.changeFirstToUpper(tableInfo.getTname()));//包名.类名
				beanClassTableMap.put(c, tableInfo);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
}
