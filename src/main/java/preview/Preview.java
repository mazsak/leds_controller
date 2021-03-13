package preview;

import data.DataInApp;
import settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Preview extends JFrame {

    private DataInApp dataInApp;

    private BufferedImage image;

    private JLabel photo;


    public Preview() {
        dataInApp = DataInApp.getInstance();

        photo = new JLabel();
        add(photo);

        settings();
    }

    public void drawZones() {
        image = dataInApp.getNewImage();

        dataInApp.getZones().clear();


        Dimension screenSize = new Dimension(dataInApp.getDefaultDevice().getDisplayMode().getWidth(), dataInApp.getDefaultDevice().getDisplayMode().getHeight());
        int widthZone = (int) (Settings.THICKNESS * screenSize.getWidth());
        int heightZone = (int) (Settings.THICKNESS * screenSize.getHeight());

        int topSizeZone = (int) (screenSize.getWidth() / Settings.TOP_AMOUNT);
        int bottomSizeZone = (int) ((screenSize.getWidth() * (1 - Settings.STAND_WIDTH)) / Settings.BOTTOM_AMOUNT);
        int sidesSizeZone = (int) (screenSize.getHeight() / Settings.SIDES_AMOUNT);

        int number = 1;

        remove(photo);
        photo = new JLabel(new ImageIcon(image));
        photo.setPreferredSize(screenSize);
        add(photo);
        revalidate();
        repaint();

        for (int i = 0; i < Settings.SIDES_AMOUNT; i++) {
            ZoneView sideL = new ZoneView(number++, new Dimension(widthZone, sidesSizeZone), new Point(0, (int) (screenSize.getHeight() - ((i + 1) * sidesSizeZone))));
            dataInApp.getZones().add(sideL);
        }
        for (int i = 0; i < Settings.TOP_AMOUNT; i++) {
            ZoneView top = new ZoneView(number++, new Dimension(topSizeZone, heightZone), new Point(i * topSizeZone, 0));
            dataInApp.getZones().add(top);
        }
        for (int i = 0; i < Settings.SIDES_AMOUNT; i++) {
            ZoneView sideR = new ZoneView(number++, new Dimension(widthZone, sidesSizeZone), new Point((int) (screenSize.getWidth() - widthZone), i * sidesSizeZone));
            dataInApp.getZones().add(sideR);
        }

        for (int i = 0; i < Settings.BOTTOM_AMOUNT; i++) {
            ZoneView bottom = new ZoneView(number++, new Dimension(bottomSizeZone, heightZone), new Point((int) (screenSize.getWidth() - ((i + 1) * bottomSizeZone)), (int) (screenSize.getHeight() - heightZone)));
            dataInApp.getZones().add(bottom);
        }

    }

    private void settings() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLayout(null);
        getContentPane().setLayout(null);

    }

    @Override
    public synchronized void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        dataInApp.getZones().forEach(zone -> {
//            if (!dataInApp.isOnCalculate()) {
//                zone.calculateColor(image);
//            }
            g.setColor(zone.getColor());
            g.fillRect(zone.getLocation().x, zone.getLocation().y, zone.getSize().width, zone.getSize().height);
        });
    }
}
