package gr.meallab;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane; // <-- Προστέθηκε αυτό για το τρικ
import javafx.stage.Stage;

public class App extends Application {

    public static List<Recipe> favoritesList = new ArrayList<>();
    public static List<Recipe> cookedList = new ArrayList<>();
    
    private static Stage primaryStage;
    static Scene mainScene, searchScene, mealDetailScene;

    @Override
    public void start(Stage stage) {
        try {
            // 1. Φόρτωση δεδομένων
            UserData data = StorageManager.load();
            if (data != null) {
                favoritesList.addAll(data.favorites);
                cookedList.addAll(data.cooked);
                for (Recipe r : data.favorites) {
                    MealStatusManager.addFavorite(r.getIdMeal());
                }
                for (Recipe r : data.cooked) {
                    MealStatusManager.addCooked(r.getIdMeal());
                }
            }

            // 2. Ρύθμιση Stage
            this.primaryStage = stage;
            stage.setTitle("Meal Lab App");

            // 3. Εικονίδιο
            try {
                stage.getIcons().add(
                    new Image(App.class.getResourceAsStream("/icons/app.png"))
                );
            } catch (Exception iconEx) {
                System.out.println("Icon not found, skipping...");
            }

            // 4. Εμφάνιση αρχικής σκηνής
            mainScene = MainSceneCreator.createScene();
            primaryStage.setScene(mainScene);
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch (Exception e) {
            System.err.println("Error starting the application:");
            e.printStackTrace();
        }
    }

    // --- ΜΕΘΟΔΟΣ CHANGE SCENE ---
    public static void changeScene(Scene newScene) {
        if (primaryStage != null) {
            Scene currentScene = primaryStage.getScene();

            if (currentScene == null) {
                primaryStage.setScene(newScene);
            } else {
                
                // Reuse the existing Scene to avoid recreating the Stage.
                // Only the root node is replaced, improving performance and keeping window state.

                Parent newRoot = newScene.getRoot();             
            
                newScene.setRoot(new StackPane());         
                
                currentScene.setRoot(newRoot);
                
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        launch(args);
    }
}