package groups;

import data.DataInApp;
import settings.Strings;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Arrays;

public class Screen extends JPanel {
    private Strings strings;

    private DataInApp dataInApp;

    private JComboBox scale;

    public Screen() {
        strings = Strings.getInstance();
        dataInApp = DataInApp.getInstance();
        settings();
        addComponent();

    }

    private void addComponent() {
        JLabel scaleLabel = new JLabel(strings.getScale());
        add(scaleLabel);

        scale = new JComboBox(Arrays.asList(dataInApp.getDefaultDevice().getDisplayModes()).stream().map(displayMode -> displayMode.getWidth()+"x"+displayMode.getHeight()).toArray());
        scale.setPreferredSize(new Dimension(150, scale.getHeight()));
        add(scale);

    }

    private void settings() {
        setLayout(new GridLayout(0, 2));
        setBorder(BorderFactory.createTitledBorder(strings.getScreen()));
    }

}
