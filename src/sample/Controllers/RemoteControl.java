package sample.Controllers;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import sample.DataInstances.Screenshare;
import sample.Network.Server;
import sample.DataInstances.TransferObject;

import java.io.FileNotFoundException;
import java.io.IOException;

public class RemoteControl {
    private int first = 1;
    @FXML private ImageView imageViewTry;
    private Robot robot;
    private WritableImage image;
    private long prevt = 0;
    private int index = 0;
    private long thistime;
    private AnimationTimer anim;
    private TransferObject transferObject;
    private Image defaultImage;// = "../Images/bank.png";
    private double tranX = 0.0;
    private double tranY = 0.0;
    private double w = 0.0;
    private double h = 0.0;
    private double W = 0.0;
    private double H = 0.0;
    private double reducCoeff = 1.0;
    public void initialize() throws FileNotFoundException {
        System.out.println("init");
        //defaultImage = new Image("../Images/bank.png");
        //transferObject = new TransferObject();
        thistime = System.currentTimeMillis();
       // robot = new Robot();
        addTimer();
        addListenrs();

    }
    private void addTimer(){
        anim = new Mytimer();
        anim.start();
    }

    public void handleButtonAction2(ActionEvent event) throws IOException {
        System.out.println("You clicked me!2");
        anim.stop();
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ViewFXML/sample.fxml"));
        stageTheEventSourceNodeBelongs.setScene(new Scene(root));
    }

    private class Mytimer extends AnimationTimer{
        @Override
        public void handle(long l) {
            if( l - prevt>500000000 ){
                image = Screenshare.getInstance().getImage();
                if( image!=null ){
                    if( first>0 ){
                        System.out.println("image loaded");
                        first = 0;
                    }
                    imageViewTry.setImage(image);
                    w = 0.0;
                    h = 0.0;

                    double ratioX = imageViewTry.getFitWidth() / image.getWidth();
                    double ratioY = imageViewTry.getFitHeight() / image.getHeight();

                    reducCoeff = 1.0;
                    if(ratioX >= ratioY) {
                        reducCoeff = ratioY;
                    } else {
                        reducCoeff = ratioX;
                    }

                    w = image.getWidth() * reducCoeff;
                    h = image.getHeight() * reducCoeff;

                    imageViewTry.setX((imageViewTry.getFitWidth() - w) / 2);
                    imageViewTry.setY((imageViewTry.getFitHeight() - h) / 2);
                    tranX = (imageViewTry.getFitWidth() - w) / 2;
                    tranY = (imageViewTry.getFitHeight() - h) / 2;

                }



                index+=1;
                if(index>100){
                    long end = System.currentTimeMillis();
                    System.out.println((end-thistime));
                    thistime = end;
                    index = 0;
                }
                prevt = l;
            }
        }
        public void stopNow(){
            stop();
        }
    }

    private void addListenrs(){
        imageViewTry.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println((int)(mouseEvent.getX()-tranX)+" "+(int)(mouseEvent.getY()-tranY));
                TransferObject clicker = new TransferObject();
                clicker.setClickCount(mouseEvent.getClickCount());
                clicker.setClickX( (mouseEvent.getX()-tranX)/reducCoeff);
                clicker.setClickY( (mouseEvent.getY()-tranY)/reducCoeff);
                clicker.setClickType(mouseEvent.getButton());
                if( Server.getInstance().isConnected() ){
                    Server.getInstance().send(clicker);
                }
            }
        });
        imageViewTry.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                TransferObject clicker = new TransferObject();
                clicker.setKeyType(keyEvent.getCharacter());
                if( Server.getInstance().isConnected() ){
                    Server.getInstance().send(clicker);
                }
            }
        });
    }


}
