package gr.meallab;

import gr.meallab.Recipe;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;

public class StorageManager {
    private static final String FILE_NAME = "meal_lab_data.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    // Αποθήκευση
    public static void save(java.util.List<Recipe> favorites, java.util.List<Recipe> cooked) {
        try {
            UserData data = new UserData(favorites, cooked);
            mapper.writeValue(new File(FILE_NAME), data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Φόρτωση
    public static UserData load() {
        try {
            File file = new File(FILE_NAME);
            if (file.exists()) {
                return mapper.readValue(file, UserData.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new UserData(new java.util.ArrayList<>(), new java.util.ArrayList<>());
    }
}