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
	
	public Object doExecute(Connection c,PreparedStatement ps,ResultSet rs);
	
}
