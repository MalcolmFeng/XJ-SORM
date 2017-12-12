package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 * 用于模板回调的接口
 * @author Malcolm
 *
 */
public interface CallBack {
	/**
	 * 回调方法
	 * @param c 连接对象
	 * @param ps 预编译对象
	 * @param rs 结果集对象
	 * @return
	 */
	public Object doExecute(Connection c,PreparedStatement ps,ResultSet rs);
}
