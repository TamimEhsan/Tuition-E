package sample.DataInstances;

import sample.Controllers.Controller;

import javax.management.Notification;
import java.util.ArrayList;

public class Notifications {
    private static Notifications instance;
    private ArrayList<String> list;
    private Notifications(){
        list = new ArrayList<>();
    }
    public static Notifications getInstance(){
        if( instance == null ){
            instance = new Notifications();
        }
        return instance;
    }
    public ArrayList<String> getList(){
        return list;
    }
    public void addData(String s){
        list.add(s);
        if( ControllertStatus.getInstance().isRunning() ){
            Controller.getInstance().addNewNotification(s);
        }
    }
}
