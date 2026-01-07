package gr.meallab;

import java.util.HashSet;
import java.util.Set;

public class MealStatusManager {

    // Using Sets for efficient lookup of meal IDs and then we search them 
    private static final Set<String> favorites = new HashSet<>();
    private static final Set<String> cooked = new HashSet<>();

    // ===== Favorites =====
    public static void addFavorite(String mealId) {
        favorites.add(mealId);
    }

    public static void removeFavorite(String mealId) {
        favorites.remove(mealId);
    }

    public static boolean isFavorite(String mealId) {
        return favorites.contains(mealId);
    }

    // ===== Cooked =====
    public static void addCooked(String mealId) {
        cooked.add(mealId);
    }

    public static void removeCooked(String mealId) {
        cooked.remove(mealId);
    }

    public static boolean isCooked(String mealId) {
        return cooked.contains(mealId);
    }

    // (προαιρετικό – για λίστες)
    public static Set<String> getFavorites() {
        return favorites;
    }

    public static Set<String> getCooked() {
        return cooked;
    }
}


