package groups;

import settings.Settings;
import settings.Strings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.List;

public class Device extends JPanel {

    private Strings strings;


    private List<GraphicsDevice> devices;
    private GraphicsDevice defaultDevice;

    private JComboBox devicesBox;
    private JLabel resolution;
    private JLabel bitDepth;
    private JLabel refreshRate;

    public Device() {
        strings = Strings.getInstance();

        devices = Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices());
        if (Settings.DEFAULT_DEVICE.equals("")){
        defaultDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        }else{
            defaultDevice = devices.stream().filter(device -> device.getIDstring().replace("\\", "").equals(Settings.DEFAULT_DEVICE)).findFirst().get();
        }

        settings();
        addComponent();
    }

    private void addComponent() {
        JLabel devicesLabel = new JLabel(strings.getDevice());
        add(devicesLabel);

        devicesBox = new JComboBox(devices.stream().map(device -> device.getIDstring().replace("\\", "")).toArray());
        devicesBox.setPreferredSize(new Dimension(150,devicesBox.getHeight()));
        devicesBox.getModel().setSelectedItem(defaultDevice.getIDstring().replace("\\", ""));
        devicesBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String nameDevice = (String) e.getItem();
                defaultDevice = devices.stream().filter(device -> device.getIDstring().replace("\\", "").equals(nameDevice)).findFirst().get();
                fillFieldDataMonitor();

                Settings.DEFAULT_DEVICE = defaultDevice.getIDstring().replace("\\", "");
                Settings.change();
            }
        });
        add(devicesBox);

        JLabel resolutionLabel = new JLabel(strings.getResolution());
        add(resolutionLabel);

        resolution = new JLabel();
        add(resolution);

        JLabel refreshRateLabel = new JLabel(strings.getRefreshRate());
        add(refreshRateLabel);

        refreshRate = new JLabel();
        add(refreshRate);

        JLabel bitDepthLabel = new JLabel(strings.getBitDepth());
        add(bitDepthLabel);

        bitDepth = new JLabel();
        add(bitDepth);

        fillFieldDataMonitor();
    }

    private void fillFieldDataMonitor() {
        resolution.setText(defaultDevice.getDisplayMode().getWidth() + "x" + defaultDevice.getDisplayMode().getHeight());
        bitDepth.setText(String.valueOf(defaultDevice.getDisplayMode().getBitDepth()));
        refreshRate.setText(String.valueOf(defaultDevice.getDisplayMode().getRefreshRate()));
    }

    private void settings() {
        setLayout(new GridLayout(0, 2));


        setBorder(BorderFactory.createTitledBorder(strings.getDevice()));
    }
}
