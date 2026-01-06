package gr.meallab.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Meal {

    @JsonProperty("idMeal") 
    private String idMeal;

    @JsonProperty("strMeal")
    private String name; 

    @JsonProperty("strCategory")
    private String category;

    @JsonProperty("strArea")
    private String area;

    @JsonProperty("strInstructions")
    private String instructions;

    @JsonProperty("strMealThumb")
    private String thumbnail;

    // Προσθήκη JsonProperty για να γεμίζουν τα υλικά από το API
    @JsonProperty("strIngredient1") private String strIngredient1;
    @JsonProperty("strIngredient2") private String strIngredient2;
    @JsonProperty("strIngredient3") private String strIngredient3;
    @JsonProperty("strIngredient4") private String strIngredient4;
    @JsonProperty("strIngredient5") private String strIngredient5;
    @JsonProperty("strIngredient6") private String strIngredient6;
    @JsonProperty("strIngredient7") private String strIngredient7;
    @JsonProperty("strIngredient8") private String strIngredient8;
    @JsonProperty("strIngredient9") private String strIngredient9;
    @JsonProperty("strIngredient10") private String strIngredient10;
    @JsonProperty("strIngredient11") private String strIngredient11;

    @JsonProperty("strMeasure1") private String strMeasure1;
    @JsonProperty("strMeasure2") private String strMeasure2;
    @JsonProperty("strMeasure3") private String strMeasure3;
    @JsonProperty("strMeasure4") private String strMeasure4;
    @JsonProperty("strMeasure5") private String strMeasure5;
    @JsonProperty("strMeasure6") private String strMeasure6;
    @JsonProperty("strMeasure7") private String strMeasure7;
    @JsonProperty("strMeasure8") private String strMeasure8;
    @JsonProperty("strMeasure9") private String strMeasure9;
    @JsonProperty("strMeasure10") private String strMeasure10;
    @JsonProperty("strMeasure11") private String strMeasure11;

    // Getters και Setters
    public String getIdMeal() { return idMeal; }
    public void setIdMeal(String idMeal) { this.idMeal = idMeal; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    // Διορθωμένοι μέθοδοι πρόσβασης μέσω Reflection
    public String getIngredient(int index) {
        try {
            java.lang.reflect.Field field = this.getClass().getDeclaredField("strIngredient" + index);
            field.setAccessible(true);
            return (String) field.get(this);
        } catch (Exception e) { return null; }
    }

    public String getMeasure(int index) {
        try {
            java.lang.reflect.Field field = this.getClass().getDeclaredField("strMeasure" + index);
            field.setAccessible(true);
            return (String) field.get(this);
        } catch (Exception e) { return null; }
    }
}