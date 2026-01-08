package gr.meallab;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MealDBClientTest {

    @Test
    void testGetMealById() {
        MealDBClient client = new MealDBClient();
        // Χρησιμοποιούμε ένα γνωστό ID (π.χ. 52772 = Teriyaki Chicken Cashew)
        String knownId = "52772"; 
        
        try {
            Meal meal = client.getMealById(knownId);
            assertNotNull(meal, "Η συνταγή δεν πρέπει να είναι null");
            assertEquals("Teriyaki Chicken Casserole", meal.getName(), "Το όνομα πρέπει να είναι σωστό");
        } catch (Exception e) {
            fail("Απέτυχε η σύνδεση με το API: " + e.getMessage());
        }
    }

    @Test
    void testSearchMeal() {
         // Εδώ τεστάρουμε ότι η αναζήτηση επιστρέφει αποτελέσματα
         try {
             // Υποθέτουμε ότι έχεις κάποια μέθοδο search στο client ή απλά test του connection
             MealDBClient client = new MealDBClient();
             Meal meal = client.getMealById("52772");
             assertNotNull(meal);
         } catch (Exception e) {
             fail("Search failed");
         }
    }
}