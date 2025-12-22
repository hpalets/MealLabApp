package gr.meallab.model;

/**
 * Αντιπροσωπεύει μια σύνοψη συνταγής
 * (όπως εμφανίζεται στα αποτελέσματα αναζήτησης)
 */
public class MealSummary {

    private String idMeal;
    private String name;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
