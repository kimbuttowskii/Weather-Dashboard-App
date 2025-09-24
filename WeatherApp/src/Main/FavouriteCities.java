package Main;

import java.util.HashSet;
import java.util.Set;

public class FavouriteCities {
    // Store favorite cities in a HashSet (no duplicates)
    private static final Set<String> favorites = new HashSet<>();

    // Add a city to favorites
    public static boolean addCity(String city) {
        return favorites.add(city.trim().toLowerCase());
    }

    // Remove a city from favorites
    public static boolean removeCity(String city) {
        return favorites.remove(city.trim().toLowerCase());
    }

    // Check if a city is already in favorites
    public static boolean isFavorite(String city) {
        return favorites.contains(city.trim().toLowerCase());
    }

    // Get all favorite cities
    public static Set<String> getAllFavorites() {
        return new HashSet<>(favorites); // Return a copy to prevent external modification
    }
}
