package gr.meallab.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; // Εργαλεία για να λέμε "Πρέπει να είναι..."

import java.io.IOException;
import java.util.List;

public class MealDBClientTest {

    // Φτιάχνουμε έναν πελάτη για να τον δοκιμάσουμε
    private final MealDBClient client = new MealDBClient();

    @Test
    public void testSearchByName() throws IOException {
        System.out.println("TEST: Αναζήτηση με όνομα (Arrabiata)...");
        
        // Ζητάμε την Arrabiata
        List<Meal> meals = client.searchMealsByName("Arrabiata");

        // ΕΛΕΓΧΟΙ (Assertions):
        // 1. Η λίστα δεν πρέπει να είναι κενή (null)
        assertNotNull(meals, "Η λίστα δεν πρέπει να είναι null");
        
        // 2. Πρέπει να έχει τουλάχιστον 1 συνταγή
        assertTrue(meals.size() > 0, "Πρέπει να βρεθεί τουλάχιστον 1 συνταγή");
        
        // 3. Η πρώτη συνταγή πρέπει να περιέχει τη λέξη "Arrabiata"
        Meal firstMeal = meals.get(0);
        assertTrue(firstMeal.getName().contains("Arrabiata"), "Το όνομα πρέπει να είναι σωστό");
    }

    @Test
    public void testSearchByIngredient() throws IOException {
        System.out.println("TEST: Αναζήτηση με υλικό (Rice)...");
        
        List<Meal> meals = client.searchMealsByIngredient("Rice");

        assertNotNull(meals);
        assertTrue(meals.size() > 0, "Πρέπει να υπάρχουν συνταγές με ρύζι");
    }

    @Test
    public void testRandomMeal() throws IOException {
        System.out.println("TEST: Τυχαία Συνταγή...");
        
        Meal meal = client.getRandomMeal();

        assertNotNull(meal, "Η τυχαία συνταγή δεν πρέπει να είναι null");
        assertNotNull(meal.getIdMeal(), "Η συνταγή πρέπει να έχει ID");
        assertNotNull(meal.getName(), "Η συνταγή πρέπει να έχει όνομα");
    }
}