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
import convertor.MysqlTypeConvertor;
import creator.JavaBeanCreator;
import creator.JavaControllerCreator;
import creator.JavaDaoCreator;
import creator.JavaServiceCreator;
import creator.XMLMapperCreatorMySQL;
import utils.StringUtils;

/**
 * 所有表结构和类结构的关系，并可以根据表结构生成类结构。
 * @author Malcolm
 *
 */
public class TableContext {
	
	public static Map<String,TableInfo> tables = new HashMap<String,TableInfo>();
	public static Map<Class,TableInfo> poClassTableMap = new HashMap<Class,TableInfo>();
	
	/**
	 * 加载所有数据库信息 将表结构 生成类结构
	 * @param table
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
	 * 将po包的所有类都加载生成Class对象。
	 */
	public static void loadPoFile(){
		for(TableInfo tableInfo:tables.values()){
			try {
				Class c = Class.forName(DBManager.conf.getPoPackage()+"."+StringUtils.changeFirstToUpper(tableInfo.getTname()));//包名.类名
				poClassTableMap.put(c, tableInfo);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据表结构在PO包中更新相应的javabean
	 */
	public void createAllFiles() {
		for(TableInfo tableInfo:tables.values()){
			
			//Bean模块代码生成
			JavaBeanCreator javaBeanCreator = new JavaBeanCreator(tableInfo, new MysqlTypeConvertor(), "Bean","java");
			javaBeanCreator.createJavaFile();
			
			//Dao模块代码生成
			JavaDaoCreator javaDaoCreator = new JavaDaoCreator(tableInfo, new MysqlTypeConvertor(), "Dao","java");
			javaDaoCreator.createJavaFile();
			
			//Service模块代码生成
			JavaServiceCreator javaServiceCreator = new JavaServiceCreator(tableInfo, new MysqlTypeConvertor(), "Service","java");
			javaServiceCreator.createJavaFile();
			
			//Controller模块代码生成
			JavaControllerCreator javaControllerCreator = new JavaControllerCreator(tableInfo, new MysqlTypeConvertor(), "Controller","java");
			javaControllerCreator.createJavaFile();
			
			//Mapper代码生成
			XMLMapperCreatorMySQL xmlMapperCreatorMySQL = new XMLMapperCreatorMySQL(tableInfo, null, null, null, null, null, null);
			xmlMapperCreatorMySQL.createXMLFile(tableInfo);
			
		}
	}
	
}
