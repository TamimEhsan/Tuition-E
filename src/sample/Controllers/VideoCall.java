package sample.Controllers;

import com.github.sarxos.webcam.Webcam;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import sample.DataInstances.Screenshare;
import sample.Network.Server;
import sample.DataInstances.TransferObject;

import java.io.IOException;

public class VideoCall {

    @FXML private ImageView imageViewTry;
    private WritableImage image;
    private long prevt = 0;
    private Mytimer anim;
    private Webcam webcam;
    private TransferObject transferObject;
    public void initialize(){
        transferObject = new TransferObject();
        webcam = Webcam.getDefault();
        webcam.open();
        anim = new Mytimer();
        anim.start();
    }
    public void goToHome(ActionEvent event) throws IOException {
        anim.stop();
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ViewFXML/sample.fxml"));
        stageTheEventSourceNodeBelongs.setScene(new Scene(root));
    }



    private class Mytimer extends AnimationTimer {
        @Override
        public void handle(long l) {
            if( l - prevt>500000000 ){
                image = Screenshare.getInstance().getImage();
                if( image!=null ){
                    imageViewTry.setImage(image);

                    double w = 0;
                    double h = 0;

                    double ratioX = imageViewTry.getFitWidth() / image.getWidth();
                    double ratioY = imageViewTry.getFitHeight() / image.getHeight();

                    double reducCoeff = 0;
                    if(ratioX >= ratioY) {
                        reducCoeff = ratioY;
                    } else {
                        reducCoeff = ratioX;
                    }

                    w = image.getWidth() * reducCoeff;
                    h = image.getHeight() * reducCoeff;

                    imageViewTry.setX((imageViewTry.getFitWidth() - w) / 2);
                    imageViewTry.setY((imageViewTry.getFitHeight() - h) / 2);
                    image = SwingFXUtils.toFXImage(webcam.getImage(),null);
                    transferObject.setImage(image);
                    if(Server.getInstance().isConnected()){
                        Server.getInstance().send(transferObject);
                    }
                }
                prevt = l;
            }
        }
    }

}
