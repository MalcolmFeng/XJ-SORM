package utils;

import java.lang.reflect.Method;

/**
 * 封装了反射常用的操作。
 * @author Malcolm
 *
 */
public class RefectUtils {
	
	/**
	 * 调用obj对象对应属性fieldName的get方法。
	 * @param fieldName 属性名称
	 * @param obj 调用的对象
	 * @return 返回属性的值
	 */
	public static Object invokeGet(String fieldName,Object obj){
		try {
			Method m = obj.getClass().getMethod("get"+StringUtils.changeFirstToUpper(fieldName), null);
			return m.invoke(obj, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}
	
	/**
	 * 调用obj对象对应属性fieldName的set方法。
	 * @param obj 所调用的对象
	 * @param columnName 列的名称
	 * @param columnValue 列的值
	 */
	public static void invokeSet(Object obj,String columnName,Object columnValue){
		try {
			if(columnValue!=null){
				Method m = obj.getClass().getDeclaredMethod("set"+StringUtils.changeFirstToUpper(columnName), columnValue.getClass());
				m.invoke(obj, columnValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
