# XJ-SORM

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
		tableContext.createAppointedModuleFiles(new MysqlTypeConvertor(),new XMLMapperCreatorMySQL(true, true, true, true, true, true));
        System.out.println("Create finished.");
      }
    }
   
   
If you want to create all module's code in the tables you appointed and your db is MySQL, you can write the following code:

    import core.TableContext;
    public class Test {
      public static void main(String[] args) {
        TableContext tableContext = new TableContext();
        tableContext.loadDB("table1", "table2");
		tableContext.createAppointedModuleFiles(new MysqlTypeConvertor(),new XMLMapperCreatorMySQL(true, true, true, true, true, false));
        System.out.println("Create finished.");
      }
    }

If you want to create the module you appointed and all tables in db(is MySQL), you can write  the following code:

    import core.TableContext;
    public class Test {
      public static void main(String[] args) {
        TableContext tableContext = new TableContext();
        tableContext.loadDB("table1", "table2");
		tableContext.createAppointedModuleFiles(new MysqlTypeConvertor(),new XMLMapperCreatorMySQL(true, true, true, true, true, true), "Bean","Dao");
        System.out.println("Create finished.");
      }
    }
 
Ps: the Class XMLMapperCreatorMySQL's constructor's params:

    //(add, update, queryRows, queryAll, queryList, delete)
    new XMLMapperCreatorMySQL(true, true, true, true, true, true)
    
If true, then the Mapper module will create the function.
