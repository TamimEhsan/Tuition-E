package sample.Controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sample.DataInstances.ChatMessages;
import sample.DataInstances.TransferObject;
import sample.Network.Server;


import javax.print.DocFlavor;
import java.io.IOException;
import java.util.ArrayList;

public class Chat {
    @FXML private ListView chatListView;
    @FXML private TextField messageTextFiled;
    private TransferObject transferObject;

    public static Chat chat = null;
    public Chat(){
        chat = this;
        System.out.println("called");
    }
    public static Chat getInstance(){
        return chat;
    }
    public void initialize(){
        transferObject = new TransferObject();
        ChatMessages.getInstance().setActive(true);
        ArrayList<ArrayList<String>> list = ChatMessages.getInstance().getList();
        for(int i=0;i<list.size();i++){
            Label label = new Label();
            label.setText(list.get(i).get(1));
            System.out.println(list.get(i).get(1));
            label.setMaxWidth(300);
            label.setTextFill(Color.web("#ffffff", 1));
            label.setTextAlignment(TextAlignment.JUSTIFY);
            label.setWrapText(true);
            label.setPadding(new Insets(5));
            HBox y = new HBox();
            VBox x = new VBox();
            if( list.get(i).get(0).equals("client") ) {
                x.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(15), null)));
            } else{
                x.setBackground(new Background(new BackgroundFill(Color.web("#1698f5",1), new CornerRadii(15), null)));
            }
            x.setMaxWidth(200);
            x.getChildren().add(label);
            y.getChildren().add(x);
            if( list.get(i).get(0).equals("server") ){
                y.setAlignment(Pos.CENTER_RIGHT);
            } else{
                y.setAlignment(Pos.CENTER_LEFT);
            }
            chatListView.getItems().add(y);
        }
        chatListView.scrollTo(chatListView.getItems().size());
    }
    public void backtoHome(ActionEvent event) throws IOException {
        ChatMessages.getInstance().setActive(false);
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ViewFXML/sample.fxml"));
        stageTheEventSourceNodeBelongs.setScene(new Scene(root));
    }
    public void addToChat(String type,String message){
        Label label = new Label();
        label.setText(message);
        label.setMaxWidth(200);
        label.setTextAlignment(TextAlignment.JUSTIFY);
        label.setWrapText(true);
        label.setPadding(new Insets(5));
        label.setTextFill(Color.web("#ffffff", 1));
        HBox y = new HBox();
        label.setBackground(new Background(new BackgroundFill(Color.GREY,new CornerRadii(15),null)));
        y.getChildren().add(label);
        y.setAlignment(Pos.CENTER_LEFT);
        chatListView.getItems().add(y);
        chatListView.scrollTo(chatListView.getItems().size());
    }
    public void addToChat(){
        String message = messageTextFiled.getText().toString().trim();
        //String message = "asd";
        System.out.println(message);
        messageTextFiled.setText("");
        if( message == "" )
            return;
        Label label = new Label();
        label.setText(message);
        label.setMaxWidth(200);
        label.setTextAlignment(TextAlignment.JUSTIFY);
        label.setWrapText(true);
        label.setPadding(new Insets(5));
        HBox y = new HBox();
        label.setBackground(new Background(new BackgroundFill(Color.web("#1698f5",1),new CornerRadii(15),null)));
        label.setTextFill(Color.web("#ffffff", 1));
        y.getChildren().add(label);
        y.setAlignment(Pos.CENTER_RIGHT);
        chatListView.getItems().add(y);
        chatListView.scrollTo(chatListView.getItems().size());
        ChatMessages.getInstance().addToList("server",message);
        if(Server.getInstance().isConnected()){
            transferObject.setString(message);
            Server.getInstance().send(transferObject);
        }
    }
}
