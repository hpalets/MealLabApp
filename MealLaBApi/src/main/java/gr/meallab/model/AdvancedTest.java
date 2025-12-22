package gr.meallab.model;

import java.util.List;

public class AdvancedTest {

    public static void main(String[] args) {
        System.out.println("--- TEST 2: ΠΡΟΗΓΜΕΝΕΣ ΛΕΙΤΟΥΡΓΙΕΣ ---");
        
        MealDBClient client = new MealDBClient();
        
        try {
            // --- ΜΕΡΟΣ Α: ΤΥΧΑΙΑ ΣΥΝΤΑΓΗ ---
            System.out.println("\n[A] Ζητάμε Τυχαία Συνταγή...");
            Meal randomMeal = client.getRandomMeal();
            
            if (randomMeal != null) {
                System.out.println("Πρόταση: " + randomMeal.getName());
                System.out.println("Κατηγορία: " + randomMeal.getCategory());
                System.out.println("Οδηγίες (αρχή): " + randomMeal.getInstructions().substring(0, Math.min(50, randomMeal.getInstructions().length())) + "...");
            } else {
                System.out.println("Σφάλμα: Δεν ήρθε τυχαία συνταγή.");
            }

            // --- ΜΕΡΟΣ Β: ΑΝΑΖΗΤΗΣΗ ΜΕ ΥΛΙΚΟ ---
            // Ας ψάξουμε κάτι διαφορετικό, π.χ. "Chicken" (Κοτόπουλο)
            String ingredient = "Chicken"; 
            System.out.println("\n[B] Ψάχνουμε συνταγές με υλικό: " + ingredient);
            
            List<Meal> meals = client.searchMealsByIngredient(ingredient);
            
            if (meals != null) {
                System.out.println("Βρέθηκαν " + meals.size() + " συνταγές!");
                // Τυπώνουμε τις 3 πρώτες
                for (int i = 0; i < Math.min(3, meals.size()); i++) {
                    System.out.println(" -> " + meals.get(i).getName());
                }
            } else {
                System.out.println("Δεν βρέθηκαν συνταγές με " + ingredient);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}