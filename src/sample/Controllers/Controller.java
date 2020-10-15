package sample.Controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.DataInstances.ControllertStatus;
import sample.DataInstances.Notifications;
import sample.Network.Server;
import sample.DataInstances.TransferObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.prefs.Preferences;

public class Controller {
    private FileChooser fileChooser;
    @FXML private Label directoryLabel;
    @FXML private JFXButton connectionButton;
    @FXML private ListView notificationList;
    private Preferences preferences;
    private static Controller instance;

    public Controller(){
        instance = this;
    }
    public static Controller getInstance(){
        return instance;
    }
    public void initialize() {
        ControllertStatus.getInstance().setRunning(true);
        addNotification();
        checkConnection();
        preferences = Preferences.userNodeForPackage(Controller.class);
        directoryLabel.setText(preferences.get("downloadDirectory", "C:\\Users\\User\\Desktop"));
        fileChooser = new FileChooser();
    }



    public void handleButtonAction(ActionEvent event) throws IOException {
        ControllertStatus.getInstance().setRunning(false);
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ViewFXML/shareScreen.fxml"));
        stageTheEventSourceNodeBelongs.setScene(new Scene(root));
    }

    public void sendFileEvent(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if( file == null || file.exists() == false ){
            return;
        }
        System.out.println(file.getAbsolutePath());
        TransferObject transferObject = new TransferObject();
        transferObject.setFile(file);
        if (Server.getInstance().isConnected()) {
            Server.getInstance().send(transferObject);
        }
    }

    public void goToViewShare(ActionEvent event) throws IOException {
        ControllertStatus.getInstance().setRunning(false);
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ViewFXML/viewShare.fxml"));
        stageTheEventSourceNodeBelongs.setScene(new Scene(root));
    }

    public void goToRenoteControl(ActionEvent event) throws IOException {
        ControllertStatus.getInstance().setRunning(false);
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ViewFXML/remoteControl.fxml"));
        stageTheEventSourceNodeBelongs.setScene(new Scene(root));
    }

    public void goToChat(ActionEvent event) throws IOException {
        ControllertStatus.getInstance().setRunning(false);
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ViewFXML/chat.fxml"));
        stageTheEventSourceNodeBelongs.setScene(new Scene(root));
    }

    public void goToCanvas(ActionEvent event) throws IOException {
        ControllertStatus.getInstance().setRunning(false);
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ViewFXML/canvas.fxml"));
        stageTheEventSourceNodeBelongs.setScene(new Scene(root));
    }

    public void goToProfile(ActionEvent event) throws IOException {
        ControllertStatus.getInstance().setRunning(false);
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ViewFXML/profile.fxml"));
        stageTheEventSourceNodeBelongs.setScene(new Scene(root));
    }
    public void goToAudioCall(ActionEvent event) throws IOException {
        ControllertStatus.getInstance().setRunning(false);
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ViewFXML/audioCall.fxml"));
        stageTheEventSourceNodeBelongs.setScene(new Scene(root));
    }
    public void goToTasks(ActionEvent event) throws IOException {
        ControllertStatus.getInstance().setRunning(false);
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ViewFXML/taskView.fxml"));
        stageTheEventSourceNodeBelongs.setScene(new Scene(root));
    }
    public void checkConnection(){
        if( Server.getInstance().isConnected() ){
            connectionButton.setText("Connected");
        } else if( Server.getInstance().isSearching() ){
            connectionButton.setText("Wating");
        } else{
            connectionButton.setText("Start");
        }
    }
    public void startConnection(){
        if( Server.getInstance().isConnected() == false && Server.getInstance().isSearching() == false ){
            Server.getInstance().setConnection();
            connectionButton.setText("Wating");
        }
    }
    public void setConnectionButtonText(String s){
        connectionButton.setText(s);
    }

    public void choosedirectory(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory == null || selectedDirectory.exists() == false) {
            System.out.println("pera");
            return;
        }
        System.out.println(selectedDirectory.getAbsolutePath());
        directoryLabel.setText(selectedDirectory.getAbsolutePath());
        preferences.put("downloadDirectory", selectedDirectory.getAbsolutePath());

    }
    public void addNotification(){
        ArrayList<String> list = Notifications.getInstance().getList();
        for(int i=0;i<list.size();i++){
            Label label = new Label();
            label.setText(list.get(i));
            label.setFont(new Font(20));
            label.setWrapText(true);
            label.setMaxWidth(500);
            label.setTextAlignment(TextAlignment.JUSTIFY);
            label.setPadding(new Insets(5));
            //label.setBorder(new Border(5));
            notificationList.getItems().add(label);
        }
    }
    public void addNewNotification(String s){
        Label label = new Label();
        label.setText(s);
        label.setFont(new Font(20));
        label.setWrapText(true);
        label.setMaxWidth(500);
        label.setTextAlignment(TextAlignment.JUSTIFY);
        label.setPadding(new Insets(5));
        //label.setBorder(new Border(5));
        notificationList.getItems().add(label);
    }
   
}
