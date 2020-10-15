package sample.Controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class EditProfile {
    private Preferences preferences;
    @FXML private JFXTextField editName;
    @FXML private JFXTextField editDescription;
    @FXML private Label editImagePath;

    public void initialize(){
       // System.out.println("here");
        preferences = Preferences.userNodeForPackage(Controller.class);
        editName.setText(preferences.get("username","Jon Doe"));
        editDescription.setText(preferences.get("userDescription","Jon Doe"));
        editImagePath.setText(preferences.get("userImagePath","G:\\JavaFX\\Dashboard\\src\\sample\\Images\\profileMan.png"));

    }

    public void goToProfile(ActionEvent event) throws IOException {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ViewFXML/profile.fxml"));
        stageTheEventSourceNodeBelongs.setScene(new Scene(root));
    }
    public void chooseImage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if( file!=null ){
            System.out.println(file.getAbsolutePath());
            preferences.put("userImagePath",file.getAbsolutePath());
            editImagePath.setText(file.getAbsolutePath());
        }

    }
    public void saveInfo(ActionEvent event) throws IOException {
        String name = editName.getText().toString().trim();
        String desc = editDescription.getText().toString().trim();
        String path = editImagePath.getText().toString().trim();
        preferences.put("username",name);
        preferences.put("userDescription",desc);
        preferences.put("userImagePath",path);
    }
}
