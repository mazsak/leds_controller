import data.DataInApp;
import groups.Device;
import groups.Screen;
import groups.SettingsApp;
import groups.Zone;
import settings.Strings;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private Strings strings;

    private DataInApp dataInApp;

    public Window() {
        strings = Strings.getInstance();

        dataInApp = DataInApp.getInstance();

        settings();
        addComponent();
        showToCenter();

        setVisible(true);
    }

    private void addComponent() {
        add(new SettingsApp());
        add(new Screen());
        add(new Device());
        add(new Zone());

        JButton start = new JButton(strings.getStart());
        start.addActionListener(e -> {
            dataInApp.startCalculateZones();
        });
        add(start);

        JButton stop = new JButton(strings.getStop());
        stop.addActionListener(e -> {
            dataInApp.stopCalculateZones();
        });
        add(stop);
    }

    private void showToCenter() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }

    private void settings() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setSize(800, 500);
        setBackground(Color.lightGray);
        setTitle(strings.getNameApp());
        setLayout(new FlowLayout());
        setResizable(false);

    }
}
