package sample.Controllers;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import sample.DataInstances.Screenshare;
import sample.Network.Server;
import sample.DataInstances.TransferObject;

import java.io.IOException;

public class WhiteBoard implements ChangeListener {
    @FXML private JFXSlider slider;
    @FXML private ColorPicker colorPicker;
   // @FXML private JFXColorPicker colorPicker;
    private double brushSize;
    private Color brushColor;
    @FXML private Canvas canvasThis;
    @FXML private ImageView canvasThat;
    @FXML private JFXToggleButton toggleAnnote;
    @FXML private JFXTextField addTextLabel;
    @FXML private JFXSlider textSize;
    private GraphicsContext graphicsContext;
    private long prevt = 0;
    private Mytimer mytimer;
    private WritableImage image;
    private TransferObject transferObject;
    private WritableImage imageThat;
    private boolean annote = true;
    private boolean hasText = false;
    private String textToAdd;

    public void initialize(){
        graphicsContext = canvasThis.getGraphicsContext2D();
        initDraw(graphicsContext);
        addlisteners();
        brushSize = slider.getValue();
        colorPicker.setValue(Color.BLACK);
        brushColor = colorPicker.getValue();
        transferObject = new TransferObject();
        System.out.println(colorPicker.getValue());
        System.out.println(brushSize);
        slider.valueProperty().addListener(this);
        colorPicker.valueProperty().addListener(this);
        mytimer = new Mytimer();
        mytimer.start();
    }

    public void changeColor(){
        brushColor = colorPicker.getValue();
        graphicsContext.setStroke(brushColor);
    }
    @Override
    public void changed(ObservableValue observableValue, Object o, Object t1) {
        brushSize = slider.getValue();
        graphicsContext.setLineWidth(brushSize/10.0);
        //System.out.println(brushSize);
       // System.out.println(brushColor);
    }

    public void backtoHome(ActionEvent event) throws IOException {
        mytimer.stopNow();
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ViewFXML/sample.fxml"));
        stageTheEventSourceNodeBelongs.setScene(new Scene(root));
    }

    public void clearScreen(){
        graphicsContext.clearRect(5,5,graphicsContext.getCanvas().getWidth()-10,graphicsContext.getCanvas().getHeight()-10);
    }

    public void switchAnnote(){
        if( toggleAnnote.isSelected() ){
            annote = true;
        } else{
            annote = false;
        }
    }
    public void addText(){
        textToAdd = addTextLabel.getText().toString().trim();
        addTextLabel.setText("");
        hasText = true;
    }

    private void addlisteners(){
        canvasThis.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        if( hasText ){
                            graphicsContext.setFont(new Font(textSize.getValue()));
                            graphicsContext.setFill(Color.BLACK);
                            graphicsContext.fillText(textToAdd,event.getX(),event.getY());
                            graphicsContext.setStroke(brushColor);
                            hasText = false;
                            textToAdd = "";
                        }else if(annote){
                            //graphicsContext.strokeOval(event.getX()-brushSize/10.0, event.getY()-brushSize/10.0, brushSize/5.0, brushSize/5.0);
                            graphicsContext.beginPath();
                            graphicsContext.moveTo(event.getX(), event.getY());
                            graphicsContext.stroke();
                        }
                    }
                });

        canvasThis.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        if(annote){
                            graphicsContext.lineTo(event.getX(), event.getY());
                            graphicsContext.stroke();
                        }
                    }
                });

        canvasThis.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {

                    }
                });


    }

    private void initDraw(GraphicsContext gc){
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        gc.setFill(Color.TRANSPARENT);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(10);

        gc.fill();
        gc.strokeRect(
                0,              //x of the upper left corner
                0,              //y of the upper left corner
                canvasWidth,    //width of the rectangle
                canvasHeight);  //height of the rectangle

        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

    }

    private class Mytimer extends AnimationTimer{

        @Override
        public void handle(long l) {
            if( l - prevt>100000000 ){
                imageThat = Screenshare.getInstance().getImage();
                canvasThat.setImage(imageThat);
                image = canvasThis.snapshot(null, null);
                transferObject.setImage(image);
                if( Server.getInstance().isConnected() ){
                    Server.getInstance().send(transferObject);
                }
                prevt = l;
            }
        }
        public void stopNow(){
            stop();
        }
    }
}
