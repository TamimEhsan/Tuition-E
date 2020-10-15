package sample.Controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import sample.Database.Databasehelper;
import sample.Database.Tasks;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

public class TaskView {
    @FXML private JFXTreeTableView<Tasks> taskTable;
    @FXML private JFXTextField filterTasks;
    private ObservableList<Tasks> tasksObservableList;

    public void initialize(){
        addTasks();
    }

    public void goToHome(ActionEvent event) throws IOException {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ViewFXML/sample.fxml"));
        stageTheEventSourceNodeBelongs.setScene(new Scene(root));
    }

    private void addTasks(){
        JFXTreeTableColumn<Tasks,String> taskName = new JFXTreeTableColumn<>("Name");
        taskName.setPrefWidth(195);
        taskName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Tasks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Tasks, String> tasksStringCellDataFeatures) {
                return tasksStringCellDataFeatures.getValue().getValue().taskName;
            }
        });

        JFXTreeTableColumn<Tasks,String> taskdesc = new JFXTreeTableColumn<>("Description");
        taskdesc.setPrefWidth(350);
        taskdesc.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Tasks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Tasks, String> tasksStringCellDataFeatures) {
                //taskdesc.prefWidthProperty().bind(taskTable.widthProperty().subtract(300.0));
                return tasksStringCellDataFeatures.getValue().getValue().taskDescription;
            }
        });

        JFXTreeTableColumn<Tasks,String> taskDeadLine = new JFXTreeTableColumn<>("Deadline");
        taskDeadLine.setPrefWidth(150);
        taskDeadLine.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Tasks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Tasks, String> tasksStringCellDataFeatures) {
                return tasksStringCellDataFeatures.getValue().getValue().taskDeadline;
            }
        });
        taskDeadLine.setPrefWidth(100);

        tasksObservableList = FXCollections.observableArrayList();
        ArrayList<Tasks> data = Databasehelper.getInstance().getAllData();
        for( int i=0;i<data.size();i++){
            // System.out.println(data.get(i).serial);
            tasksObservableList.add(data.get(i));

        }
        TreeItem<Tasks> root = new RecursiveTreeItem<Tasks>(tasksObservableList, RecursiveTreeObject::getChildren);
        taskTable.getColumns().setAll(taskName,taskdesc,taskDeadLine);

        taskTable.setRoot(root);
        taskTable.setShowRoot(false);
        filterTasks.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                taskTable.setPredicate(new Predicate<TreeItem<Tasks>>() {
                    @Override
                    public boolean test(TreeItem<Tasks> tasksTreeItem) {
                        boolean flag1 = tasksTreeItem.getValue().taskName.getValue().contains(t1);
                        boolean flag2 = tasksTreeItem.getValue().taskDescription.getValue().contains(t1);
                        boolean flag3 = tasksTreeItem.getValue().taskDeadline.getValue().contains(t1);
                        return flag1 || flag2 || flag3;
                    }
                });
            }
        });
    }

    public void addData(){
        // Create the custom dialog.
        Dialog<Pair<String, Pair<String,String>>> dialog = new Dialog<>();
        dialog.setTitle("Add new task");
        dialog.setHeaderText("Add Name, Description and deadline for your task");

// Set the icon (must be included in the project).


// Set the button types.
        ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        JFXTextField name = new JFXTextField();
        name.setPromptText("Name of Task");
        name.setPrefWidth(250);
        JFXTextField desc = new JFXTextField();
        desc.setPromptText("Description of the task");
        JFXTextField deadline = new JFXTextField();
        deadline.setPromptText("Set deadline");
        grid.add(new Label("Username:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(desc, 1, 1);
        grid.add(new Label("Deadline:"), 0, 2);
        DatePicker datePicker = new DatePicker();
        datePicker.setPrefWidth(250);
        grid.add(datePicker,1,2);
// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(() -> name.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                String date="";
                if(datePicker.getValue()!=null){
                    date= DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(datePicker.getValue()).toString();
                }
                // System.out.println();
                //System.out.println(datePicker.getValue().format(DateTimeFormatter.BASIC_ISO_DATE).toString());
                return new Pair<>(name.getText(), new Pair<>(desc.getText(),date));
            }
            return null;
        });

        Optional<Pair<String, Pair<String,String>>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            int index = Databasehelper.getInstance().addData(usernamePassword.getKey(),usernamePassword.getValue().getKey(),usernamePassword.getValue().getValue());
            if(index!=-1) {
                tasksObservableList.add(new Tasks(index, usernamePassword.getKey(), usernamePassword.getValue().getKey(), usernamePassword.getValue().getValue()));
            }
            taskTable.scrollTo(0);
            //System.out.println("Name=" + usernamePassword.getKey() + ", Des=" + usernamePassword.getValue().getKey()+" Deadline:"+usernamePassword.getValue().getValue());
        });


    }
    public void deleteData(){
        if(taskTable.getSelectionModel().getSelectedItem() != null){
            Tasks task = taskTable.getSelectionModel().getSelectedItem().getValue();
            Databasehelper.getInstance().deleteData(task.serial);
            tasksObservableList.remove(task);
        }
    }
}
