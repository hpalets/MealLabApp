package gr.meallab.model;

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
                throw new IOException("Πρόβλημα με τη σύνδεση: " + response);
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

    // --- ΛΕΙΤΟΥΡΓΙΑ 1: Αναζήτηση με Όνομα ---
    public List<Meal> searchMealsByName(String name) throws IOException {
        String url = BASE_URL + "search.php?s=" + name;
        return makeRequest(url);
    }

    // --- ΛΕΙΤΟΥΡΓΙΑ 2: Αναζήτηση με Υλικό (ΝΕΟ) ---
    // Ζητάει η εργασία: "Ανάκτηση λίστας συνταγών που περιέχουν συγκεκριμένο υλικό"
    public List<Meal> searchMealsByIngredient(String ingredient) throws IOException {
        String url = BASE_URL + "filter.php?i=" + ingredient;
        return makeRequest(url);
    }

    // --- ΛΕΙΤΟΥΡΓΙΑ 3: Λήψη Λεπτομερειών με ID ---
    // Ζητάει η εργασία: "Πλήρεις πληροφορίες... με βάση το μοναδικό ID"
    public Meal getMealById(String id) throws IOException {
        String url = BASE_URL + "lookup.php?i=" + id;
        List<Meal> meals = makeRequest(url);
        
        // Επειδή ψάχνουμε με ID, περιμένουμε να βρούμε ΜΙΑ συνταγή
        if (meals != null && !meals.isEmpty()) {
            return meals.get(0);
        }
        return null;
    }

    // --- ΛΕΙΤΟΥΡΓΙΑ 4: Τυχαία Συνταγή ---
    // Ζητάει η εργασία: "Πρόταση για τυχαία συνταγή"
    public Meal getRandomMeal() throws IOException {
        String url = BASE_URL + "random.php";
        List<Meal> meals = makeRequest(url);
        
        if (meals != null && !meals.isEmpty()) {
            return meals.get(0);
        }
        return null;
    } 
     // --- EXTRA ΛΕΙΤΟΥΡΓΙΑ: Αναζήτηση με Κατηγορία (π.χ. "Seafood", "Dessert") ---
        public List<Meal> searchMealsByCategory(String category) throws IOException {
            // Το API για κατηγορίες είναι: filter.php?c=Seafood
            String url = BASE_URL + "filter.php?c=" + category;
            return makeRequest(url);
        }
}