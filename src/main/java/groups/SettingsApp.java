package groups;

import settings.Settings;
import settings.Strings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;

public class SettingsApp extends JPanel {
    private Strings strings;

    private JComboBox language;

    public SettingsApp() {
        strings = Strings.getInstance();

        settings();
        addComponent();
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private void addComponent() {
        JLabel languageLabel = new JLabel(strings.getLanguage());
        add(languageLabel);

        File folder = Paths.get("settings", "language").toFile();
        Object[] languages = Arrays.asList(folder.listFiles()).stream().map(file -> {
            String name = file.getName().replace(".json", "");
            return capitalize(name);
        }).toArray();
        language = new JComboBox(languages);
        language.getModel().setSelectedItem(capitalize(Settings.LANGUAGE));
        language.setPreferredSize(new Dimension(150, language.getHeight()));
        language.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Settings.LANGUAGE = e.getItem().toString().toLowerCase();
                Settings.change();
                getParent().repaint();
            }
        });
        add(language);
    }

    private void settings() {
        setLayout(new GridLayout(0, 2));
        setBorder(BorderFactory.createTitledBorder(strings.getSettingsApp()));
    }
}
