package gr.meallab.model;

import java.util.List;

public class MainTest {

    public static void main(String[] args) {
        
        System.out.println("--- ΕΚΚΙΝΗΣΗ ΔΟΚΙΜΗΣ ---");
        
        // 1. Δημιουργούμε τον "πελάτη" μας
        MealDBClient client = new MealDBClient();
        
        try {
            // 2. Ζητάμε να βρούμε συνταγές με τη λέξη "Arrabiata"
            System.out.println("Αναζήτηση για 'Arrabiata'...");
            List<Meal> meals = client.searchMealsByName("Arrabiata");
            
            // 3. Ελέγχουμε αν βρήκαμε κάτι
            if (meals != null) {
                System.out.println("Βρέθηκαν " + meals.size() + " συνταγές!");
                
                // 4. Τυπώνουμε τα ονόματα που βρήκαμε
                for (Meal m : meals) {
                    System.out.println("Όνομα Συνταγής: " + m.getName());
                    System.out.println("Κατηγορία: " + m.getCategory());
                    System.out.println("ID: " + m.getIdMeal());
                }
            } else {
                System.out.println("Δεν βρέθηκαν συνταγές.");
            }
         // --- ΜΕΡΟΣ Γ (EXTRA): ΑΝΑΖΗΤΗΣΗ ΜΕ ΚΑΤΗΓΟΡΙΑ ---
            System.out.println("\n[Γ] Ψάχνουμε για γλυκά (Dessert)...");
            List<Meal> sweets = client.searchMealsByCategory("Dessert");
            
            if (sweets != null) {
                System.out.println("Βρέθηκαν " + sweets.size() + " γλυκά!");
                System.out.println("Πάρε μια ιδέα: " + sweets.get(0).getName());
            }
        } catch (Exception e) {
            // Αν κάτι πάει στραβά (π.χ. δεν έχετε ίντερνετ), θα μας το πει εδώ
            e.printStackTrace();
        }
        
        System.out.println("--- ΤΕΛΟΣ ΔΟΚΙΜΗΣ ---");
    }
}