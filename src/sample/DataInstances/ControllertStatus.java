package sample.DataInstances;

import sample.Controllers.Controller;

public class ControllertStatus {

    private boolean running;
    private static ControllertStatus instance;
    private ControllertStatus(){}
    public static ControllertStatus getInstance(){
        if( instance == null ){
            instance = new ControllertStatus();
        }
        return instance;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
