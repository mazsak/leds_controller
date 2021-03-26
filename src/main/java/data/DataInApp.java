package data;

import enums.Sides;
import lombok.Data;
import preview.ZoneView;
import settings.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.*;

@Data
public class DataInApp {

    private static DataInApp instance;

    private List<GraphicsDevice> devices;
    private GraphicsDevice defaultDevice;
    private BufferedImage image;
    private List<ZoneView> zones;
    private Thread zonesThread;
    private boolean onCalculate;
    private Map<String, List<Sides>> order;

    private DataInApp() {
        onCalculate = false;
        zones = new ArrayList<>();
        devices = Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices());

        order = new HashMap<>();
        order.put("Middle", Arrays.asList(Sides.BOTTOM_LEFT, Sides.LEFT, Sides.TOP, Sides.RIGHT, Sides.BOTTOM_RIGHT));
        order.put("Bottom left", Arrays.asList(Sides.LEFT, Sides.TOP, Sides.RIGHT, Sides.BOTTOM_RIGHT, Sides.BOTTOM_LEFT));
        order.put("Top left", Arrays.asList(Sides.TOP, Sides.RIGHT, Sides.BOTTOM_RIGHT, Sides.BOTTOM_LEFT, Sides.LEFT));
        order.put("Top right", Arrays.asList(Sides.RIGHT, Sides.BOTTOM_RIGHT, Sides.BOTTOM_LEFT, Sides.LEFT, Sides.TOP));
        order.put("Bottom right", Arrays.asList(Sides.BOTTOM_RIGHT, Sides.BOTTOM_LEFT, Sides.LEFT, Sides.TOP, Sides.RIGHT));

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
                BufferedImage screen = getNewImage();
                int i = 1;
                for (ZoneView zone : zones) {
                    zone.calculateColor(screen);
                        try {
                            ArduinoConnection.getInstance().sendColor(zone.getStringColor());
                            System.out.println(zone.getStringColor());
                            Thread.sleep(10);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                }
            }
        });
    }

    public static DataInApp getInstance() {
        if (instance == null) {
            instance = new DataInApp();
        }
        return instance;
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

    public BufferedImage getNewImage() {
        try {
            image = new Robot().createScreenCapture(new Rectangle(defaultDevice.getDefaultConfiguration().getBounds()));
        } catch (AWTException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void calculateZones() {
        zones.clear();

        Dimension screenSize = new Dimension(defaultDevice.getDisplayMode().getWidth(), defaultDevice.getDisplayMode().getHeight());
        int widthZone = (int) (Settings.THICKNESS * screenSize.getWidth());
        int heightZone = (int) (Settings.THICKNESS * screenSize.getHeight());

        int topSizeZone = (int) (screenSize.getWidth() / Settings.TOP_AMOUNT);
        int bottomSizeZone = (int) ((screenSize.getWidth() * (1 - Settings.STAND_WIDTH)) / Settings.BOTTOM_AMOUNT);
        int sidesSizeZone = (int) (screenSize.getHeight() / Settings.SIDES_AMOUNT);

        int bottomLeftAmountLed = !Settings.INVERT_ORDER ? (int) Math.ceil(((float) Settings.BOTTOM_AMOUNT) / 2.0) : (int) Math.round(((float) Settings.BOTTOM_AMOUNT) / 2.0);
        int bottomRightAmountLed = Settings.BOTTOM_AMOUNT - bottomLeftAmountLed;

        int number = 0;

        for (Sides side : order.get(Settings.STARTING_POSITION)) {
            switch (side) {
                case LEFT:
                    number = calculateZonesLeft(screenSize, widthZone, sidesSizeZone, number);
                    break;
                case TOP:
                    number = calculateZonesTop(heightZone, topSizeZone, number);
                    break;
                case RIGHT:
                    number = calculateZonesRight(screenSize, widthZone, sidesSizeZone, number);
                    break;
                case BOTTOM_LEFT:
                    number = calculateZonesBottom(screenSize, heightZone, bottomSizeZone, bottomLeftAmountLed, number);
                    break;
                case BOTTOM_RIGHT:
                    number = calculateZonesBottom(screenSize, heightZone, bottomSizeZone, bottomRightAmountLed, number);
                    break;
            }
        }

        if (Settings.INVERT_ORDER) {
            Collections.reverse(zones);
            for (int i = 0; i < zones.size(); i++) {
                zones.get(i).setNumber(i);
            }
        }
    }

    private int calculateZonesBottom(Dimension screenSize, int heightZone, int bottomSizeZone, int bottomLeftAmountLed, int number) {
        for (int i = 0; i < bottomLeftAmountLed; i++) {
            zones.add(new ZoneView(number++, new Dimension(bottomSizeZone, heightZone), new Point((int) (screenSize.getWidth() - ((i + 1) * bottomSizeZone)), (int) (screenSize.getHeight() - heightZone))));
        }
        return number;
    }

    private int calculateZonesRight(Dimension screenSize, int widthZone, int sidesSizeZone, int number) {
        for (int i = 0; i < Settings.SIDES_AMOUNT; i++) {
            zones.add(new ZoneView(number++, new Dimension(widthZone, sidesSizeZone), new Point((int) (screenSize.getWidth() - widthZone), i * sidesSizeZone)));
        }
        return number;
    }

    private int calculateZonesTop(int heightZone, int topSizeZone, int number) {
        for (int i = 0; i < Settings.TOP_AMOUNT; i++) {
            zones.add(new ZoneView(number++, new Dimension(topSizeZone, heightZone), new Point(i * topSizeZone, 0)));
        }
        return number;
    }

    private int calculateZonesLeft(Dimension screenSize, int widthZone, int sidesSizeZone, int number) {
        for (int i = 0; i < Settings.SIDES_AMOUNT; i++) {
            zones.add(new ZoneView(number++, new Dimension(widthZone, sidesSizeZone), new Point(0, (int) (screenSize.getHeight() - ((i + 1) * sidesSizeZone)))));
        }
        return number;
    }


}
