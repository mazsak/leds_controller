package data;

import lombok.Data;
import preview.ZoneView;
import settings.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Data
public class DataInApp {

    private static DataInApp instance;

    private List<GraphicsDevice> devices;
    private GraphicsDevice defaultDevice;
    private BufferedImage image;
    private List<ZoneView> zones;
    private Thread zonesThread;
    private boolean onCalculate;

    private DataInApp() {
        onCalculate = false;
        zones = new ArrayList<>();
        devices = Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices());

        if (Settings.DEFAULT_DEVICE.equals("")) {
            defaultDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        } else {
            defaultDevice = devices.stream().filter(device -> device.getIDstring().replace("\\", "").equals(Settings.DEFAULT_DEVICE)).findFirst().get();
        }

        zonesThread = new Thread(() -> {
            while (onCalculate) {
                for (int i = 0; i < zones.size(); i++){
                zones.get(i).calculateColor(image);
                    try {
                        ArduinoConnection.getInstance().sendColor(zones.get(i).getColor(), i);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void startCalculateZones(){
        onCalculate = true;
        if (!zonesThread.isAlive()) {
            zonesThread.start();
        }
        System.out.println("Start calculate zones");
    }


    public void stopCalculateZones(){
        onCalculate = false;
        System.out.println("Stop calculate zones");
    }

    public static DataInApp getInstance() {
        if (instance == null)
            instance = new DataInApp();
        return instance;
    }

    public BufferedImage getNewImage() {
        try {
            image = new Robot().createScreenCapture(new Rectangle(defaultDevice.getDefaultConfiguration().getBounds()));
        } catch (AWTException e) {
            e.printStackTrace();
        }

        return image;
    }

}
