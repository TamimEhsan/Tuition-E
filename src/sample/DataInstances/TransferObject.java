package sample.DataInstances;

import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;

public class TransferObject implements Serializable {
    // General information
    private static final long serialVersionUID = 1L;
    private int state = 0;
    private String status;
    // Image data ; state = 1
    private int imageheight;
    private int imagewidth;
    private byte[] imagedata;
    //File data ; state =2
    private String filename;
    private long filesize;
    private byte[] fileData;
    final private String defaultDestinationFolder = "G:/TermProject/FileDownload/";
    // Message data; type = 3
    private String string;

    // Clicker; type = 4
    private int clickCount;
    private double clickX;
    private double clickY;
    private String clickType;
    // Clicker; type = 5
    private String keyType;
    //Audio Call; type = 6
    private byte[] audioData;

    public TransferObject() {
    }

    public int getState() {
        return state;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WritableImage getImage() {
        WritableImage image = new WritableImage(imagewidth, imageheight);
        image.getPixelWriter().setPixels(0, 0, imagewidth, imageheight,
                PixelFormat.getByteBgraInstance(),
                imagedata, 0, imagewidth * 4);
        return image;
    }

    public void setImage(WritableImage image) {
        if (imageheight != image.getHeight() && imagewidth != image.getWidth()) {
            imagedata = new byte[(int) image.getWidth() * (int) image.getHeight() * 4];
        }
        imagewidth = (int) image.getWidth();
        imageheight = (int) image.getHeight();

        image.getPixelReader().getPixels(0, 0, imagewidth, imageheight,
                PixelFormat.getByteBgraInstance(),
                imagedata, 0, imagewidth * 4);
        status = "success";
        state = 1;
    }


    public void setFile(File file) {
        filename = file.getName();
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            long len = (int) file.length();
            byte[] filebyte = new byte[(int) len];
            int read = 0;
            int numRead = 0;
            while (read < filebyte.length && (numRead = dis.read(filebyte, read, filebyte.length - read)) >= 0) {
                read = read + numRead;
            }
            filesize = len;
            fileData = filebyte;
            state = 2;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFilename() {
        return filename;
    }

    public long getFilesize() {
        return filesize;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
        state = 3;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public double getClickX() {
        return clickX;
    }

    public void setClickX(double clickX) {
        this.clickX = clickX;
    }

    public double getClickY() {
        return clickY;
    }

    public void setClickY(double clickY) {
        this.clickY = clickY;
    }

    public String getClickType() {
        return clickType;
    }

    public void setClickType(MouseButton clickType) {
        state = 4;
        if (clickType.compareTo(MouseButton.PRIMARY) == 0) {
            this.clickType = "PRIMARY";
        } else if (clickType.compareTo(MouseButton.SECONDARY) == 0) {
            this.clickType = "SECONDARY";
        }
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
        state = 5;
    }

    public void setAudioData(byte[] audioData) {
        this.audioData = audioData;
        state = 6;
    }

    public byte[] getAudioData() {
        return audioData;
    }
}
