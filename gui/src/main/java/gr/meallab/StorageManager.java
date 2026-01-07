package gr.meallab;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StorageManager {
    private static final String FILE_NAME = "meal_lab_data.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    // ΔΙΟΡΘΩΣΗ: Μετονομασία σε saveData και αφαίρεση παραμέτρων
    public static void saveData() {
        try {
            // Μετατρέπουμε τα Set σε List γιατί αυτό περιμένει ο Constructor του UserData
            List<String> favs = new ArrayList<>(MealStatusManager.getFavorites());
            List<String> cooked = new ArrayList<>(MealStatusManager.getCooked());
            
            UserData data = new UserData(favs, cooked);
            mapper.writeValue(new File(FILE_NAME), data);
            System.out.println("Save successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UserData load() {
        try {
            File file = new File(FILE_NAME);
            if (file.exists()) {
                return mapper.readValue(file, UserData.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new UserData(new ArrayList<>(), new ArrayList<>());
    }
}