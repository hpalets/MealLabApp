package gr.meallab;

import java.util.HashSet;
import java.util.Set;

/**
 * Διαχειρίζεται την κατάσταση των συνταγών (Αγαπημένες και Μαγειρεμένες).
 * Κάθε αλλαγή αποθηκεύεται αυτόματα μέσω του StorageManager.
 */
public class MealStatusManager {

    // Χρήση Set για γρήγορη αναζήτηση και αποφυγή διπλότυπων
    private static final Set<String> favorites = new HashSet<>();
    private static final Set<String> cooked = new HashSet<>();

    // ===== Favorites (Αγαπημένα) =====

    /**
     * Προσθέτει μια συνταγή στα αγαπημένα και αποθηκεύει την αλλαγή.
     */
    public static void addFavorite(String mealId) {
        favorites.add(mealId);
        StorageManager.saveData(); // Ενημέρωση του αρχείου JSON
    }

    /**
     * Αφαιρεί μια συνταγή από τα αγαπημένα και αποθηκεύει την αλλαγή.
     */
    public static void removeFavorite(String mealId) {
        favorites.remove(mealId);
        StorageManager.saveData(); // Ενημέρωση του αρχείου JSON
    }

    /**
     * Ελέγχει αν μια συνταγή είναι στα αγαπημένα.
     */
    public static boolean isFavorite(String mealId) {
        return favorites.contains(mealId);
    }

    // ===== Cooked (Μαγειρεμένα) =====

    /**
     * Προσθέτει μια συνταγή στα μαγειρεμένα και αποθηκεύει την αλλαγή.
     */
    public static void addCooked(String mealId) {
        cooked.add(mealId);
        StorageManager.saveData(); // Ενημέρωση του αρχείου JSON
    }

    /**
     * Αφαιρεί μια συνταγή από τα μαγειρεμένα και αποθηκεύει την αλλαγή.
     */
    public static void removeCooked(String mealId) {
        cooked.remove(mealId);
        StorageManager.saveData(); // Ενημέρωση του αρχείου JSON
    }

    /**
     * Ελέγχει αν μια συνταγή είναι στα μαγειρεμένα.
     */
    public static boolean isCooked(String mealId) {
        return cooked.contains(mealId);
    }

    // ===== Getters =====

    /**
     * Επιστρέφει το σύνολο των αγαπημένων συνταγών.
     */
    public static Set<String> getFavorites() {
        return favorites;
    }

    /**
     * Επιστρέφει το σύνολο των μαγειρεμένων συνταγών.
     */
    public static Set<String> getCooked() {
        return cooked;
    }
}