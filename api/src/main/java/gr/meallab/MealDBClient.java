package gr.meallab;


import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MealDBClient {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private final OkHttpClient client;
    private final ObjectMapper mapper;

    public MealDBClient() {
        this.client = new OkHttpClient();
        this.mapper = new ObjectMapper();
    }

    // --- ΒΟΗΘΗΤΙΚΗ ΜΕΘΟΔΟΣ (Για να μην γράφουμε τον ίδιο κώδικα πολλές φορές) ---
    private List<Meal> makeRequest(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Connection failed" + response);
            }

            String jsonResponse = response.body().string();
            // Deserialization: JSON -> Java Object
            MealResponse wrapper = mapper.readValue(jsonResponse, MealResponse.class);

            if (wrapper == null || wrapper.getMeals() == null) {
                return null;
            }
            return wrapper.getMeals();
        }
    }

    // --- Fuction 1 : Search by name --- 
    public List<Meal> searchMealsByName(String name) throws IOException {
        String url = BASE_URL + "search.php?s=" + name;
        return makeRequest(url);
    }

    // --- Fuction 2 : Search by Ingredient --- 
    public List<Meal> searchMealsByIngredient(String ingredient) throws IOException {
        String url = BASE_URL + "filter.php?i=" + ingredient;
        return makeRequest(url);
    }

    // --- Fuction 3 : Search using ID --- 
    public Meal getMealById(String id) throws IOException {
        String url = BASE_URL + "lookup.php?i=" + id;
        List<Meal> meals = makeRequest(url);
        
        //Expecting only one meal
        if (meals != null && !meals.isEmpty()) {
            return meals.get(0);
        }
        return null;
    }

    // --- Fuction 4 : Random Recipe --- 
    public Meal getRandomMeal() throws IOException {
        String url = BASE_URL + "random.php";
        List<Meal> meals = makeRequest(url);

        if (meals != null && !meals.isEmpty()) {
            return meals.get(0);
        }
        return null;
    } 

    // --- Fuction 5 : Search by Category  ---
        public List<Meal> searchMealsByCategory(String category) throws IOException {

            String url = BASE_URL + "filter.php?c=" + category;

            List<Meal> meals = makeRequest(url);

            if (meals != null && !meals.isEmpty()) {
                return meals;
            }

            return null;
        }
      
}