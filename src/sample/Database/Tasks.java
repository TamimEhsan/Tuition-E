package sample.Database;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tasks extends RecursiveTreeObject<Tasks> {
    //public ObservableValue<String> taskName;
    public int serial;
    public StringProperty taskName;
    public StringProperty taskDescription;
    public StringProperty taskDeadline;

    public Tasks(int serial,String name,String desc,String deadline){
        this.serial = serial;
        taskName = new SimpleStringProperty(name);
        taskDescription = new SimpleStringProperty(desc);
        taskDeadline = new SimpleStringProperty(deadline);
    }
}
