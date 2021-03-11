package groups;

import data.DataInApp;
import settings.Settings;
import settings.Strings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Arrays;

public class Device extends JPanel {

    private Strings strings;

    private DataInApp dataInApp;

    private JComboBox devicesBox;
    private JLabel resolution;
    private JLabel bitDepth;
    private JLabel refreshRate;

    public Device() {
        dataInApp = DataInApp.getInstance();
        strings = Strings.getInstance();

        settings();
        addComponent();
    }

    private void addComponent() {
        JLabel devicesLabel = new JLabel(strings.getDevice());
        add(devicesLabel);

        devicesBox = new JComboBox(dataInApp.getDevices().stream().map(device -> device.getIDstring().replace("\\", "")).toArray());
        devicesBox.setPreferredSize(new Dimension(150,devicesBox.getHeight()));
        devicesBox.getModel().setSelectedItem(dataInApp.getDefaultDevice().getIDstring().replace("\\", ""));
        devicesBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String nameDevice = (String) e.getItem();
                dataInApp.setDefaultDevice(dataInApp.getDevices().stream().filter(device -> device.getIDstring().replace("\\", "").equals(nameDevice)).findFirst().get());
                fillFieldDataMonitor();

                Settings.DEFAULT_DEVICE = nameDevice;
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
        resolution.setText(dataInApp.getDefaultDevice().getDisplayMode().getWidth() + "x" + dataInApp.getDefaultDevice().getDisplayMode().getHeight());
        bitDepth.setText(String.valueOf(dataInApp.getDefaultDevice().getDisplayMode().getBitDepth()));
        refreshRate.setText(String.valueOf(dataInApp.getDefaultDevice().getDisplayMode().getRefreshRate()));
    }

    private void settings() {
        setLayout(new GridLayout(0, 2));


        setBorder(BorderFactory.createTitledBorder(strings.getDevice()));
    }
}
