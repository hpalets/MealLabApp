package gr.meallab.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class MealDBClient {
    private final ObjectMapper mapper = new ObjectMapper();

    // Αναζήτηση με όνομα
    public List<Meal> searchMealsByName(String name) throws Exception {
        URL url = new URL("https://www.themealdb.com/api/json/v1/1/search.php?s=" + name);
        JsonNode node = mapper.readTree(url);
        JsonNode mealsNode = node.get("meals");
        if (mealsNode == null || mealsNode.isNull()) return null;
        return Arrays.asList(mapper.treeToValue(mealsNode, Meal[].class));
    }

    // Αναζήτηση με υλικό
    public List<Meal> searchMealsByIngredient(String ingredient) throws Exception {
        URL url = new URL("https://www.themealdb.com/api/json/v1/1/filter.php?i=" + ingredient);
        JsonNode node = mapper.readTree(url);
        JsonNode mealsNode = node.get("meals");
        if (mealsNode == null || mealsNode.isNull()) return null;
        return Arrays.asList(mapper.treeToValue(mealsNode, Meal[].class));
    }

    // Λήψη συνταγής με ID
    public Meal getMealById(String id) throws Exception {
        URL url = new URL("https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + id);
        JsonNode node = mapper.readTree(url);
        JsonNode mealsNode = node.get("meals");
        if (mealsNode == null || mealsNode.isNull()) return null;
        Meal[] meals = mapper.treeToValue(mealsNode, Meal[].class);
        return meals.length > 0 ? meals[0] : null;
    }
}