package test;

import core.TableContext;

public class Test {

	public static void main(String[] args) {
		TableContext aaa = new TableContext();

		aaa.loadDB();
		aaa.createAllFiles();
		System.out.println("创建完毕");
//		Emp emp = new Emp();
//		Object value = q.queryRow("select age from emp where id=?",emp.getClass(),new Object[]{1});
//		Object value = q.queryValue("select count(*) from emp where salary>?",emp.getClass(),new Object[]{3000});
//		Number value = q.queryNumber("select count(*) from emp where salary>?",emp.getClass(),new Object[]{3000});
//		System.out.println(value); 
		
		
//		Emp emp = new Emp();
//		List<Emp> list = q.queryRows("select id,name,age,salary from emp where salary>? and age<?", emp.getClass(),new Object[]{10000,25});
//		for(Emp obj :list){
//			System.out.println(obj.getName()+"--->"+obj.getAge()+"--->"+obj.getSalary());
//		}
//		
		
//		Emp emp = new Emp();
//		emp.setId(4);
//		emp.setAge(32);
//		emp.setName("fengdian");
//		q.update(emp, new String[]{"name","age"});
//		q.insert(emp);
//		q.delete(emp);
//		q.delete(Emp.class,emp.getId());
		
	}

}
