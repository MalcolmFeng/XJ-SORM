package core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * 连接池
 * @author Malcolm
 *
 */
public class DBConnectionPool {
	/**
	 * 连接池对象list
	 */
	private List<Connection> pool=null;
	/**
	 * 最小连接数
	 */
	private static final int MIN_POOLSIZE=DBManager.conf.getPoolMinSize();
	/**
	 * 最大连接数
	 */
	private static final int MAX_POOLSIZE=DBManager.conf.getPoolMaxSize();
	
	
	/**
	 * 无参构造的时候就初始化连接池。
	 */
	public DBConnectionPool() {
		iniPool();
	}
	

	/**
	 * 初始化连接池，使池中连接数达到最小值
	 */
	public void iniPool(){
		if(pool==null){
			pool= new ArrayList<Connection>();
		}
		while(pool.size()<MIN_POOLSIZE){
			pool.add(DBManager.createConnction());
		}
	}
	
	/**
	 * 获取连接池中的最后一个连接，并在连接池中移除
	 * @return 返回连接池中最后一个连接
	 */
	public synchronized Connection getConneciton(){
		int last_index = pool.size()-1;
		Connection lastConnection = pool.get(pool.size()-1);
		pool.remove(pool.size()-1);
		return lastConnection;
	}
	
	/**
	 * 关闭连接，放回连接池
	 */
	public synchronized void closeConneciton(Connection c){
		if(pool.size()<MAX_POOLSIZE){
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("关闭一个连接");
			pool.add(c);
		}
	}
	
	
}
