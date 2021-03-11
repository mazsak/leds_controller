package groups;

import settings.Strings;

import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel {
    private Strings strings;

    private JTextField scaleWidth;
    private JTextField scaleHeight;

    public Screen() {
        strings = Strings.getInstance();

        settings();
        addComponent();
    }

    private void addComponent() {

    }

    private void settings() {
        setLayout(new GridLayout(0, 2));
        setBorder(BorderFactory.createTitledBorder(strings.getScreen()));
    }

}
