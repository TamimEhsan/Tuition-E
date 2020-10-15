package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class Profile {
    Preferences preferences;
    @FXML private Label userName;
    @FXML private Label userDescription;
    @FXML private Circle userImage;
    public void initialize(){
        preferences = Preferences.userNodeForPackage(Controller.class);
        userName.setText(preferences.get("username","Jon Doe"));
        userDescription.setText(preferences.get("userDescription",""));
        //System.out.println(preferences.get("userImagePath","error"));
        File file = new File(preferences.get("userImagePath","G:\\JavaFX\\Dashboard\\src\\sample\\Images\\profileMan.png"));
        if(file!=null && file.exists()) {
            Image image = new Image(file.toURI().toString());
            userImage.setFill(new ImagePattern(image));
        }
    }
    public void goToHome(ActionEvent event) throws IOException {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ViewFXML/sample.fxml"));
        stageTheEventSourceNodeBelongs.setScene(new Scene(root));
    }
    public void goToEdit(ActionEvent event) throws IOException {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ViewFXML/editProfile.fxml"));
        stageTheEventSourceNodeBelongs.setScene(new Scene(root));
    }
}
