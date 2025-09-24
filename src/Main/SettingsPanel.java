package Main;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private final Settings settings;
    private final JComboBox<String> unitComboBox;
    private final JComboBox<String> themeComboBox;

    public SettingsPanel(Settings settings) {
        this.settings = settings;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Settings"));

        // Temperature Unit selector
        JLabel unitLabel = new JLabel("Temperature Unit:");
        unitComboBox = new JComboBox<>(new String[]{"Celsius", "Fahrenheit"});
        unitComboBox.setSelectedIndex(settings.getTemperatureUnit() == Settings.Unit.CELSIUS ? 0 : 1);
        unitComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Theme selector
        JLabel themeLabel = new JLabel("Theme:");
        themeComboBox = new JComboBox<>(new String[]{"Light Mode", "Dark Mode"});
        themeComboBox.setSelectedIndex(settings.getTheme() == Settings.Theme.LIGHT ? 0 : 1);
        themeComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(0, 10)));
        unitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(unitLabel);
        add(unitComboBox);

        add(Box.createRigidArea(new Dimension(0, 20)));
        themeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(themeLabel);
        add(themeComboBox);

        add(Box.createVerticalGlue());
    }

    // Call this method to update settings from user choices
    public void applySettings() {
        int unitIndex = unitComboBox.getSelectedIndex();
        settings.setTemperatureUnit(unitIndex == 0 ? Settings.Unit.CELSIUS : Settings.Unit.FAHRENHEIT);

        int themeIndex = themeComboBox.getSelectedIndex();
        settings.setTheme(themeIndex == 0 ? Settings.Theme.LIGHT : Settings.Theme.DARK);
    }
}
