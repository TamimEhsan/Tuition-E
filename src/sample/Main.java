package sample;

import com.jfoenix.controls.JFXSnackbar;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Network.Server;

public class Main extends Application {


    private static Main instance;
    public static Stage stage;

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    public void init() {
        Server server = Server.getInstance();
        if (server == null)
            System.out.println("here");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("ViewFXML/sample.fxml"));
        primaryStage.setTitle("Tuition-E");
        Scene scene = new Scene(root, 576, 476);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest((event) -> {
            Platform.exit(); // Stage Exit
            System.exit(0); // All thread Close
        });
       /* scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                System.out.println(keyEvent.getCharacter());
            }
        });*/
        primaryStage.show();
        // android.widget.Toast.makeText(, "", Toast.LENGTH_SHORT).show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
