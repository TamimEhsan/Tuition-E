package sample.Controllers;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import sample.Network.Server;
import sample.DataInstances.TransferObject;

import javax.sound.sampled.*;
import java.io.IOException;

public class AudioCall {
    @FXML private Circle circle1;
    @FXML private Circle circle2;
    private Mytimer anim;
    private long prevt = 0;
    private byte[] audiobuffer;
    private static AudioInputStream audioInputStream;
    private TargetDataLine line;
    private DataLine.Info info;
    private float rate = 44100.0f;
    private int channels = 2;
    private int samplesize = 16;
    private boolean bigEndian = false;
    private AudioFormat format;
    private AudioFormat.Encoding encoding;
    private boolean ongoing = false;
    private TransferObject transferObject;
    public void initialize(){
      /*  transferObject = new TransferObject();
        encoding = AudioFormat.Encoding.PCM_SIGNED;
        format = new AudioFormat(encoding,rate,samplesize,channels,(samplesize/8)*channels,rate,bigEndian);
        info = new DataLine.Info(TargetDataLine.class,format);
        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line matching " + info + " not supported.");
            return;
        }
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }*/
        anim = new Mytimer();

    }
    public void goToHome(ActionEvent event) throws IOException {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ViewFXML/sample.fxml"));
        stageTheEventSourceNodeBelongs.setScene(new Scene(root));
        anim.stop();
    }

    public void startCall(){
        anim.start();
      /*  int buffsize = line.getBufferSize()/5;
        buffsize += 512;

        try {
            line.open(format);
            line.start();

            int numBytesRead;
            byte[] data = new byte[4096];

            while (ongoing) {
                // Read the next chunk of data from the TargetDataLine.
                numBytesRead = line.read(data, 0, data.length);
                transferObject.setAudioData(data);
                if(Server.getInstance().isConnected()){
                    Server.getInstance().send(transferObject);
                }
                // Save this chunk of data
            }
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }*/

    }
    public void endCall(){
        ongoing = false;
        anim.stop();
        circle1.setRadius(0);
        circle2.setRadius(0);
    }

    private class Mytimer extends AnimationTimer{
        @Override
        public void handle(long l) {
            if( l - prevt>50000000 ){
                double rad1 = circle1.getRadius();
                double rad2 = circle2.getRadius();
                rad1 = rad1+5.0;
                rad2 = rad2+5.0;
                if( rad1>100 ){
                    rad1 = 20.0;
                    rad2 = 5.0;
                }
                /*if(rad2>100){
                    rad2 = 10.0;
                }*/
                circle1.setRadius(rad1);
                circle2.setRadius(rad2);
                prevt = l;
            }
        }
    }
}
