package Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherGUI {
    public static void main(String[] args) {
        // Settings object to hold preferences and theme
        Settings settings = new Settings();

        JFrame frame = new JFrame("Weather App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 450);
        frame.setLocationRelativeTo(null); // Center window

        // Main container with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Left panel for input, forecast, last searched, settings button
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(500, 450));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Apply initial theme colors
        applyThemeColors(leftPanel, settings);

        // City Label
        JLabel cityLabel = new JLabel("Enter City:");
        cityLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        cityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cityLabel.setForeground(settings.getForegroundColor());

        // City input field
        JTextField cityTextField = new JTextField(20);
        cityTextField.setMaximumSize(new Dimension(400, 30));
        cityTextField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cityTextField.setHorizontalAlignment(JTextField.CENTER);
        cityTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        cityTextField.setForeground(settings.getForegroundColor());
        cityTextField.setBackground(settings.getAccentColor());

        // Get Forecast Button
        JButton fetchButton = new JButton("Get Forecast");
        fetchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        fetchButton.setFocusPainted(false);
        fetchButton.setBackground(settings.getAccentColor());
        fetchButton.setForeground(settings.getForegroundColor());
        fetchButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Forecast display area
        JTextArea forecastArea = new JTextArea(6, 30);
        forecastArea.setWrapStyleWord(true);
        forecastArea.setLineWrap(true);
        forecastArea.setEditable(false);
        forecastArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        forecastArea.setBackground(settings.getAccentColor());
        forecastArea.setForeground(settings.getForegroundColor());
        forecastArea.setBorder(BorderFactory.createLineBorder(settings.getAccentColor()));
        forecastArea.setText("Your forecast will be displayed here.");

        JScrollPane scrollPane = new JScrollPane(forecastArea);
        scrollPane.setPreferredSize(new Dimension(250, 100));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Bottom panel in left panel for Last Searched and Settings buttons
        JPanel bottomLeftButtons = new JPanel();
        bottomLeftButtons.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        bottomLeftButtons.setOpaque(false);

        JButton lastSearchedButton = new JButton("Last Searched");
        lastSearchedButton.setFocusPainted(false);
        lastSearchedButton.setBackground(settings.getAccentColor());
        lastSearchedButton.setForeground(settings.getForegroundColor());
        lastSearchedButton.setFont(new Font("SansSerif", Font.BOLD, 12));

        JButton settingsButton = new JButton("Settings");
        settingsButton.setFocusPainted(false);
        settingsButton.setBackground(settings.getAccentColor());
        settingsButton.setForeground(settings.getForegroundColor());
        settingsButton.setFont(new Font("SansSerif", Font.BOLD, 12));

        bottomLeftButtons.add(lastSearchedButton);
        bottomLeftButtons.add(settingsButton);

        // Add components to left panel
        leftPanel.add(cityLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(cityTextField);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(fetchButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        leftPanel.add(scrollPane);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(bottomLeftButtons);

        // Right panel for favorite cities list and buttons
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(230, 450));
        rightPanel.setBorder(new EmptyBorder(20, 10, 20, 20));
        rightPanel.setLayout(new BorderLayout());

        // Apply theme colors to right panel
        applyThemeColors(rightPanel, settings);

        JLabel favLabel = new JLabel("Favorite Cities");
        favLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        favLabel.setHorizontalAlignment(SwingConstants.CENTER);
        favLabel.setForeground(settings.getForegroundColor());

        DefaultListModel<String> favoritesModel = new DefaultListModel<>();
        JList<String> favoritesList = new JList<>(favoritesModel);
        favoritesList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        favoritesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        favoritesList.setBackground(settings.getBackgroundColor());
        favoritesList.setForeground(settings.getForegroundColor());

        JScrollPane favScrollPane = new JScrollPane(favoritesList);
        favScrollPane.setOpaque(false);
        favScrollPane.getViewport().setOpaque(false);
        favScrollPane.setBorder(BorderFactory.createLineBorder(settings.getAccentColor()));

        // Panel for Add and Remove favorite buttons
        JPanel favButtonsPanel = new JPanel();
        favButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        favButtonsPanel.setOpaque(false);

        JButton addFavButton = new JButton("Add");
        addFavButton.setFocusPainted(false);
        addFavButton.setBackground(settings.getAccentColor());
        addFavButton.setForeground(settings.getForegroundColor());
        addFavButton.setFont(new Font("SansSerif", Font.BOLD, 12));

        JButton removeFavButton = new JButton("Remove");
        removeFavButton.setFocusPainted(false);
        removeFavButton.setBackground(settings.getAccentColor());
        removeFavButton.setForeground(settings.getForegroundColor());
        removeFavButton.setFont(new Font("SansSerif", Font.BOLD, 12));

        favButtonsPanel.add(addFavButton);
        favButtonsPanel.add(removeFavButton);
        
        Dimension size = removeFavButton.getPreferredSize();
        addFavButton.setPreferredSize(size);


        rightPanel.add(favLabel, BorderLayout.NORTH);
        rightPanel.add(favScrollPane, BorderLayout.CENTER);
        rightPanel.add(favButtonsPanel, BorderLayout.SOUTH);

        // Add panels to main container
        mainPanel.add(leftPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);

        // Store last searched city
        final String[] lastSearchedCity = {null};

        // Fetch weather button action
        fetchButton.addActionListener(e -> {
            String city = cityTextField.getText().trim();
            if (!city.isEmpty()) {
            	String weatherReport = WeatherAPI.getWeatherForCity(city, settings);
            	forecastArea.setText(weatherReport);


                // Update last searched city
                lastSearchedCity[0] = city;
            } else {
                forecastArea.setText("Please enter a city name.");
            }
        });

        // Clicking on favorite city shows its weather, but doesn't update last searched
        favoritesList.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                String selectedCity = favoritesList.getSelectedValue();
                if (selectedCity != null) {
                    String weatherReport = WeatherAPI.getWeatherForCity(selectedCity, settings);
                    forecastArea.setText(weatherReport);
                    cityTextField.setText(selectedCity);
                }
            }
        });

        // Last searched button shows the last searched city weather
        lastSearchedButton.addActionListener(e -> {
            if (lastSearchedCity[0] != null) {
                cityTextField.setText(lastSearchedCity[0]);
                String weatherReport = WeatherAPI.getWeatherForCity(lastSearchedCity[0], settings);
                forecastArea.setText(weatherReport);  // plain text output
            } else {
                forecastArea.setText("No last searched city.");
            }
        });


        // Settings button opens settings dialog
        settingsButton.addActionListener(e -> {
            SettingsPanel settingsPanel = new SettingsPanel(settings);

            int result = JOptionPane.showConfirmDialog(frame, settingsPanel,
                    "Settings", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                settingsPanel.applySettings();
                applyThemeColors(leftPanel, settings);
                applyThemeColors(rightPanel, settings);

                // Update individual components colors to match theme:
                cityLabel.setForeground(settings.getForegroundColor());
                cityTextField.setForeground(settings.getForegroundColor());
                cityTextField.setBackground(settings.getAccentColor());
                fetchButton.setBackground(settings.getAccentColor());
                fetchButton.setForeground(settings.getForegroundColor());
                forecastArea.setForeground(settings.getForegroundColor());
                forecastArea.setBackground(settings.getAccentColor());
                scrollPane.getViewport().setBackground(settings.getBackgroundColor());

                favLabel.setForeground(settings.getForegroundColor());
                favoritesList.setBackground(settings.getBackgroundColor());
                favoritesList.setForeground(settings.getForegroundColor());
                favScrollPane.setBorder(BorderFactory.createLineBorder(settings.getAccentColor()));

                addFavButton.setBackground(settings.getAccentColor());
                addFavButton.setForeground(settings.getForegroundColor());
                removeFavButton.setBackground(settings.getAccentColor());
                removeFavButton.setForeground(settings.getForegroundColor());

                settingsButton.setBackground(settings.getAccentColor());
                settingsButton.setForeground(settings.getForegroundColor());

                lastSearchedButton.setBackground(settings.getAccentColor());
                lastSearchedButton.setForeground(settings.getForegroundColor());

                bottomLeftButtons.setBackground(settings.getBackgroundColor());
                
                if (lastSearchedCity[0] != null) {
                    String weatherReport = WeatherAPI.getWeatherForCity(lastSearchedCity[0], settings);
                    forecastArea.setText(weatherReport);
            }
          }
        });

        // Add to Favorites button action
        addFavButton.addActionListener(e -> {
            String city = cityTextField.getText().trim();
            if (!city.isEmpty()) {
            	String capitalizedCity = capitalizeEachWord(city);
                if (!favoritesModel.contains(capitalizedCity)) {
                    favoritesModel.addElement(capitalizedCity);
                }
            }
        });

        // Remove Selected Favorite button action
        removeFavButton.addActionListener(e -> {
            String selected = favoritesList.getSelectedValue();
            if (selected != null) {
                favoritesModel.removeElement(selected);
            }
        });
    }
    
    private static String capitalizeEachWord(String input) {
        if (input == null || input.isEmpty()) return input;

        String[] words = input.toLowerCase().split("\\s+");
        StringBuilder capitalized = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                           .append(word.substring(1))
                           .append(" ");
            }
        }

        return capitalized.toString().trim();
    }


    // Helper method to apply theme colors to a panel
    private static void applyThemeColors(JPanel panel, Settings settings) {
        panel.setBackground(settings.getBackgroundColor());
        panel.setForeground(settings.getForegroundColor());
    }
}
