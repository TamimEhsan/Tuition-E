package sample.Network;

import javafx.application.Platform;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import sample.Controllers.Chat;
import sample.Controllers.Controller;
import sample.DataInstances.ChatMessages;
import sample.DataInstances.Notifications;
import sample.DataInstances.Screenshare;
import sample.DataInstances.TransferObject;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

import java.io.*;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.prefs.Preferences;

public class Server {
    private ServerSocket server;
    private Socket connection;

    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private static final String LOCALHOST = "127.0.0.1";
    private static final int TIMEOUT = 10000;
    public static int PORT = 26979;
    private Thread thread;
    private boolean connected;
    private TransferObject transferObject;
    private WritableImage image;
    private static Server instance = null;
    private boolean searching = false;

    private Server(){ }
    public static Server getInstance(){
        if( instance == null ){
            instance = new Server();
        }
        return instance;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean isSearching() {
        return searching;
    }

    public void setSearching(boolean searching) {
        this.searching = searching;
    }

    public void setConnection(){
        searching = true;
        new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println("started");
                    if( server!=null ){
                        server.close();
                    }
                    server = new ServerSocket(PORT,100);
                    // Create Connection
                    connection = server.accept();
                    // Input Stream from Server
                    out = new ObjectOutputStream(connection.getOutputStream());
                    // Output Stream to Server
                    in = new ObjectInputStream(connection.getInputStream());
                    connected = true;
                    searching = false;
                    System.out.println("connected");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Controller.getInstance().setConnectionButtonText("Connected");
                        }
                    });
                    getInput();
                    //  Main.showSuccess("Connected",2000);
                    // ResponseListen from InputStream
                    //responseListener = new ResponseListener(in);
                }
                catch (ConnectException e) {

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void send(TransferObject ob) {
        try{
            //System.out.println("before sending");
            out.writeObject(ob);
            //out.flush();
            out.reset();
            System.out.println("send");
        } catch (IOException e){
            connected = false;
        }
    }

    private void getInput(){
        new Thread(){
            @Override
            public void run() {
                while(connected){
                    try{
                        //System.out.println("here");
                        transferObject = (TransferObject) in.readUnshared();
                        //in.reset();
                        if( transferObject.getState() == 1 ) {
                            image = transferObject.getImage();
                            Screenshare.getInstance().setImage(image);
                        } else if( transferObject.getState() == 2 ){
                            Preferences preferences = Preferences.userNodeForPackage(Controller.class);
                            String destinationFolder = preferences.get("downloadDirectory","G:\\JavaFX\\New folder\\");
                            String outputfile = destinationFolder +"\\"+ transferObject.getFilename();
                            System.out.println(outputfile);
                            File dstfile = new File(outputfile);
                            FileOutputStream fileOutputStream = new FileOutputStream(dstfile);
                            fileOutputStream.write(transferObject.getFileData());
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    TrayNotification tray = new TrayNotification();
                                    tray.setTitle("New File");
                                    tray.setMessage("New file was Downloaded. Check the download folder");
                                    tray.setRectangleFill(Paint.valueOf("#2A9A84"));
                                    tray.setAnimationType(AnimationType.POPUP);
                                    //tray.setImage(whatsAppImg);
                                    tray.showAndDismiss(Duration.seconds(2));
                                    Notifications.getInstance().addData("New file was Downloaded. Check the download folder");
                                }
                            });
                        } else if(  transferObject.getState() == 3 ){
                            String message = transferObject.getString();
                            ChatMessages.getInstance().addToList("client",message);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if( ChatMessages.getInstance().isActive() ){
                                        Chat chat = Chat.getInstance();
                                        if( chat!=null ){
                                            chat.addToChat("client",message);
                                        }
                                    } else{
                                        TrayNotification tray = new TrayNotification();
                                        tray.setTitle("New Message");
                                        tray.setMessage(message);
                                        tray.setRectangleFill(Paint.valueOf("#2A9A84"));
                                        tray.setAnimationType(AnimationType.POPUP);
                                        //tray.setImage(whatsAppImg);
                                        tray.showAndDismiss(Duration.seconds(2));
                                        Notifications.getInstance().addData("You have a new message");
                                    }
                                }
                            });
                        }
                        //System.out.println(image);
                        // System.out.println("too");
                    } catch (IOException | ClassNotFoundException e){
                        connected = false;
                    }
                }
            }
        }.start();


    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }


}
