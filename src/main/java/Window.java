import groups.Device;
import groups.Screen;
import groups.SettingsApp;
import groups.Zone;
import settings.Strings;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private Strings strings;

    public Window() {
        strings = Strings.getInstance();

        settings();
        addComponent();
        showToCenter();

        setVisible(true);
    }

    private void addComponent() {
        add(new SettingsApp());
        add(new Device());
        add(new Screen());
        add(new Zone());
    }

    private void showToCenter() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }

    private void settings() {
        setSize(800, 600);
        setBackground(Color.lightGray);
        setTitle(strings.getNameApp());
        setLayout(new FlowLayout());
        setResizable(false);
    }
}
