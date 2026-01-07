package gr.meallab;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipe {
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("idMeal") // Εδώ σιγουρεύουμε ότι το JSON θα γράφει idMeal
    private String idMeal;

    public Recipe() {}

    public Recipe(String name, String idMeal) {
        this.name = name;
        this.idMeal = idMeal;
    }

    public String getName() { return name; }
    public String getIdMeal() { return idMeal; }
    
    public void setName(String name) { this.name = name; }
    public void setIdMeal(String idMeal) { this.idMeal = idMeal; }
}