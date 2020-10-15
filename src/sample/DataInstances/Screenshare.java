package sample.DataInstances;

import javafx.scene.image.WritableImage;

public class Screenshare {
    private static Screenshare instance = null;
    private WritableImage image = null;

    private Screenshare(){}
    public static Screenshare getInstance(){
        if( instance == null ){
            instance = new Screenshare();
        }
        return instance;
    }

    public WritableImage getImage() {
        return image;
    }

    public void setImage(WritableImage image) {
        this.image = image;
    }
}
