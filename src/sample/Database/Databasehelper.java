package sample.Database;

import java.sql.*;
import java.util.ArrayList;

public class Databasehelper {
    private static Databasehelper instance = null;
    private Connection connection;

    private Databasehelper(){
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:Database\\TuitionE.db");
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS tasks " +
                    "(ID INTEGER PRIMARY KEY,taskName TEXT, taskDesc TEXT ,deadline TEXT)");
        }catch (SQLException e){
            System.out.println("error");
        }
    }
    public static Databasehelper getInstance(){
        if( instance == null ){
            instance = new Databasehelper();
        }
        return instance;
    }
    public ArrayList<Tasks> getAllData(){
        String sql = "SELECT * FROM tasks";
        ArrayList<Tasks> tasksArrayList = new ArrayList<>();
        try(Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)){
            while ( resultSet.next() ){
                Tasks tasks = new Tasks(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
                tasksArrayList.add(tasks);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return tasksArrayList;
    }

    public void deleteData(final int index){
        try( Statement statement = connection.createStatement()){
            statement.execute("DELETE FROM tasks WHERE ID = '"+index+"'");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public int addData(String name,String desc,String deadline){
        String sql = "INSERT INTO tasks (taskName, taskDesc, deadline) " +
                "VALUES ( '"+name+"' , '"+desc+"' , '"+deadline+"' )";
        try(Statement statement = connection.createStatement()){
            statement.execute(sql);
            ResultSet resultSet = statement.executeQuery("select last_insert_rowid()");
            System.out.println(resultSet.getInt(1));
            return resultSet.getInt(1);
//            System.out.println(resultSet.getString(2));
            // Then grab the bottom 32-bits as the unique ID of the row.
            //
            //int LastRowID = (int)LastRowID64;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
}
