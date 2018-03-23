# XJ-SORM

（一个自动代码生成器，基于 spring mvc mybatis 和关系型数据库项目。可以自动根据关系型数据库表结构，自动生成项目的 Bean、Mapper、Service、Controller 增删改查的基本代码，并创建包路径和java 文件）

An AutoCodeCreator Framework based on Spring Framework and Mybatis and some relationship database.

This is a Super-ORM(Object Relationship Mapper) which can automatically create directory and its code if your Dynamic web project based on SSM(Spring/Spring web mvc/Mybatis) and some relationship databases.

It can connect a database you appointed,load all tables in the db(or the tables you appointed) to the memory,and automatically  create the code in your module which you can appoint,such as Bean Controller Service Dao and Mapper.

The following is a little tutorial help you start quickly.
    
If you want to create all module's code in all tables in db(is MySQL), you can write the following code:

	import core.TableContext;
	public class Test {
		public static void main(String[] args) {
			TableContext tableContext = new TableContext();
			tableContext.loadDB();
			tableContext.createAppointedModuleFiles(true, true, true, true, true, true);
			System.out.println("Create finished.");
		}
	}
   
   
If you want to create all module's code in the tables you appointed and your db is MySQL, you can write the following code:

	import core.TableContext;
	public class Test {
		public static void main(String[] args) {
			TableContext tableContext = new TableContext();
			tableContext.loadDB("table1", "table2");
			tableContext.createAppointedModuleFiles(true, true, true, true, true, true);
			System.out.println("Create finished.");
		}
	}

If you want to create the module you appointed and all tables in db(is MySQL), you can write  the following code:

	import core.TableContext;
	public class Test {
		public static void main(String[] args) {
			TableContext tableContext = new TableContext();
			tableContext.loadDB("table1", "table2");
			tableContext.createAppointedModuleFiles(true, true, true, true, true, true, "Bean","Dao");
			System.out.println("Create finished.");
		}
	}
 
Ps: the Class XMLMapperCreatorMySQL's constructor's params:

    //(add, update, queryRows, queryAll, queryList, delete)
    
If true, then the Mapper module will create the function.
