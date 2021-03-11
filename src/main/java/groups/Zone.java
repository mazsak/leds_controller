package groups;

import settings.Strings;

import javax.swing.*;
import java.awt.*;

public class Zone extends JPanel {
    private Strings strings;

    public Zone() {
        strings = Strings.getInstance();

        settings();
        addComponent();
    }

    private void addComponent() {

    }

    private void settings() {
        setLayout(new GridLayout(0, 2));

        setBorder(BorderFactory.createTitledBorder(strings.getZone()));
    }
}
