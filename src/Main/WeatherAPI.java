package Main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherAPI {

    // Main method to get weather info for a given city
    public static String getWeatherForCity(String city, Settings settings) {
        try {
            // Get location info (latitude, longitude) for the city
            JSONObject cityLocationData = getLocationData(city);
            if (cityLocationData == null) return "City not found or API error.";

            double latitude = (double) cityLocationData.get("latitude");
            double longitude = (double) cityLocationData.get("longitude");

            // Fetch weather data for those coordinates and return formatted string
            return fetchWeatherData(latitude, longitude, settings.getTemperatureUnit());
        } catch (Exception e) {
            // Return error message if something goes wrong
            return "Error fetching weather: " + e.getMessage();
        }
    }

    // Get location data (lat, long) from Open-Meteo geocoding API
    private static JSONObject getLocationData(String city) {
        city = city.replaceAll(" ", "+"); // Replace spaces for URL

        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" +
                city + "&count=1&language=en&format=json";

        try {
            // Make HTTP GET request to API
            HttpURLConnection apiConnection = fetchApiResponse(urlString);

            // If response not OK, return null
            if (apiConnection.getResponseCode() != 200) return null;

            // Read response and parse JSON
            String jsonResponse = readApiResponse(apiConnection);
            JSONParser parser = new JSONParser();
            JSONObject resultsJsonObj = (JSONObject) parser.parse(jsonResponse);

            JSONArray locationData = (JSONArray) resultsJsonObj.get("results");

            // Return first result or null if none found
            return locationData.isEmpty() ? null : (JSONObject) locationData.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Fetch current weather data from Open-Meteo API for given coordinates
    private static String fetchWeatherData(double latitude, double longitude, Settings.Unit unit) {
        try {
        	
            // Build API URL with required parameters
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude +
                    "&longitude=" + longitude + "&current=temperature_2m,relative_humidity_2m,wind_speed_10m";

            // Make HTTP GET request
            HttpURLConnection apiConnection = fetchApiResponse(url);

            // Return error message if connection fails
            if (apiConnection.getResponseCode() != 200) return "Error connecting to weather API.";

            // Read and parse JSON response
            String jsonResponse = readApiResponse(apiConnection);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);

            // Extract current weather data object
            JSONObject currentWeatherJson = (JSONObject) jsonObject.get("current");

            // Get individual values
            String time = (String) currentWeatherJson.get("time");
            double temperatureC = (double) currentWeatherJson.get("temperature_2m");
            long humidity = (long) currentWeatherJson.get("relative_humidity_2m");
            double windSpeed = (double) currentWeatherJson.get("wind_speed_10m");
            
            double temperature;
            String tempUnit;

            if (unit == Settings.Unit.FAHRENHEIT) {
                temperature = celsiusToFahrenheit(temperatureC);
                tempUnit = "°F";
            } else {
                temperature = temperatureC;
                tempUnit = "°C";
            }


            // Get weather condition description based on these values
            String weatherDescription = getWeatherDescription(temperature, humidity, windSpeed);

            // Format and return weather report string
            return String.format("Time: %s\nTemperature: %.1f%s\nHumidity: %d%%\nWind Speed: %.1f m/s\nCondition: %s",
                    time, temperature, tempUnit, humidity, windSpeed, weatherDescription);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing weather data.";
        }
    }
    
    private static double celsiusToFahrenheit(double celsius) {
        return celsius * 9 / 5 + 32;
    }


    // Generate simple weather condition text based on temp, humidity, wind speed
    private static String getWeatherDescription(double temp, long humidity, double windSpeed) {
        StringBuilder description = new StringBuilder();

        // Interpret temperature
        if (temp >= 30) {
            description.append("Hot");
        } else if (temp >= 20) {
            description.append("Warm");
        } else if (temp >= 10) {
            description.append("Cool");
        } else {
            description.append("Cold");
        }

        // Interpret humidity
        if (humidity >= 80) {
            description.append(" and very humid");
        } else if (humidity >= 60) {
            description.append(" and humid");
        } else if (humidity <= 30) {
            description.append(" and dry");
        }

        // Interpret wind speed
        if (windSpeed > 10) {
            description.append(", windy.");
        } else if (windSpeed >= 3) {
            description.append(", breezy.");
        } else {
            description.append(", calm.");
        }

        return description.toString();
    }

    // Read API response body as a string
    private static String readApiResponse(HttpURLConnection apiConnection) throws IOException {
        Scanner scanner = new Scanner(apiConnection.getInputStream());
        StringBuilder resultJson = new StringBuilder();

        // Read all lines and append to string builder
        while (scanner.hasNext()) {
            resultJson.append(scanner.nextLine());
        }
        scanner.close();
        return resultJson.toString();
    }

    // Create HTTP GET connection for the given URL string
    private static HttpURLConnection fetchApiResponse(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET"); // Use GET method
        return conn;
    }
}
