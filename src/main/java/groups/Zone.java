package groups;

import data.DataInApp;
import settings.Strings;

import javax.swing.*;
import java.awt.*;

public class Zone extends JPanel {
    private Strings strings;

    private DataInApp dataInApp;

    public Zone() {
        strings = Strings.getInstance();

        dataInApp = DataInApp.getInstance();

        settings();
        addComponent();
    }

    private void addComponent() {
        JLabel amount = new JLabel();

    }

    private void settings() {
        setLayout(new GridLayout(0, 2));
        setBorder(BorderFactory.createTitledBorder(strings.getZone()));
    }
}
