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
            try {
                defaultDevice = devices.stream().filter(device -> device.getIDstring().replace("\\", "").equals(Settings.DEFAULT_DEVICE)).findFirst().get();
            } catch (Exception e) {
                defaultDevice = devices.get(0);
            }
        }

        zonesThread = new Thread(() -> {
            while (onCalculate) {
                BufferedImage i = getNewImage();
                for (ZoneView zone : zones) {
                    zone.calculateColor(i);
                    try {
                        ArduinoConnection.getInstance().sendColor(zone.getStringColor());
                        Thread.sleep(10);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void startCalculateZones() {
        onCalculate = true;
        if (!zonesThread.isAlive()) {
            zonesThread.start();
        }
        System.out.println("Start calculate zones");
    }


    public void stopCalculateZones() {
        onCalculate = false;
        System.out.println("Stop calculate zones");
    }

    public static DataInApp getInstance() {
        if (instance == null) {
            instance = new DataInApp();
        }
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
