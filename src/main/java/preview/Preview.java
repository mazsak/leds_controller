package preview;

import data.DataInApp;
import settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Preview extends JFrame {

    private DataInApp dataInApp;

    private BufferedImage image;

    public Preview() {
        dataInApp = DataInApp.getInstance();

        settings();
    }

    public void drawZones() {
        image = dataInApp.getNewImage();

        dataInApp.calculateZones();
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
            if (!dataInApp.isOnCalculate()) {
                zone.calculateColor(image);
            }
            g.setColor(zone.getColor());
            g.fillRect(zone.getLocation().x, zone.getLocation().y, zone.getSize().width, zone.getSize().height);
        });
    }
}
