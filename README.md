# XJ-SORM

#### AutoCodeCreator Framework based on Spring Framework and Mybatis and some relationship database.

#### This is a Super-ORM(Object Relationship Mapper) which can automatically create directory and its code if your Dynamic web project based on SSM(Spring/Spring web mvc/Mybatis) and some relationship databases.

#### It can connect a database you appointed,load all tables in the db(or the tables you appointed) to the memory,and automatically  create the code in your module which you can appoint,such as Bean Controller Service Dao and Mapper.

#### The following is a little tutorial help you start quickly.
    
##### If you want to create all module's code in all tables in db, you can write the following code:

    import core.TableContext;
    public class Test {
      public static void main(String[] args) {
        TableContext tableContext = new TableContext();
        tableContext.loadDB();
        tableContext.createAllFiles();
        System.out.println("Create finished.");
      }
    }
   
   
#####If you want to create all module's code in the tables you appointed, you can write the following code:

    import core.TableContext;
    public class Test {
      public static void main(String[] args) {
        TableContext tableContext = new TableContext();
        tableContext.loadDB("table1","table2","table3");
        tableContext.createAllFiles();
        System.out.println("Create finished.");
      }
    }

##### If you want to create the module you appointed and all tables in db, you can write  the following code:

    import java.util.Map;

    import bean.TableInfo;
    import convertor.MysqlTypeConvertor;
    import core.TableContext;
    import creator.JavaBeanCreator;
    import creator.JavaControllerCreator;
    import creator.JavaDaoCreator;
    import creator.JavaServiceCreator;
    import creator.XMLMapperCreatorMySQL;

    public class Test {
      public static void main(String[] args) {
        TableContext tableContext = new TableContext();
        tableContext.loadDB();

        Map<String, TableInfo> tablesMap = tableContext.tables;

        for (TableInfo tableInfo : tablesMap.values()) {
          //Bean Module creator
          JavaBeanCreator javaBeanCreator = new JavaBeanCreator(tableInfo, new MysqlTypeConvertor(), "Bean","java");
          javaBeanCreator.createJavaFile();

          //Dao Module creator
          JavaDaoCreator javaDaoCreator = new JavaDaoCreator(tableInfo, new MysqlTypeConvertor(), "Dao","java");
          javaDaoCreator.createJavaFile();

          //Service Module creator
          JavaServiceCreator javaServiceCreator = new JavaServiceCreator(tableInfo, new MysqlTypeConvertor(), "Service","java");
          javaServiceCreator.createJavaFile();

          //Controller Module creator
          JavaControllerCreator javaControllerCreator = new JavaControllerCreator(tableInfo, new MysqlTypeConvertor(), "Controller","java");
          javaControllerCreator.createJavaFile();

          //Mapper Module creator
          XMLMapperCreatorMySQL xmlMapperCreatorMySQL = new XMLMapperCreatorMySQL(tableInfo, null, null, null, null, null, null);
          xmlMapperCreatorMySQL.createXMLFile(tableInfo);
        }

        System.out.println("Create finished.");
      }
    }
