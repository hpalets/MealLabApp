package gr.meallab.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * Αντιπροσωπεύει μια πλήρη συνταγή
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meal {

 // Λέμε: "Όταν έρθει το 'idMeal', βάλτο στο δικό μου 'idMeal'"
    @JsonProperty("idMeal") 
    private String idMeal;

    // Λέμε: "Όταν έρθει το 'strMeal', βάλτο στο δικό μου 'name'"
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
}
