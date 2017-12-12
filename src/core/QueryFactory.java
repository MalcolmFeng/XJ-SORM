package core;
/**
 * 根据配置文件，生产对应的Query实现类的工厂
 * @author Malcolm
 *
 */
public class QueryFactory {
	
	private static Query prototypeObj;
	
	static{
		//工厂作为入口，初始化以下表结构信息。
		System.out.println("作为程序的主入口，在QueryFactory的static中加载"+TableContext.class);
		try {
			//对原型对象进行初始化，初始化的值为配置文件中设定的那个Query类
			Class c = Class.forName(DBManager.conf.getQueryClass());
			prototypeObj = (Query) c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 提供对外的静态方法 获取原型对象克隆的Query对象
	 * @return 返回一个克隆Query实现类对象
	 */
	public static Query createQuery(){ 
		try {
			return (Query) prototypeObj.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
