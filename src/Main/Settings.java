package Main;

import java.awt.Color;

public class Settings {
    public enum Unit {
        CELSIUS, FAHRENHEIT
    }

    public enum Theme {
        LIGHT, DARK
    }

    private Unit temperatureUnit;
    private Theme theme;

    public Settings() {
        temperatureUnit = Unit.CELSIUS;
        theme = Theme.LIGHT;
    }

    // Getters and setters
    public Unit getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(Unit unit) {
        this.temperatureUnit = unit;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    // Utility to get colors based on theme
    public Color getBackgroundColor() {
        if (theme == Theme.LIGHT) {
            return new Color(173, 216, 230);  // pastel blue
        } else {
            return new Color(30, 30, 30);     // really dark gray instead of pure black
        }
    }

    public Color getAccentColor() {
        if (theme == Theme.LIGHT) {
            return new Color(230, 230, 250);  // pastel lavender
        } else {
            return new Color(80, 80, 80);     // medium gray instead of Color.GRAY
        }
    }

    public Color getForegroundColor() {
        if (theme == Theme.LIGHT) {
            return Color.BLACK;
        } else {
            return new Color(220, 220, 220);  // light gray text instead of pure white
        }
    }

}
