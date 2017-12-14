package core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ColumnInfo;
import bean.TableInfo;
import utils.JBDCUtils;
import utils.RefectUtils;
import utils.StringUtils;

/**
 * 增删改查核心抽象类
 * 
 * @author Malcolm
 */
@SuppressWarnings("all")
public abstract class Query implements Cloneable {

	/**
	 * 模板方法模式 将JDBC的查询操作执行 封装成模板，便于重用（回调使用）
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数组
	 * @param clz
	 *            记录要封装到的java类Class对象
	 * @param cb
	 *            CallBack的实现类，匿名内部类，实现回调。
	 * @return 返回 回调方法的返回值
	 */
	public Object executeQueryTemplate(String sql, Object[] params, Class clz, CallBack cb) {
		Connection c = DBManager.getConnction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 预编译sql语句，并设定参数和执行。
			ps = c.prepareStatement(sql);
			JBDCUtils.setParems(ps, params);
			rs = ps.executeQuery();

			return cb.doExecute(c, ps, rs);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DBManager.close(ps, c);

		}
	}

	/**
	 * 执行一个DML语句
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数
	 * @return 执行sql语句后影响的记录行数
	 */
	public int executeDML(String sql, Object[] params) {
		Connection c = DBManager.getConnction();
		PreparedStatement ps = null;
		int count = 0;
		try {
			ps = c.prepareStatement(sql);
			JBDCUtils.setParems(ps, params);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(ps, c);
		}
		return count;
	}

	/**
	 * 将一个对象存储到数据库中
	 * 
	 * @param obj
	 *            要存储的对象
	 */
	public void insert(Object obj) {
		Class clz = obj.getClass();
		TableInfo tableInfo = TableContext.beanClassTableMap.get(clz);
		Field[] fs = clz.getDeclaredFields();

		List<Object> params = new ArrayList<Object>();

		StringBuilder sql = new StringBuilder();
		sql.append("insert into " + tableInfo.getTname() + "(");
		for (Field f : fs) {
			String fieldName = f.getName();
			Object fieldValue = RefectUtils.invokeGet(fieldName, obj);
			if (fieldValue != null) {
				sql.append(fieldName + ",");
				params.add(fieldValue);
			}
		}
		sql.setCharAt(sql.length() - 1, ')');
		sql.append(" values (");
		for (int i = 0; i < params.size(); i++) {
			sql.append("?,");
		}
		sql.setCharAt(sql.length() - 1, ')');
		executeDML(sql.toString(), params.toArray());
	}

	/**
	 * 删除clz表示类对应的表中的记录（指定主键值的id的记录）
	 * 
	 * @param clz
	 *            跟表对应的类的Class对象
	 * @param id
	 *            主键的值
	 */
	public void delete(Class clz, Object id) {
		// Emp.class,id=2 ----> delet from emp where id=2;
		// 通过Class对象找TableInfo
		TableInfo tableInfo = TableContext.beanClassTableMap.get(clz);
		ColumnInfo onlyPrikey = tableInfo.getOnlyPrikey();

		String sql = "delete from " + tableInfo.getTname() + " where " + onlyPrikey.getName() + " = ?";
		System.out.println("执行sql的删除语句：" + sql);
		executeDML(sql, new Object[] { id });
	}

	/**
	 * 删除对象在数据库中对应的记录（对象所在的类对应到表，对象的主键的值对应到记录）
	 * 
	 * @param obj
	 */
	public void delete(Object obj) {
		Class clz = obj.getClass();
		TableInfo tableInfo = TableContext.beanClassTableMap.get(clz);
		ColumnInfo onlyPrikey = tableInfo.getOnlyPrikey();
		// 通过反射机制，获取属性对应的set get方法
		Object priKeyValue = RefectUtils.invokeGet(onlyPrikey.getName(), obj);
		delete(clz, priKeyValue);
	}

	/**
	 * g更新对象对应的记录，并且只更新指定的字段的值。
	 * 
	 * @param obj
	 *            所有要更新的对象
	 * @param fieldNames
	 *            更新的属性列表
	 * @return 执行sql语句后影响的记录行数
	 */
	public int update(Object obj, String[] fieldNames) {
		Class clz = obj.getClass();
		TableInfo tableInfo = TableContext.beanClassTableMap.get(clz);

		// 设置一个容器用于存放每一个参数
		List<Object> params = new ArrayList<Object>();
		// 获取主键的列信息对象
		ColumnInfo priKey = tableInfo.getOnlyPrikey();

		// 拼接sql语句为：updata 表名 set name=?,age=? where id=?
		StringBuilder sql = new StringBuilder();
		sql.append("update " + tableInfo.getTname() + " set ");
		for (String fieldName : fieldNames) {
			Object fieldValue = RefectUtils.invokeGet(fieldName, obj);
			sql.append(fieldName + "=?,");
			params.add(fieldValue); // 将循环遍历获取的每一个参数值，添加到参数list中
		}
		sql.setCharAt(sql.length() - 1, ' ');
		sql.append("where " + priKey.getName() + "=?");
		params.add(RefectUtils.invokeGet(priKey.getName(), obj));

		// 执行sql语句（所拼接的sql语句转换为字符串，参数list转换为参数数组）
		System.out.println("执行sql的修改语句：" + sql);
		return executeDML(sql.toString(), params.toArray());
	}

	/**
	 * 查询返回多行记录，并将每行记录封装到Class指定的类的对象中。
	 * 
	 * @param sql
	 *            查询语句
	 * @param clz
	 *            封装数据的javabean类的Class对象
	 * @param params
	 *            sql的参数
	 * @return 返回查询到的结果
	 */
	public List queryRows(String sql, Class clz, Object[] params) {

		System.out.println("执行sql查找的语句：" + sql);
		return (List) executeQueryTemplate(sql, params, clz, new CallBack() {

			@Override
			public Object doExecute(Connection c, PreparedStatement ps, ResultSet rs) {
				try {
					List list = null;
					ResultSetMetaData metaData = rs.getMetaData();
					while (rs.next()) {
						if (list == null) {
							list = new ArrayList();
						}
						Object rowObj = clz.newInstance();
						// 循环遍历每一列
						for (int i = 0; i < metaData.getColumnCount(); i++) {
							// 列的名字和值
							String columnName = metaData.getColumnLabel(i + 1);
							Object columnValue = rs.getObject(i + 1);
							// 通过反射获取set方法，对rowObj进行set值。
							Method m = rowObj.getClass().getDeclaredMethod(
									"set" + StringUtils.changeFirstToUpper(columnName), columnValue.getClass());
							m.invoke(rowObj, columnValue);
						}
						// 在每一行的每一列参数设置好之后，把此行记录obj添加到list中。
						list.add(rowObj);
					}
					return list;
				} catch (Exception e) {
					return null;
				}
			}
		});
	}

	/**
	 * 查询返回一行记录，并将记录封装到Class指定的类的对象中。
	 * 
	 * @param sql
	 *            查询语句
	 * @param clz
	 *            封装数据的javabean类的Class对象
	 * @param params
	 *            sql的参数
	 * @return 返回查询到的结果
	 */
	public Object queryRow(String sql, Class clz, Object[] params) {
		List list = queryRows(sql, clz, params);
		return list != null && (list.size() > 0) ? list : null;
	}

	/**
	 * 查询返回一个值，并将该值返回
	 * 
	 * @param sql
	 *            查询语句
	 * @param clz
	 *            封装数据的javabean类的Class对象
	 * @param params
	 *            sql的参数
	 * @return 返回查询到的结果
	 */
	public Object queryValue(String sql, Class clz, Object[] params) {
		System.out.println("执行sql的查找某个值语句：" + sql);
		return executeQueryTemplate(sql, params, clz, new CallBack() {

			@Override
			public Object doExecute(Connection c, PreparedStatement ps, ResultSet rs) {
				Object value = null;
				try {
					while (rs.next()) {
						value = rs.getObject(1);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return value;
			}
		});

	}

	/**
	 * 查询返回一个数字，并将该值返回
	 * 
	 * @param sql
	 *            查询语句
	 * @param clz
	 *            封装数据的javabean类的Class对象
	 * @param params
	 *            sql的参数
	 * @return 返回查询到的数字
	 */
	public Number queryNumber(String sql, Class clz, Object[] params) {
		return (Number) queryValue(sql, clz, params);
	}

	/**
	 * 提供分页查询的抽象方法，供后期重写
	 * 
	 * @param PageNumber
	 *            页数
	 * @param size
	 *            每页的大小
	 * @return 返回查询对象
	 */
	public abstract Object queryPagenate(int PageNumber, int size);

	/**
	 * Query实现Cloneable接口，重写clone方法（原型模式，提高效率和安全）
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
