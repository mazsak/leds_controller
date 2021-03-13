package preview;

import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;

@Data
public class ZoneView {

    private int number;
    private Dimension size;
    private Point location;
    private Color color;

    public ZoneView(int number, Dimension size, Point location) {
        this.number = number;
        this.size = size;
        this.location = location;
    }

    public synchronized void calculateColor(BufferedImage image) {
        BufferedImage zone = image.getSubimage(location.x, location.y,size.width, size.height);
        Color[][] colors = new Color[(zone.getWidth() / 10) + 1][(zone.getHeight() / 10) + 1];
        int red = 0;
        int green = 0;
        int blue = 0;
        int size = 0;

        int n = 200;
        Color[] cols = new Color[n];
        for (int i = 0; i < n; i++) {
            cols[i] = Color.getHSBColor((float) i / (float) n, 1.0f, 1.0f);
        }

        for (int i = 0; i < zone.getWidth(); i = i + 10) {
            for (int j = 0; j < zone.getHeight(); j = j + 10) {
                Color color = new Color(zone.getRGB(i, j));
                colors[i / 10][j / 10] = color;

                red += color.getRed();
                green += color.getGreen();
                blue += color.getBlue();
                size++;
            }
        }
        color = new Color(red / size, green / size, blue / size);
    }
}
