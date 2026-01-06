package gr.meallab.model;

import java.util.ArrayList;
import java.util.List;

public class SearchFunctions {

    private MealDBClient client = new MealDBClient();

    // Λειτουργία αναζήτησης με όνομα
    public List<Meal> SearchUsingName(String name) {
        try {
            System.out.println("Searching Recipe with name: " + name + "\n");
            List<Meal> meals = client.searchMealsByName(name);
            return meals;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Λειτουργία αναζήτησης με υλικό (ΕΔΩ ΕΙΝΑΙ Η ΔΙΟΡΘΩΣΗ)
    public List<Meal> SearchUsingIngredient(String ingredient) {
        try {
            System.out.println("Searching recipes with ingredient: " + ingredient + "\n");
            List<Meal> summaryMeals = client.searchMealsByIngredient(ingredient);
            
            if (summaryMeals == null) return null;

            List<Meal> detailedMeals = new ArrayList<>();
            // Για κάθε συνταγή που βρέθηκε, παίρνουμε τις πλήρεις πληροφορίες (Category/Area)
            for (Meal m : summaryMeals) {
                Meal fullMeal = client.getMealById(m.getIdMeal());
                if (fullMeal != null) {
                    detailedMeals.add(fullMeal);
                } else {
                    detailedMeals.add(m); // fallback
                }
            }
            return detailedMeals;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}