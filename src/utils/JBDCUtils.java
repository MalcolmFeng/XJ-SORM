package utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 封装了JDBC查询常用的操作。
 * @author Malcolm
 *
 */
public class JBDCUtils {
	/**
	 * 设置sql语句的参数
	 * @param ps 预编译sql对象
	 * @param params 参数组
	 */
	public static void setParems(PreparedStatement ps,Object[] params){
		if(params!=null){
			for(int i=0;i<params.length;i++){
				try {
					ps.setObject(i+1, params[i]);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
