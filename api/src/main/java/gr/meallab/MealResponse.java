package gr.meallab;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MealResponse {
       
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