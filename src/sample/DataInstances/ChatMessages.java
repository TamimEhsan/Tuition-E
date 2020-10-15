package sample.DataInstances;


import java.util.ArrayList;
import java.util.Arrays;
public class ChatMessages {
    private static ChatMessages instance = null;
    private ArrayList<ArrayList<String>> list;
    private boolean isActive = false;

    private ChatMessages(){
        list = new ArrayList<ArrayList<String>>();
    }
    public static ChatMessages getInstance(){
        if( instance == null ){
            instance = new ChatMessages();
        }
        return instance;
    }

    public ArrayList<ArrayList<String>> getList(){
        return list;
    }
    public void addToList(String type,String message){
        list.add(new ArrayList<String>(Arrays.asList(type,message)));
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
