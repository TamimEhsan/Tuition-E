package sample.DataInstances;

import javax.sound.sampled.*;

public class AudioRender {
    public static AudioRender instance;
    private AudioInputStream audioInputStream;
    private AudioFormat format;
    private boolean onGoing = false;
    private float sampleRate = 44100.0f;
    private DataLine.Info info;
    private SourceDataLine sourceDataLine;

    private AudioRender(){
        format = new AudioFormat(sampleRate, 16, 2, true, false);
        info = new DataLine.Info(SourceDataLine.class, format);
        try {
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static AudioRender getInstance(){
        if( instance == null ){
            instance = new AudioRender();
        }
        return instance;
    }

    public boolean isOnGoing() {
        return onGoing;
    }

    public void setOnGoing(boolean onGoing) {
        this.onGoing = onGoing;
    }

    public void start(){
        try {
            sourceDataLine.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        sourceDataLine.start();
    }
    public void end(){
        sourceDataLine.drain();
        sourceDataLine.close();
    }

    public void toSpeaker(byte soundbytes[]) {
        try
        {
            System.out.println("At the speaker");
            sourceDataLine.write(soundbytes, 0, soundbytes.length);
        } catch (Exception e) {
            System.out.println("Not working in speakers...");
            e.printStackTrace();
        }
    }
}
