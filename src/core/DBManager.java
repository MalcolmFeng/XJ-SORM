package core;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import bean.Configuration;

/**
 * 根据配置信息，维持连接对象的管理。
 * @author Malcolm
 *
 */
public class DBManager {
	/**
	 * 配置文件管理器对象
	 */
	public static Configuration conf= new Configuration();
	/**
	 * 加载配置文件对象
	 */
	private static Properties prop=null;
	/**
	 * 连接池对象
	 */
	private static DBConnectionPool pool=null;
	
	static{
		prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		conf.setDriver(prop.getProperty("Driver"));
		conf.setURL(prop.getProperty("URL"));
		conf.setUser(prop.getProperty("User"));
		conf.setPwd(prop.getProperty("Pwd"));
		conf.setUsingDB(prop.getProperty("usingDB"));
		conf.setSrcPath(prop.getProperty("srcPath"));
		conf.setPoPackage(prop.getProperty("poPackage"));
		conf.setQueryClass(prop.getProperty("queryClass"));
		conf.setPoolMinSize(Integer.parseInt(prop.getProperty("poolMinSize")));
		conf.setPoolMaxSize(Integer.parseInt(prop.getProperty("poolMaxSize")));
		
		conf.setCompanyName(prop.getProperty("companyName"));
		conf.setProjectName(prop.getProperty("projectName"));
	}
	
	/**
	 * 创建新的连接
	 * @return 返回一个新的连接
	 */
	public static Connection createConnction(){
		try {
			Class.forName(conf.getDriver());
			return DriverManager.getConnection(conf.getURL(), conf.getUser(), conf.getPwd()); //目前只建立连接，后期加入连接池！！！
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 在连接池中获取一个连接
	 * @return 在连接池中获取一个连接
	 */
	public static Connection getConnction(){
		if(pool==null){
			pool=new DBConnectionPool();
		}
		return pool.getConneciton();
	} 
	/**
	 * 关闭对象的方法
	 * @param ps PreparedStatement对象
	 * @param c Conneciton对象
	 */
	public static void close(PreparedStatement ps,Connection c){
		
		try {
			if(ps!=null){
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			pool.closeConneciton(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
