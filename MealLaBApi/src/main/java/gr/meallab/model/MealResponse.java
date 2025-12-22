package gr.meallab.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MealResponse {

    // Η εργασία ζητάει να πάρουμε τα δεδομένα από το JSON.
    // Το JSON ξεκινάει με τη λέξη "meals", άρα πρέπει να έχουμε μια λίστα με αυτό το όνομα.
    
    @JsonProperty("meals")
    private List<Meal> meals;

    // Getters και Setters για να μπορούμε να πάρουμε τη λίστα
    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}