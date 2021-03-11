package data;

import settings.Settings;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class DataInApp {

    private static DataInApp instance;

    private List<GraphicsDevice> devices;
    private GraphicsDevice defaultDevice;

    public static DataInApp getInstance() {
        if (instance ==null)
            instance = new DataInApp();
        return instance;
    }

    private DataInApp() {
        devices = Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices());

        if (Settings.DEFAULT_DEVICE.equals("")){
            defaultDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        }else{
            defaultDevice = devices.stream().filter(device -> device.getIDstring().replace("\\", "").equals(Settings.DEFAULT_DEVICE)).findFirst().get();
        }
    }

    public List<GraphicsDevice> getDevices() {
        return devices;
    }

    public void setDevices(List<GraphicsDevice> devices) {
        this.devices = devices;
    }

    public GraphicsDevice getDefaultDevice() {
        return defaultDevice;
    }

    public void setDefaultDevice(GraphicsDevice defaultDevice) {
        this.defaultDevice = defaultDevice;
    }
}
