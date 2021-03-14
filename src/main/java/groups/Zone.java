package groups;

import data.DataInApp;
import preview.Preview;
import settings.Settings;
import settings.Strings;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Zone extends JPanel {
    private Strings strings;

    private DataInApp dataInApp;
    private Preview preview;
    private boolean onRepaint;

    private JSpinner topAmount;
    private JSpinner bottomAmount;
    private JSpinner sidesAmount;
    private JSpinner thickness;
    private JSpinner standWidth;
    private JCheckBox invertOrder;
    private Preview preView;
    private JComboBox startingPosition;

    public Zone() {
        strings = Strings.getInstance();

        dataInApp = DataInApp.getInstance();
        preView = new Preview();

        settings();
        addComponent();
    }

    private void addComponent() {

        JLabel topAmountLabel = new JLabel(strings.getTopAmount());
        add(topAmountLabel);

        topAmount = new JSpinner(new SpinnerNumberModel(Settings.TOP_AMOUNT.intValue(), 1, Integer.MAX_VALUE, 1));
        topAmount.addChangeListener(e -> {
            Settings.TOP_AMOUNT = (Integer) topAmount.getValue();
            Settings.change();
        });
        add(topAmount);

        JLabel bottomAmountLabel = new JLabel(strings.getBottomAmount());
        add(bottomAmountLabel);

        bottomAmount = new JSpinner(new SpinnerNumberModel(Settings.BOTTOM_AMOUNT.intValue(), 1, Integer.MAX_VALUE, 1));
        bottomAmount.addChangeListener(e -> {
            Settings.BOTTOM_AMOUNT = (Integer) bottomAmount.getValue();
            Settings.change();
        });
        add(bottomAmount);

        JLabel sidesAmountLabel = new JLabel(strings.getSidesAmount());
        add(sidesAmountLabel);

        sidesAmount = new JSpinner(new SpinnerNumberModel(Settings.SIDES_AMOUNT.intValue(), 1, Integer.MAX_VALUE, 1));
        sidesAmount.addChangeListener(e -> {
            Settings.SIDES_AMOUNT = (Integer) sidesAmount.getValue();
            Settings.change();
        });
        add(sidesAmount);

        JLabel thicknessLabel = new JLabel(strings.getThickness());
        add(thicknessLabel);

        thickness = new JSpinner(new SpinnerNumberModel(Settings.THICKNESS.doubleValue(), 0, 1, 0.01));
        thickness.setEditor(new JSpinner.NumberEditor(thickness, "#%"));
        thickness.addChangeListener(e -> {
            Settings.THICKNESS = (Double) thickness.getValue();
            Settings.change();
        });
        add(thickness);

        JLabel standWidthLabel = new JLabel(strings.getStandWidth());
        add(standWidthLabel);

        standWidth = new JSpinner(new SpinnerNumberModel(Settings.STAND_WIDTH.doubleValue(), 0, 1, 0.01));
        standWidth.setEditor(new JSpinner.NumberEditor(standWidth, "#%"));
        standWidth.addChangeListener(e -> {
            Settings.STAND_WIDTH = (Double) standWidth.getValue();
            Settings.change();
        });
        add(standWidth);

        invertOrder = new JCheckBox(strings.getInvertOrder());
        invertOrder.setSelected(Settings.INVERT_ORDER);
        invertOrder.addChangeListener(e -> {
            Settings.INVERT_ORDER = invertOrder.isSelected();
            Settings.change();
        });
        add(invertOrder);
        add(new JLabel());

        JLabel startingPositionLabel = new JLabel(strings.getStartingPosition());
        add(startingPositionLabel);

        String[] positions = {strings.getTopLeft(),
                strings.getTopRight(), strings.getBottomLeft(), strings.getBottomRight(),
                strings.getMiddle()};
        startingPosition = new JComboBox<>(positions);
        startingPosition.setSelectedItem(Settings.STARTING_POSITION);
        startingPosition.addActionListener(e -> {
            Settings.STARTING_POSITION = (String) startingPosition.getSelectedItem();
            Settings.change();
        });
        add(startingPosition);

        add(new JLabel());
        add(new JLabel());

        JButton calculateButton = new JButton(strings.getCalculate());
        calculateButton.addActionListener(e -> {
            preView.setVisible(false);
            preView.drawZones();
            preView.setVisible(true);
        });
        add(calculateButton);

        JButton livePreview = new JButton(strings.getLivePreview());
        livePreview.addActionListener(e -> {
            new Thread(() -> {
                onRepaint = true;
                while (onRepaint) {
                    preView.repaint();
                }
            });
        });
        add(livePreview);


        JButton livePreviewStop = new JButton(strings.getStop());
        livePreviewStop.addActionListener(e -> {
            onRepaint = false;
        });
        add(livePreviewStop);


    }

    private void settings() {
        setLayout(new GridLayout(0, 2));
        setBorder(BorderFactory.createTitledBorder(strings.getZone()));
    }
}
