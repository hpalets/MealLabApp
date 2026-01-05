package gr.meallab.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//Recipe
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meal {

    //The response from the API replaces the field using the JSON

    //THIS ARE THE FIELDS WE NEED
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

    
    private String strIngredient1;
    private String strIngredient2;
    private String strIngredient3;
    private String strIngredient4;
    private String strIngredient5;
    private String strIngredient6;
    private String strIngredient7;
    private String strIngredient8;
    private String strIngredient9;
    private String strIngredient10;
    private String strIngredient11;

    
    private String strMeasure1;
    private String strMeasure2;
    private String strMeasure3;
    private String strMeasure4;
    private String strMeasure5;
    private String strMeasure6;
    private String strMeasure7;
    private String strMeasure8;
    private String strMeasure9;
    private String strMeasure10;
    private String strMeasure11;

    
    //---------------------GETTERS AND SETTERS------------------------
    
    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getIngredient(int index) {
    try {
        return (String) this.getClass()
                .getDeclaredField("strIngredient" + index)
                .get(this);
    } catch (Exception e) {
            return null;
        }
    }

    public String getMeasure(int index) {
        try {
            return (String) this.getClass()
                    .getDeclaredField("strMeasure" + index)
                    .get(this);
        } catch (Exception e) {
            return null;
        }
    }

}
