package gr.meallab;

import gr.meallab.MealDBClient;
import gr.meallab.Meal;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainSceneCreator {

    public static Scene createScene() {

        // --- 1. Background Image ---
        ImageView backgroundView = new ImageView();
        try {
            // Load from resources
            Image bgImage = new Image(MainSceneCreator.class.getResourceAsStream("/icons/background.jpg"));
            backgroundView.setImage(bgImage);
            backgroundView.setFitWidth(800);
            backgroundView.setFitHeight(600);
            
            // Doesn't preserve ratio to fill the screen
            backgroundView.setPreserveRatio(false); 
        } catch (Exception e) {
            System.out.println("Error:can't load image from /icons/cooking_bg.gif");
        }

        // --- 2. Title ---
        Label titleLabel = new Label("Meal Lab App");
        // White Text with Drop Shadow
        titleLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);");

        // --- 3. Create Buttons ---
        Button searchBtn = createStyledButton("🔍 Search Recipes");
        Button randomBtn = createStyledButton("🎲 Random Recipe");
        Button cookedBtn = createStyledButton("✔ Cooked Recipes");
        Button favoriteBtn = createStyledButton("⭐ Favorite Recipes");

        // --- 4. Button Functions ---

        searchBtn.setOnAction(e -> 
            App.changeScene(SearchSceneCreator.createScene())
        );

        randomBtn.setOnAction(e -> {
            try {
                MealDBClient client = new MealDBClient();
                Meal randomMeal = client.getRandomMeal();
                if (randomMeal != null) {
                    App.changeScene(DetailsSceneCreator.createScene(randomMeal.getIdMeal()));
                }
            } catch (Exception ex) {
                AlertUtil.showAlert("Failed to load random recipe");
            }
        });

        favoriteBtn.setOnAction(e -> 
            App.changeScene(FavoriteSceneCreator.createScene())
        );

        cookedBtn.setOnAction(e -> {    
    App.changeScene(CookedSceneCreator.createScene()); 
});

        // --- 5. LAYOUT---
        
        // VBox για τα κουμπιά και τον τίτλο
        VBox menuBox = new VBox(20, titleLabel, searchBtn, randomBtn, cookedBtn, favoriteBtn);
        menuBox.setAlignment(Pos.CENTER);

        // StackPane: Βάζει το menuBox ΠΑΝΩ από το backgroundView
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundView, menuBox);

        // Συνδέουμε το μέγεθος της εικόνας με το μέγεθος του παραθύρου (root)
        backgroundView.fitWidthProperty().bind(root.widthProperty());
        backgroundView.fitHeightProperty().bind(root.heightProperty());
        backgroundView.setPreserveRatio(false); 

        return new Scene(root, 800, 600);
    }

    // --- Buttons Style ---
    private static Button createStyledButton(String text) {
        Button btn = new Button(text);
        
        // CSS for the background and hover effects
        String defaultStyle = "-fx-background-color: rgba(255, 255, 255, 0.85); " +
                              "-fx-text-fill: #333; " +
                              "-fx-font-size: 16px; " +
                              "-fx-font-weight: bold; " +
                              "-fx-background-radius: 20; " +
                              "-fx-min-width: 220px; " +
                              "-fx-cursor: hand;";
        
        String hoverStyle = "-fx-background-color: white; " + // Όταν περνάει το ποντίκι γίνεται τελείως λευκό
                            "-fx-text-fill: #000; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-background-radius: 20; " +
                            "-fx-min-width: 220px; " +
                            "-fx-cursor: hand; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 10, 0, 0, 0);"; // Σκιά στο hover

        btn.setStyle(defaultStyle);

        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e -> btn.setStyle(defaultStyle));

        return btn;
    }
}